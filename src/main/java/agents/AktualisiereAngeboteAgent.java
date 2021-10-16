package agents;


import java.io.IOException;
import java.util.ArrayList;
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
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.UnreadableException;
import managers.DoubleManager;
import managers.RabattManager;

public class AktualisiereAngeboteAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4215772131075835095L;
	private String name;
	private boolean aktualisiert = false;
	
	public AktualisiereAngeboteAgent() {
		this.name = "AktualisiereAngeboteAgent";
	}
	
	public String getAgentName() {
		return name;
	}
	
	private boolean getAktualisiert() {
		return aktualisiert;
	}
	
	private void setAktualisiert(boolean t) {
		this.aktualisiert = t;
	}
	
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
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}
	
	private class AktualisierungsBehavior extends Behaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2801746822033209525L;
		
		private boolean finished = false;
		
		@Override
		public void action() {
			String conv_id = "";
			
			jade.lang.acl.ACLMessage msg = blockingReceive();
				
			UeberwachungsAgent ue_agent;
			
			if(msg != null) {
				conv_id = msg.getConversationId();
			} else {
				block();
			}
			
			try {
				if(msg != null && conv_id.equals("UpdateAnfrage")) {
				Object[] objects = (Object[]) msg.getContentObject();
				ue_agent = (UeberwachungsAgent) objects[0];
				
					Supermarkt[] maerkte = (Supermarkt[]) objects[1];
					int status = (int) objects[2];
					
					if(status == 1) {

						for(int i = 0; i < maerkte.length; i++) {
							Supermarkt s = maerkte[i];
							
							initAngebote(s.getBezeichnung());
						}
						setAktualisiert(true);
						sendBackToUeberwachung(msg, getAktualisiert(), objects);
					} else {
						setAktualisiert(false);
						sendBackToUeberwachung(msg, getAktualisiert(), objects);
					}
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
				
		private void sendBackToUeberwachung(jade.lang.acl.ACLMessage m,boolean aktualisiert, Object[] objects) {
			System.out.println("In sendback von AktualisierungsBeh");
			jade.lang.acl.ACLMessage msg = m.createReply();
			try {
				HashMap<Integer, Einkaufsliste> l = (HashMap<Integer, Einkaufsliste>) objects[3];
				
				System.out.println("In try von sendback von AktualisierungsBeh");
				Object[] ob = {aktualisiert, new AktualisiereAngeboteAgent(), l};
				msg.setContentObject(ob);
				msg.setConversationId("UpdateAntwort");
				
				ArrayList<String> s = new ArrayList<>();
				s.add("Agent: [ " + getName() + " ] Sende Hinweis zur Aktualisierung zurueck.");
				s.add("Angebote wurden Aktualisiert: [ " + aktualisiert + " ]");
				Object[] o = {s, "0"};
				
				jade.lang.acl.ACLMessage to_protocoll = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
				to_protocoll.addReceiver(new AID("ProtokollAgent", AID.ISLOCALNAME));
				to_protocoll.setContentObject(o);
				send(to_protocoll);
				send(msg);
				this.finished = true;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
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
		 * Diese Methode initialisiert die Angebote eines Supermarktes.
		 * Es wird eine zufaellige Anzahl von Produkten zufaellig in das 
		 * Angebotsortiment aufgenommen.
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
			
			LoescheAusDatenbank.loescheTabellenInhalt(s.getBezeichnung().toLowerCase(), "artikelnr");
			
			for(int key : random_artikelnr.keySet()) {
				arr[counter] = key;
				//System.out.println(arr[counter]);
				counter++;
			}
			
			
			//Anzahl richtet sich nach der Anzahl an angeboten.
			for(int i = 0; i <= angeboteAnzahl; i++) {
				
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
