package agents;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
import managers.DoubleManager;
/**
 * Realisierung eines Angebote Agenten.
 * @author norman
 *
 */
public class AngeboteAgent extends Agent {
	//Attribute
	/**
	 * 
	 */
	private static final long serialVersionUID = -6592100288896507467L;
	
	private String name;
	
	/**
	 * Konstruktor der Klasse AngeboteAgent. Der {@code name} wird im Konsturktor
	 * auf 'AngebotAgent' gesetzt.
	 */
	public AngeboteAgent() {
		this.name = "AngebotAgent";
	}
	
	/**
	 * getter fuer den Namen des Agenten.
	 * 
	 * @return name der Name.
	 */
	public String getAgentName() {
		return name;
	}

	/**
	 * setup Methode des Agenten. Hier wird der Agent registriert.
	 */
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
	/**
	 * takeDown Methode um Agenten zu deregistrieren.
	 */
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}

	/**
	 * Private Klasse, um das Verhalten des Angebote Agenten zu realisieren.
	 * 
	 * @author norman
	 *
	 */
private class AngebotAgentBehaviour extends Behaviour {
	//Attribute
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
	private boolean finished = false;

	@Override
	public boolean done() {
		return finished;
	}
	
	@Override
	public void action() {
			
		String str1 = "Agent: [" + getName() + " ] ist  jetzt in der action() \n";
		String str = "";
		String conv_id = "";
			
		jade.lang.acl.ACLMessage msg = blockingReceive();
			
		if(msg != null) {
			conv_id = msg.getConversationId();
		} else {
			msg = blockingReceive();
		}
			
		if(msg != null && conv_id.equals("Anfrage")) {
				
			str1 += "Agent: [ " + getName() + " ] konnte Nachricht "
					+ "[ " + msg + " ] von [ " + msg.getSender().getName() + " ] empfangen. \n";
				
			jade.lang.acl.ACLMessage reply = msg.createReply();
				
			str1 += "Agent : [ " + getName() + " ] hat reply [ " + reply + " ] erstellt. \n";
				
			HashMap<Integer, Einkaufsliste> l = new HashMap<Integer, Einkaufsliste>();
			try {
				l = (HashMap<Integer, Einkaufsliste>) msg.getContentObject();
					
				if(l != null) {
					str1 += "Agent : [ "+ getName() + " ] konnte Liste mit Einkaufslisten von [ "
							+ msg.getSender().getName() + " ] entgegennehmen. \n";					
						
					jade.lang.acl.ACLMessage aktuell_anfrage = new jade.lang.acl.ACLMessage(
							jade.lang.acl.ACLMessage.REQUEST);
					aktuell_anfrage.addReceiver(new AID("UeberwachungAgent", AID.ISLOCALNAME));
					aktuell_anfrage.setContentObject(l);
					aktuell_anfrage.setConversationId("AufforderungAnUeberwachung");
					send(aktuell_anfrage);
					str1 += "KonversationID: [ " + aktuell_anfrage.getConversationId() + " ]";
						
					send(UeberwachungsAgent.sendToProtokollAgent(str1, "0"));
						
				}
			} catch (UnreadableException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(msg != null && conv_id.equals("UpdateVonUeberwachung")) {
						
			str = "Antwort [ " + msg + " ] von Ueberwachungsagent erhalten";
						
			Object[] o;
			try {
				o = (Object[]) msg.getContentObject();
				boolean aktualisiert = (boolean) o[0];
				@SuppressWarnings("unchecked")
				HashMap<Integer, Einkaufsliste> l = (HashMap<Integer, Einkaufsliste>) o[1];

				for(int i : l.keySet()) {
					str += "Agent: [ " + getName() + " ] prueft und erstellt Angebote fuer Einkaufsliste"
							+ " mit ID: [ " + l.get(i).getEinkaufslisteID() + " ] \n";
					this.einkaufsliste = l.get(i);
					Einkaufsliste liste = einkaufslisteMitAngeboten(i);
					l.put(i, liste);
				}
				jade.lang.acl.ACLMessage to_vergleichsagent = new jade.lang.acl.ACLMessage(
						jade.lang.acl.ACLMessage.INFORM); 

				to_vergleichsagent.addReceiver(new AID("Vergleichsagent", AID.ISLOCALNAME));
				to_vergleichsagent.setContentObject(l);
				to_vergleichsagent.setConversationId("ErgebnisVorliegend");
				send(to_vergleichsagent);
							
				str += "Nachricht [ " + to_vergleichsagent + " ] wurde erfolgreich"
						+ " vorbereitet und versendet \n"
						+ "Konversation ID [ " + to_vergleichsagent.getConversationId() +" ] \n"; 
				String end = str1 + "\n" + str;
				send(UeberwachungsAgent.sendToProtokollAgent(end, "0"));
				this.finished = true;
			} catch (UnreadableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			block();
		}
	}
		
	/**
	 * Rueckgabe des Wertes, wieviel gespart wurde.
	 * 
	 * @return der gesparte Wert auf zwei Nachkommastellen gerundet.
	 */
	public double getGespart() {
		return DoubleManager.round(gespart, 2);
	}

	/**
	 * Rueckgabe des Wertes, wieviel in Prozent gespart wurde.
	 * 
	 * @return der gesparte Prozentwert auf zwei Nachkommastellen gerundet.
	 */
	public double getErsparungInProzent() {
		return DoubleManager.round(ersparungInProzent, 2);
	}

	/**
	 * Rueckgabe eines Einkaufslistenobjekts.
	 * 
	 * @return einkaufsliste die Einkaufsliste zur Ruckgabe.
	 */
	private Einkaufsliste getEinkaufsliste() {
		return einkaufsliste;
	}

	/**
	 * private Methode, um die Angebote fuer einen Laden aus der Datenbank zu holen.
	 * 
	 * @param id die Laden id.
	 * @return angebote eine HashMap mit <Artikelnr, Produkt>
	 */
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

	/**
	 * private Methode, um einer Einkaufsliste die Angebote hinzufuegen zu koennen-
	 * 
	 * @param laden_id die ID des Ladens, fuer den die Angebote geholt werden.
	 * @return Die aktualisierte Einkaufsliste.
	 */
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

	/**
	 * private Methode, um ein Produkt, welches im Angebot ist, austauschen zu
	 * koennen.
	 * 
	 * @param l       Die Einkaufsliste, fuer die das Produkt getauscht werden soll.
	 * @param angebot Das Produkt, welches sich im Angebot befindet.
	 * @param alt     Das Produkt, welches ausgetauscht wird.
	 */
	private void tauscheEinkaufslisteProduktMitAngebotProdukt(Einkaufsliste l, Food angebot, Food alt) {
	
		l.getProduktliste().get(alt.getArtikelNr()).setOriginalPreis(alt.getPreis());
		
		l.getProduktliste().get(alt.getArtikelNr()).setPreis(angebot.getPreis());
		l.getProduktliste().get(angebot.getArtikelNr()).setImAngebot(true);
		
		l.getProduktliste().get(angebot.getArtikelNr()).berechneErsparnisFuerFood();
		
	}
	
	/**
	 * Methode, um das Produkt aus den Angeboten zwischenspeichern zu koennen.
	 * 
	 * @return angebotZwischenspeicher das angebot.
	 */
	private Food getAngebotZwischenspeicher() {
		return angebotZwischenspeicher;
	}

	/**
	 * Methode, um ein Produkt aus dem sortiment zwischenspeichern zu koennen.
	 * 
	 * @return sortimentZwischenspeicher das sortimentprodukt.
	 */
	private Food getSortimentZwischenspeicher() {
		return sortimentZwischenspeicher;
	}

	/**
	 * Diese Methode gibt den Endpreis einer Einkaufsliste zurueck.
	 * 
	 * @return der Endpreis gerundet auf zwei Nachkommastellen.
	 */
	private double getPreisEndergebnis() {
		return DoubleManager.round(preisEndergebnis, 2);
	}

	/**
	 * Methode, um die Ersparung einer Einkaufsliste berechnen zu koennen.
	 * 
	 * @param preis_alt der alte gesamtpreis der Einkaufsliste.
	 * @param preis_neu der neue Gesamtpreis der Einkaufsliste
	 * @return der alte preis minus des neuen preises.
	 */
	private double berechneErsparung(double preis_alt, double preis_neu) {
		return preis_alt - preis_neu;
		
	}

	/**
	 * Methode, um die Ersparnis in Prozent berechnen zu koennen. Der Gesparte
	 * Betrag * 100 durch den Endpreis.
	 */
	private void berechneErsparungInProzent() {
		this.ersparungInProzent = (getGespart() * 100) / getPreisEndergebnis();
	}	
}	
}
