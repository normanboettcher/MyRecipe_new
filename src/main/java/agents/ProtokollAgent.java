package agents;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;


import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import jade.lang.acl.UnreadableException;

/**
 * Klasse zur Realisierung eines Protokollagenten. Dieser schreibt jeden
 * Fortschritt aus der Kommunikation zwischen der Agenten in eine txt Datei im
 * Ordner Agent_Protokolle.
 * 
 * @author norman
 *
 */
public class ProtokollAgent extends Agent {
	//Attribute
	/**
	 * 
	 */
	private static final long serialVersionUID = 8184434646383054043L;
	
	private File protokoll;
	private PrintWriter w;
	private ArrayList<String> collection;
	
	/**
	 * Konstruktor des ProtokollAgenten. Ein Protokollagent hat eine
	 * {@code collection} mit allen Fortschritten der Prozesse als String.
	 */
	public ProtokollAgent() {
		this.collection = new ArrayList<String>();
	}

	/**
	 * Die Sammlung aller Fortschritte der Agentenkommunikation.
	 * 
	 * @return collection
	 */
	protected ArrayList<String> getCollection() {
		return collection;
	}

	/**
	 * Methode, um einen aus einer Message erhaltenen Fortschritt als String in der
	 * Collection abzulegen.
	 * 
	 * @param str der Fortschritt als String.
	 */
	protected void collect(String str) {
		getCollection().add(str + "\n");
	}
	/**
	 * Der Agent wird registriert.
	 */
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setName("ProtokollAgent");		
		sd.setType("Protokoll Agent");
		desc.addServices(sd);
		
		try {
			DFService.register(this, desc);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
		
		addBehaviour(new ProtokollAgentBehaviour());
	}
	/**
	 * Der Agent wird deregistriert.
	 */
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}

	/**
	 * Private Klasse zum realisieren des Verhaltens des ProtokollAgenten.
	 * 
	 * @author norman
	 *
	 */
	private class ProtokollAgentBehaviour extends CyclicBehaviour {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 7255643436722375201L;

		@Override
		public void action() {
			jade.lang.acl.ACLMessage empfangen = receive();
			
			String strings;
			String status;
			
			if(empfangen != null) {
				Object[] objects;
				try {
					objects = (Object[]) empfangen.getContentObject();
					strings = (String) objects[0];
					status = (String) objects[1];
					
					writeProtokollToFile(strings, status);
					
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
			} else {
				block();
			}
		}
	}

	/**
	 * Die Datei, in die das Protokoll geschrieben wird.
	 * 
	 * @return protokoll.
	 */
	private File getFile() {
		return protokoll;
	}

	/**
	 * Der PrintWriter, der die Fortschritte aus der {@code collection} in die Datei
	 * schreibt.
	 * 
	 * @return
	 */
	private PrintWriter getPrintWriter() {
		return w;
	}

	/**
	 * private Methode, um das Protokoll in die Datei zu schreiben.
	 * 
	 * @param str    der Fortschritt des Agenten als String.
	 * @param status der Status, der von den Agenten bei uebergabe der Fortschritte
	 *               uebergeben wird. {@code status 2} Der Zwischenstand der
	 *               Kommunikation wird in eine zwischenstand.dat datei geschrieben.
	 *               {@code status 0} Der Fortschritt als String wird der
	 *               {@code collection} hinzugefuegt. {@code status 1} Der Prozess
	 *               ist beendet und das gesamte Protokoll wird in die txt Datei
	 *               geschrieben.
	 */
	private void writeProtokollToFile(String str, String status) {
		
		switch(status) {
			case "2":
				schreibeZwischenstandZuDatei();
			case "0":
				collect(str);
				break;
			case "1":
				schreibeKomplettesProtokollZuDatei(getCollection());
				break;
			default:
					break;
		}
	}

	/**
	 * Methode, um das gesamte Protokoll niederzuschreiben.
	 * 
	 * @param str die Liste aller Fortschritte {@code collection}
	 */
	private void schreibeKomplettesProtokollZuDatei(ArrayList<String> str) {
		String project_path = System.getProperty("user.dir");
		String datum = LocalDateTime.now().toString();
		String filename = datum.replaceAll(":", "_");
		this.protokoll = new File(project_path + "/Agent_Protokolle/Protokoll_" + filename + ".txt");
		try {
			this.w = new PrintWriter(getFile()); 
			
			getPrintWriter().println(leseVonProtokollAusZwischenstand());
			
			for(int i = 0; i < str.size(); i++) {
				getPrintWriter().println(str.get(i));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (@SuppressWarnings("hiding") IOException e) {
			e.printStackTrace();
		} finally {
			getPrintWriter().close();
			//Nachdem der Zwischenstand geladen und zum Protokoll hinzugefuegt wurde,
			//Wird die Zwischenstand.dat geloescht. Sie wird nicht mehr benoetigt.
			File file = new File(System.getProperty("user.dir") + "/Agent_Protokolle/Zwischenstand.dat");
			file.delete();
		}
	}

	/**
	 * private Methode, um den Zwischenstand des Prozesses in eine zwischenstand.dat
	 * Datei zu speichern.
	 */
	private void schreibeZwischenstandZuDatei() {
		
		File file = new File(System.getProperty("user.dir") + "/Agent_Protokolle/Zwischenstand.dat");
		File file_new = null;

		if(file.exists()) {
			file.delete();
			file_new = new File(System.getProperty("user.dir") + "/Agent_Protokolle/Zwischenstand.dat");
		} else {
			file_new = new File(System.getProperty("user.dir") + "/Agent_Protokolle/Zwischenstand.dat");
		}

		DataOutputStream dous = null;
	
		try {
			dous =  new DataOutputStream(new FileOutputStream(file_new));

			for(int i = 0; i < getCollection().size(); i++) {
				dous.writeBytes(getCollection().get(i));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * private Methode, um den Zwischenstand aus der zwischenstand.dat datei zu
	 * holen.
	 * 
	 * @return str der Zwischenstand.
	 */
	@SuppressWarnings("deprecation")
	private String leseVonProtokollAusZwischenstand() {
		String str = "==================================LADE PROTOKOLL AUS ZWISCHENSTAND"
				+ "=====================================" + "\n";
		
		DataInputStream dis = null;
		try {
			
			dis = new DataInputStream(new FileInputStream(System.getProperty("user.dir") + "/Agent_Protokolle/Zwischenstand.dat"));
			
			while(dis.readLine() != null) {
				str += dis.readLine() + "\n";
			}
			
		} catch (EOFException eof) {
			//Ist ok
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
}
