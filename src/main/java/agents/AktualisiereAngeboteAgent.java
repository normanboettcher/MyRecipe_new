package agents;


import java.io.IOException;
import java.util.HashMap;

import database.transfer.LoescheAusDatenbank;
import database.transfer.SpeicherInDatenbank;
import general.Einkaufsliste;
import general.Food;
import general.Supermarkt;
import general.supermarkets.Lidl;
import general.supermarkets.Netto;
import general.supermarkets.Penny;
import general.supermarkets.Rewe;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.UnreadableException;
import managers.DoubleManager;
import managers.RabattManager;

/**
 * Ein Agent zur Aktualisierung der Angebote der Supermaerkte.
 * 
 * @author norman
 *
 */
public class AktualisiereAngeboteAgent extends Agent {
	//Attribute
	/**
	 * 
	 */
	private static final long serialVersionUID = -4215772131075835095L;
	private String name;
	private boolean aktualisiert = false;
	
	//Konstruktor
	/**
	 * Konstruktor der Klasse AktualisiereAngeboteAgent. Im Konstruktor wird der
	 * feste Name 'AktualisiereAngeboteAgent' uebergeben.
	 */
	public AktualisiereAngeboteAgent() {
		this.name = "AktualisiereAngeboteAgent";
	}
	
	/**
	 * Getter fuer den Agentennamen.
	 * 
	 * @return name der Name des Agenten. Im Konstruktor festgelegt.
	 */
	public String getAgentName() {
		return name;
	}

	/**
	 * private Methode um den status der Aktualisierung zurueckzugeben.
	 * 
	 * @return aktualisiert true wenn Angebote aktualisiert. false wenn nicht.
	 */
	private boolean getAktualisiert() {
		return aktualisiert;
	}

	/**
	 * private Methode zum aendern des Status der Aktualisierung.
	 * 
	 * @param t true oder false, je nachdem, ob aktualisiert wurde.
	 */
	private void setAktualisiert(boolean t) {
		this.aktualisiert = t;
	}

	/**
	 * setup Methode des Agenten. Hier wird der Agent registriert.
	 */
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setName("AktualisierungsAgent");		
		sd.setType("Aktualisierungs Agent");
		desc.addServices(sd);
		
		try {
			DFService.register(this, desc);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
		
		addBehaviour(new AktualisierungsBehavior());
	}

	/**
	 * takeDown() Methode des Agenten. hier wird der Agent deregistriert.
	 */
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}
	
	/**
	 * Private Klasse Aktualisierungsbehaviour. Hier wird das Verhalten zur
	 * Aktualisiserung des Agenten realisiert.
	 * 
	 * @author norman
	 *
	 */
	private class AktualisierungsBehavior extends Behaviour {
		//Attribute
		/**
		 * 
		 */
		private static final long serialVersionUID = -2801746822033209525L;
		
		private boolean finished = false;
		
		@Override
		public void action() {
			String conv_id = "";
			String str = "";
			
			jade.lang.acl.ACLMessage msg = blockingReceive();
				
			if(msg != null) {
				conv_id = msg.getConversationId();
			} else {
				block();
			}
			
			try {
				if(msg != null && conv_id.equals("UpdateAnfrage")) {
					str += "Agent : [ " + getName() + " ] hat Anfrage fuer Update"
							+ " von [ " + msg.getSender() + " ] erhalten. \n"
									+ "KonversationID : [ " + msg.getConversationId() + " ] \n";
				Object[] objects = (Object[]) msg.getContentObject();
		
					Supermarkt[] maerkte = (Supermarkt[]) objects[1];
					int status = (int) objects[2];
					
					if(status == 1) {

						for(int i = 0; i < maerkte.length; i++) {
							Supermarkt s = maerkte[i];
							
							initAngebote(s.getBezeichnung());
							str += "Agent : [ " + getName() + " ] hat Angebote aktualisiert. \n";
						}
						setAktualisiert(true);
						sendBackToUeberwachung(msg, getAktualisiert(), objects);
					} else {
						str += "Agent : [ " + getName() + " ] hat Angebote nicht aktualisiert. \n";
						setAktualisiert(false);
						sendBackToUeberwachung(msg, getAktualisiert(), objects);
					}
					send(UeberwachungsAgent.sendToProtokollAgent(str, "0"));
				} else {
					block();
				}
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public boolean done() {
			return finished;
		}

		/**
		 * private Methode, um den Status und die Objekte zurueck an den Ueber-
		 * wachungsagenten zu senden.
		 * 
		 * @param m            die Message, auf die ein reply erstellt wird.
		 * @param aktualisiert der Status, ob die Angebote aktualisiert wurden.
		 * @param objects      die evtl. aktualisierten Einkaufslisten.
		 */
		private void sendBackToUeberwachung(jade.lang.acl.ACLMessage m, boolean aktualisiert, Object[] objects) {
			System.out.println("In sendback von AktualisierungsBeh");
			jade.lang.acl.ACLMessage msg = m.createReply();
			try {
				@SuppressWarnings("unchecked")
				HashMap<Integer, Einkaufsliste> l = (HashMap<Integer, Einkaufsliste>) objects[3];
				
				System.out.println("In try von sendback von AktualisierungsBeh");
				Object[] ob = {aktualisiert, new AktualisiereAngeboteAgent(), l};
				msg.setContentObject(ob);
				msg.setConversationId("UpdateAntwort");
				
				String str = "Agent : [ " + getName() + " ] hat Antwort [ " + msg + " ] "
						+ "vorbereitet und abgeschickt. \n"
						+ "KonversationID : [ " + msg.getConversationId() + " ]";
				send(msg);
				send(UeberwachungsAgent.sendToProtokollAgent(str, "0"));
				this.finished = true;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		/**
		 * private Methode, um ausgehend vom Sortiment zufaellige Artikelzu bestimmen,
		 * die in die Angebote aufgenommen werden sollen.
		 * 
		 * @param s         der Supermarkt, fuer den die Angebote erstellt werden.
		 * @param intervall Die Anzahl der Angebote insgesamt. Wird in
		 *                  {@code initAngebote()} berechnet.
		 * @return list die Liste mit zufaelligen Artikelnummern aus dem Sortiment.
		 */
		private HashMap<Integer, Integer> randomNumbers(Supermarkt s, int intervall) {
			HashMap<Integer, Integer> list = new HashMap<Integer, Integer>();
			int nr = 0;
			while(list.size() <= intervall) {
				nr = (int)(Math.random() * (s.getSortiment().size() - 2) + 2);
				list.put(nr, nr);
			}
			return list;
		
		}
		
		/**
		 * Diese Methode initialisiert die Angebote eines Supermarktes. Es wird eine
		 * zufaellige Anzahl von Produkten zufaellig in das Angebotsortiment
		 * aufgenommen.
		 * 
		 * @param supermarkt Der Supermarkt, fuer den die Angebote erstellt werden.
		 */
		public void initAngebote(String supermarkt) {
			Supermarkt s = null;
			switch(supermarkt) {
			case "Lidl": 
				s = new Lidl();
				s.initSortiment();
				break;
			case "Penny":
				s = new Penny();
				s.initSortiment();
				break;
			case "Rewe":
				s = new Rewe();
				s.initSortiment();
				break;
			case "Netto":
				s = new Netto();
				s.initSortiment();
				break;
				default:
					break;
			}
			
			//Mindestens 15 bis 30 Prozent des Sortiments sollen in das Angebot.
			double high = 0.3;
			double low = 0.15;
			int angeboteAnzahl = 0;
			
			//Zufaellige Festlegung der Anzahl von Angeboten.
			angeboteAnzahl = (int) (s.getSortiment().size() * (Math.random() * (high - low) + low));
			
			HashMap<Integer, Integer> random_artikelnr = randomNumbers(s, angeboteAnzahl);
			int[] arr = new int[random_artikelnr.size()];
			int counter = 0;
			
			//Bevor neue Angebote eingefuegt werden, wird der Inhalt der alten
			//Angebote Tabelle geloescht.
			LoescheAusDatenbank.loescheTabellenInhalt(s.getBezeichnung().toLowerCase(), "artikelnr");
			
			for(int key : random_artikelnr.keySet()) {
				arr[counter] = key;
				//System.out.println(arr[counter]);
				counter++;
			}
			
			//Anzahl richtet sich nach der Anzahl an angeboten.
			for(int i = 0; i <= angeboteAnzahl; i++) {
				
					//Fuer ein Produkt wird ein zufaelliger Rabatt berechnet.
					double rabatt = DoubleManager.round(RabattManager.getRandomRabatt(), 2);
					
					Food object = s.getSortiment().get(arr[i]);
					
					Food f = new Food(object.getBezeichnung(), 
							RabattManager.abzugRabatt(rabatt, object.getPreis()), 
							object.getHersteller(), object.getImage(),
							object.getVeggy(), object.getVegan(),
							object.getLokal(), object.getBio(),
							object.getKategorie());
					f.setArtikelNr(arr[i]);
					f.setOriginalPreis(object.getPreis());
					f.setRabatt(rabatt);
					
					switch(s.getBezeichnung()) {
					
					case "Lidl": 
						Lidl lidl = new Lidl();
						f.setUrsprungsmarkt(lidl.getUrsprungsID());
						break;
					case "Penny":
						Penny p = new Penny();
						f.setUrsprungsmarkt(p.getUrsprungsID());
						break;
					case "Rewe":
						Rewe r = new Rewe();
						f.setUrsprungsmarkt(r.getUrsprungsID());
						break;
					case "Netto":
						Netto n = new Netto();
						f.setUrsprungsmarkt(n.getUrsprungsID());
						break;
					default:
						break;
					}
					SpeicherInDatenbank.speicherAngeboteInDatenbank(s.getBezeichnung().toLowerCase(), f);
			}
		}
	}
}
