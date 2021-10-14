package agents;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import databaseConnection.DBConnection;
import general.Einkaufsliste;
import general.Food;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.introspection.ACLMessage;
import managers.DoubleManager;
import jade.lang.acl.*;

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
	

	
	private class AngebotAgentBehaviour extends Behaviour {
		
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
		private int laden_id;
		boolean finished = false;
		
		public AngebotAgentBehaviour() {
			
		}
		
		@Override
		public void action() {
			jade.lang.acl.ACLMessage msg = receive();
			
			System.out.println(msg == null);
			
			if(msg != null) {
				String title = msg.getContent();
				System.out.println(title);
				System.out.println("Bin jetzt in action von AngebotAgent");
				
				jade.lang.acl.ACLMessage reply = msg.createReply();
				
				HashMap<Integer, Einkaufsliste> l = new HashMap<Integer, Einkaufsliste>();
				try {
					l = (HashMap<Integer, Einkaufsliste>) msg.getContentObject();

					for(int i : l.keySet()) {
						Einkaufsliste liste = einkaufslisteMitAngeboten(i);
						l.put(i, liste);
					}
				} catch (UnreadableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					reply.setContentObject((Serializable) l );
					reply.setContent("yes");
					send(reply);
					finished = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} 
			
		}
		
		@Override
		public boolean done() {
			
			return finished;
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
		
		private int getLadenID() {
			return laden_id;
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
