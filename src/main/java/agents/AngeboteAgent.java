package agents;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import databaseConnection.DBConnection;
import general.Einkaufsliste;
import general.Food;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import managers.DoubleManager;

public class AngeboteAgent extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6592100288896507467L;
	
	private HashMap<Integer, Einkaufsliste> einkaufslisten;
	private int laden_id;
	private String name;

	public AngeboteAgent() {
		this.name = "AngebotAgent";
	}
	
	public String getAgentName() {
		return name;
	}
	
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setName("AngebotAgent");		
		sd.setType("Angebot Agent");
		desc.addServices(sd);
		
		try {
			DFService.register(this, desc);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
		
		addBehaviour(new AngebotAgentBehaviour());
	}
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}
	

	
	private class AngebotAgentBehaviour extends OneShotBehaviour {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3312816331448840101L;
		private Food angebotZwischenspeicher;
		private Food sortimentZwischenspeicher;
		private Einkaufsliste einkaufsliste;

		private double preisEndergebnis;
		private double gespart;
		private double ersparungInProzent;
		
		public AngebotAgentBehaviour() {
			
		}
		
		@Override
		public void action() {
			
			String str1 = "Agent: [" + getName() + " ] ist  jetzt in der action()";
			String str2 = "";
			String str3 = "";
			String str4 = "";
			String str5 = "";
			String str6 = "";
			
			jade.lang.acl.ACLMessage msg = blockingReceive();

			
			if(msg != null) {
				
				str2 = "Agent: [ " + getName() + " ] konnte Nachricht "
						+ "[ " + msg + " ] von [ " + msg.getSender().getName() + " ] empfangen.";
				
				jade.lang.acl.ACLMessage reply = msg.createReply();
				
				str3 = "Agent : [ " + getName() + " ] hat reply [ " + reply + " ] erstellt.";
				
				HashMap<Integer, Einkaufsliste> l = new HashMap<Integer, Einkaufsliste>();
				try {
					l = (HashMap<Integer, Einkaufsliste>) msg.getContentObject();
					
					if(l != null) {
						str4 = "Agent : [ "+ getName() + " ] konnte Liste mit Einkaufslisten von [ "
								+ msg.getSender().getName() + " ] entgegennehmen.";					
					

						for(int i : l.keySet()) {
							str5 += "Agent: [ " + getName() + " ] prueft und erstellt Angebote fuer Einkaufsliste"
									+ " mit ID: [ " + l.get(i).getEinkaufslisteID() + " ]";
							this.einkaufsliste = l.get(i);

							Einkaufsliste liste = einkaufslisteMitAngeboten(i);
							l.put(i, liste);
						}
						
						reply.setContentObject(l);
						
						str6 = "Antwort auf verfuegbare angebote lautet: [ " + reply.getContent() + " ]"
								+ " und Object [ " + reply.getContentObject() + " ] konnte [ " + reply + " ] hinzugefuegt werden.";
						
						send(reply);
						
						ArrayList<String> s = new ArrayList<String>();
						
						s.add(str1); s.add(str2); s.add(str3); s.add(str4); s.add(str5); s.add(str6);
						
						Object[] objects = {s, "0"};
						
						jade.lang.acl.ACLMessage send_to_protocoll = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
						send_to_protocoll.setContentObject(objects);
						send_to_protocoll.addReceiver(new AID("ProtokollAgent", AID.ISLOCALNAME));
						send(send_to_protocoll);
						
					}
				} catch (UnreadableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				System.out.println("Noch keine Message erhalten....");
				block();
			}
		}
		
		
		
		public double getGespart() {
			return gespart;
		}
		
		public double getErsparungInProzent() {
			return DoubleManager.round(ersparungInProzent, 2);
		}

		
		private Einkaufsliste getEinkaufsliste() {
			return einkaufsliste;
		}
		
		private HashMap<Integer, Food> holeAngebote(int id) {
			HashMap<Integer, Food> angebote = new HashMap<Integer, Food>();
			
			Connection con = DBConnection.getConnection();
			String laden = "";
			try {
				
				
				PreparedStatement hole_laden = con.prepareStatement("select bez from laeden where laden_id = ?");
				hole_laden.setInt(1, id);
				
				ResultSet r = hole_laden.executeQuery();
				
				while(r.next()) {
					laden = r.getString("bez");
				}
				
				PreparedStatement stmt = con.prepareStatement("select * from " + laden + "_angebote");
				
				ResultSet r2 = stmt.executeQuery();
				
				while(r2.next()) {
					Food f = new Food(r2.getString("artikelbez"), r2.getDouble("artikelpreis"),
							r2.getString("hersteller"), "", r2.getInt("vegan"), r2.getInt("vegetarisch"), r2.getInt("lokal"),
							r2.getInt("bio"), r2.getString("kategorie"));
					f.setArtikelNr(r2.getInt("artikelnr"));
					angebote.put(f.getArtikelNr(),f);
				}
				stmt.close();
				hole_laden.close();
				r2.close();
				r.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return angebote;
		}
		
		private Einkaufsliste einkaufslisteMitAngeboten(int laden_id) {
		
			HashMap<Integer, Food> angebot = holeAngebote(laden_id);
			HashMap<Integer, Food> sortiment = getEinkaufsliste().getProduktliste();
			
			getEinkaufsliste().berechneGesamtpreis();
			
			double preis_alt = getEinkaufsliste().getGesamtPreis();
			boolean imAngebot = false;
			
			for(int i : sortiment.keySet()) {
			
					int artikelnr = i;

					if ((sortiment.get(artikelnr) != null) && (angebot.get(artikelnr) != null)) {
						imAngebot = true;
						
						this.angebotZwischenspeicher = angebot.get(artikelnr);
						this.sortimentZwischenspeicher = sortiment.get(artikelnr);

						this.angebotZwischenspeicher.setImAngebot(imAngebot);
						this.sortimentZwischenspeicher.setImAngebot(imAngebot);

						if(imAngebot) {
							tauscheEinkaufslisteProduktMitAngebotProdukt(getEinkaufsliste(), getAngebotZwischenspeicher(), getSortimentZwischenspeicher());
						}
					}
				}
			
			getEinkaufsliste().berechneGesamtpreis();
			this.preisEndergebnis = getEinkaufsliste().getGesamtPreis();
			this.gespart = berechneErsparung(preis_alt, getPreisEndergebnis());
			
			berechneErsparungInProzent();
			
			getEinkaufsliste().setErsparnisInProzent(getErsparungInProzent());
			getEinkaufsliste().setErsparnis(getGespart());
			
			return getEinkaufsliste();
		}
		
		private void tauscheEinkaufslisteProduktMitAngebotProdukt(Einkaufsliste l, Food angebot, Food alt) {
		
			l.getProduktliste().get(alt.getArtikelNr()).setOriginalPreis(alt.getPreis());
			
			l.getProduktliste().get(alt.getArtikelNr()).setPreis(angebot.getPreis());
		}
		
		private Food getAngebotZwischenspeicher() {
			return angebotZwischenspeicher;
		}
		
		private Food getSortimentZwischenspeicher() {
			return sortimentZwischenspeicher;
		}
		
		private double getPreisEndergebnis() {
			return DoubleManager.round(preisEndergebnis, 2);
		}
		
		private double berechneErsparung(double preis_alt, double preis_neu) {
			return preis_alt - preis_neu;
			
		}
		
		private void berechneErsparungInProzent() {
			this.ersparungInProzent = (getGespart() * 100) / getPreisEndergebnis();
		}	
	}	
}
