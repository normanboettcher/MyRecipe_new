package agents;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import managers.DatumsManager;

public class ProtokollAgent extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8184434646383054043L;
	
	private File protokoll;
	private String ordner;
	private PrintWriter w;
	private ArrayList<String> collection;
	
	public ProtokollAgent() {
		this.collection = new ArrayList<String>();
	}
	
	protected ArrayList<String> getCollection() {
		return collection;
	}
	
	protected void collect(String str) {
		getCollection().add(str + "\n");
	}
	
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
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}
	
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
					
					//System.out.println(status);
					
					writeProtokollToFile(strings, status);
					
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				block();
			}
		}
	}
	
	private File getFile() {
		return protokoll;
	}
	
	private PrintWriter getPrintWriter() {
		return w;
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			getPrintWriter().close();
			File file = new File(System.getProperty("user.dir") + "/Agent_Protokolle/Zwischenstand.dat");
			file.delete();
		}
	}
	
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
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		//writer.close();
	}
	}
	
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
				//	bfr.close();
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return str;
	}
}
