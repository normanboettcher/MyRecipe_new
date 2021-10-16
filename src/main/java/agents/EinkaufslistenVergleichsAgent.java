package agents;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import databaseConnection.DBConnection;
import general.Einkaufsliste;
import general.Food;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.UnreadableException;
import managers.DatumsManager;

public class EinkaufslistenVergleichsAgent extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8755850857467148818L;

	private HashMap<Integer, Einkaufsliste> einkaufslisten_geordnet_nach_preis;
	private int rezept_id;
	private String name;
	
	private static final boolean ABSTEIGEND = false;
	private static final boolean AUFSTEIGEND = true;
	
	public EinkaufslistenVergleichsAgent(int rezept_id) {
		this.rezept_id = rezept_id;
		this.einkaufslisten_geordnet_nach_preis = new HashMap<Integer, Einkaufsliste>(); 
		this.name = "Vergleichsagent";
	}
	
	public String getAgentName() {
		return name;
	}
	
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		
		sd.setName("EinkaufslistenVergleichen");
		sd.setType("Einkaufslisten Vergleichen");
		desc.addServices(sd);
				
		try {
			DFService.register(this, desc);
			System.out.println(getAgentName() + " with AID: " 
					+ " is registered and ready to use");
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
		
		addBehaviour(new VergleichsAgentAnfrageVerhalten());
		
	}
	
	protected void takeDown() {
		
		try {
			DFService.deregister(this);	
			System.out.println(getAgentName() + " with AID: " 
					+ getAID().getName() + " is deregistered now and not ready to use");
		
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}
	
	private int getRezeptID() {
		return rezept_id;
	}
	
	private class VergleichsAgentAnfrageVerhalten extends Behaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5183576776849426475L;
		private boolean finished = false;
		
		public VergleichsAgentAnfrageVerhalten() {
		}
		
		@Override
		public boolean done() {
			return finished;
		}
		
		@Override
		public void action() {
			System.out.println("In Action von VergleichsAgentAnfrageVerhalten");
			String str1  = "Agent: [" + getName() + " ] ist  jetzt in der action() von VergleichsAgentAnfrageVerhalten";
			String str2  = "";
			String conv_id = "";

			jade.lang.acl.ACLMessage ergebnis = receive();
			
			if(ergebnis != null) {
				conv_id = ergebnis.getConversationId();
			} else {
				block();
			}
			
			if(ergebnis != null && conv_id.equals("ErgebnisVorliegend")) {
				
				String str3 = "";
				String str4 = "";
				String str5 = "";
				String str6 = "";
				String str7 = "";
				
				str3 = "Agent: [ " + getName() + " ] ist jetzt in der action() von AntwortVerhalten";
				
				
				
				str4 = "Agent: [ " + getName() + " ] konnte Antwort [ " + ergebnis + " ]"
						+ " von [ " + ergebnis.getSender() + " ] empfangen." ;
			
						HashMap<Integer, Einkaufsliste> l;
						try {
							l = (HashMap<Integer, Einkaufsliste>) ergebnis.getContentObject();
							vergleicheEinkaufslisten(l);

							str5 = "Agent: [ " + getName() + " ] hat Einkaufslisten [ " + l + " ] "
									+ "verglichen und sortiert.";
							
							for(int i : getEinkaufslistenSortiertNachPreis().keySet()) {
								str6 += "ID: [ " + getEinkaufslistenSortiertNachPreis().get(i).getEinkaufslisteID() + " ] Preis:"
										+ " [ " + getEinkaufslistenSortiertNachPreis().get(i).getGesamtPreis() + " ] Ersparnis : [ " 
										+ getEinkaufslistenSortiertNachPreis().get(i).getErsparnis() + " ]";
							}
							
							jade.lang.acl.ACLMessage send_to_sender = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
							send_to_sender.addReceiver(new AID("SendeAgent", AID.ISLOCALNAME));
							send_to_sender.setConversationId("ProzessBeendet");
							send_to_sender.setContentObject(getEinkaufslistenSortiertNachPreis());
							
							str7 = "Agent: [ " + getName() + " ] sendet ContentObject [ " + l + " ]";
							send(send_to_sender);
							
							ArrayList<String> strings = new ArrayList<String>();
							
							strings.add(str1); strings.add(str2); strings.add(str3); strings.add(str4); strings.add(str5);
							
							jade.lang.acl.ACLMessage send_to_protocoll = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
							
							Object[] objects = {strings, "0"};
							
							try {
								send_to_protocoll.setContentObject(objects);
								send_to_protocoll.addReceiver(new AID("ProtokollAgent", AID.ISLOCALNAME));
								send(send_to_protocoll);
								this.finished = true;
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
			} else if(ergebnis == null) {
				jade.lang.acl.ACLMessage msg = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
				msg.setContent("Anfrage fuer Angebote");
				msg.setConversationId("Anfrage");
				try {
					HashMap<Integer, Einkaufsliste> l = erstelleEinkaufslistenFuerAlleLaeden(getRezeptID());
					
					msg.setContentObject(l);
					
					if(msg.getContentObject() != null) {
						str2 = "Agent: [" + getName() + " ] hat alle Einkaufslisten geladen und "
								+ "zur Message [" + msg.getContent() + " ] hinzugefuegt.";
										
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg.addReceiver(new AID("AngebotAgent", AID.ISLOCALNAME));
							
				send(msg);
				
				String str3 = ("Message gesendet. von [ " + getName() + " ]");
				
				ArrayList<String> strings = new ArrayList<String>();
				
				strings.add(str1); strings.add(str2); strings.add(str3);
				
				jade.lang.acl.ACLMessage send_to_protocoll = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
				
				Object[] objects = {strings, "0"};
				
				try {
					send_to_protocoll.setContentObject(objects);
					send_to_protocoll.addReceiver(new AID("ProtokollAgent", AID.ISLOCALNAME));
					send(send_to_protocoll);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			} else {
				block();
			}
		}
	}
	
	public HashMap<Integer, Einkaufsliste> getEinkaufslistenSortiertNachPreis() {
		return einkaufslisten_geordnet_nach_preis;
	}
	
	private void vergleicheEinkaufslisten(HashMap<Integer, Einkaufsliste> liste) {
			this.einkaufslisten_geordnet_nach_preis = sortiereNachBilligsten(liste, AUFSTEIGEND);
	}
	
	private static HashMap<Integer, Double> extrahierePreisVonEinkaufsliste(
			HashMap<Integer, Einkaufsliste> l) {
		
		HashMap<Integer, Double> listen_mit_preisen_und_id = new HashMap<Integer, Double>();
		
		for(int i : l.keySet()) {
			listen_mit_preisen_und_id.put(i, l.get(i).getGesamtPreis());
		}
		return listen_mit_preisen_und_id;
	}
	
	private static HashMap<Integer, Einkaufsliste> sortiereNachBilligsten(
			HashMap<Integer, Einkaufsliste> unsortiert, final boolean order) {
		
		List<Entry<Integer, Double>> liste = new LinkedList<Entry<Integer, Double>>(extrahierePreisVonEinkaufsliste(unsortiert).entrySet());
		
		//printMap(unsortiert);
		
		Collections.sort(liste, new Comparator<Entry<Integer, Double>>() {
			public int compare(
					Entry<Integer, Double> wert1, Entry<Integer, Double> wert2) {
				if(order) {
					return wert1.getValue().compareTo(wert2.getValue());
				}
				else {
					return wert2.getValue().compareTo(wert1.getValue());
				}
			}
		});
		
		HashMap<Integer, Einkaufsliste> sortiert = new LinkedHashMap<Integer, Einkaufsliste>();
		
		int key = 0;
			for(int i = 0; i < liste.size(); i++) {
				key = liste.get(i).getKey();
				
				sortiert.put(key, unsortiert.get(key));
			}		
		
		//System.out.println("Nach sortiert: ");
		//printMap(sortiert);
		return sortiert;
	}
	
	/**
	 * Hilfsmethode zum ausgeben der Maps. Zu Testzwecken.
	 * @param map die ubergebene map, die ausgegeben werden soll jeweils mit key und value.
	 */
	private static void printMap(HashMap<Integer, Einkaufsliste> map) {
		for(Entry<Integer, Einkaufsliste> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ". " + entry.getValue().getGesamtPreis() + " EUR");
		}
	}
	
	private HashMap<Integer, Einkaufsliste> erstelleEinkaufslistenFuerAlleLaeden(int rezept_id) {
		Connection con = DBConnection.getConnection();
		
		HashMap<Integer, Einkaufsliste> results = new HashMap<Integer, Einkaufsliste>();
		
		int[] laeden = {1, 3, 4};
		int laden_id = 0;
		
		for(int i = 0; i < laeden.length; i++) {
			laden_id = laeden[i];
			
			
			Einkaufsliste l = new Einkaufsliste(laden_id, DatumsManager.aktuellesDatum());
			
			try {
				String laden = "";
				
				PreparedStatement get_laden_bez = con.prepareStatement("select bez from laeden where laden_id = ?");
				get_laden_bez.setInt(1, laden_id);
				
				ResultSet laden_bez = get_laden_bez.executeQuery();
				while(laden_bez.next()) {
					laden = laden_bez.getString("bez");
				}
				//System.out.println("-----" +laden);
				
				l.addLaden(laden);
				PreparedStatement stmt = con.prepareStatement("select * from " + laden + "_sortiment"
						+ " full outer join "
						+ " zutaten_aus_rezepte"
						+ " on " + laden + "_sortiment.artikelnr = zutaten_aus_rezepte.artikelnr"
								+ " where zutaten_aus_rezepte.rezept_id = ?");
				stmt.setInt(1, rezept_id);
				
				ResultSet produkte = stmt.executeQuery();
				
				while(produkte.next()) {
					Food f = new Food(produkte.getString("artikelbez"), produkte.getDouble("artikelpreis"),
							produkte.getString("hersteller"), "", produkte.getInt("vegan"), produkte.getInt("vegetarisch"), produkte.getInt("lokal"),
							produkte.getInt("bio"), produkte.getString("kategorie"));
					f.setArtikelNr(produkte.getInt("artikelnr"));
					l.addProduktZuListe(f,  produkte.getInt("anzahl"));
					
					//System.out.println(produkte.getString("artikelbez"));
				}
				//System.out.println(l.getProduktliste().size());
				l.berechneGesamtpreis();
	
				results.put(l.getEinkaufslisteID(), l);
				
				stmt.close();
				get_laden_bez.close();
				laden_bez.close();
				produkte.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results;
	}
}
