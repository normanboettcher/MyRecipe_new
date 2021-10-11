package agents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import databaseConnection.DBConnection;
import general.Einkaufsliste;
import general.Food;
import managers.DoubleManager;

public class AngeboteAgent {
	
	private Food angebotZwischenspeicher;
	private Food sortimentZwischenspeicher;
	private double sortimentPreisZwischenspeicher;
	private double preisEndergebnis;
	private double gespart;
	private double ersparungInProzent;
	
	public AngeboteAgent() {
		
	}
	
	public Food getAngebotZwischenspeicher() {
		return angebotZwischenspeicher;
	}
	
	public Food getSortimentZwischenspeicher() {
		return sortimentZwischenspeicher;
	}
	
	public double getSortimentPreisZwischenspeicher() {
		return DoubleManager.round(sortimentPreisZwischenspeicher, 2);
	}
	
	public double getPreisEndergebnis() {
		return DoubleManager.round(preisEndergebnis, 2);
	}
	
	public double getGespart() {
		return gespart;
	}
	
	public double getErsparungInProzent() {
		return DoubleManager.round(ersparungInProzent, 2);
	}
	
	private HashMap<Integer, Food> holeSortiment(int id) {
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
			
			PreparedStatement stmt = con.prepareStatement("select * from " + laden + "_sortiment");
			
			ResultSet r2 = stmt.executeQuery();
			
			while(r2.next()) {
				Food f = new Food(r2.getString("artikelbez"), r2.getDouble("artikelpreis"),
						r2.getString("hersteller"), "", r2.getInt("vegan"), r2.getInt("vegetarisch"), r2.getInt("lokal"),
						r2.getInt("bio"), r2.getString("kategorie"));
				f.setArtikelNr(r2.getInt("artikelnr"));
				angebote.put(r2.getInt("artikelnr"),f);
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
	
	public Einkaufsliste produktImAngebot(int laden_id, Einkaufsliste l) {
	
		HashMap<Integer, Food> angebot = holeAngebote(laden_id);
		HashMap<Integer, Food> sortiment = l.getProduktliste();
		
		boolean imAngebot = false;
		
		for(int i : sortiment.keySet()) {
		
				int artikelnr = i;
				System.out.println(artikelnr);

				if ((sortiment.get(artikelnr) != null) && (angebot.get(artikelnr) != null)) {
					imAngebot = true;
					
					System.out.println("Angebot: " + angebot.get(artikelnr).getBezeichnung());
					System.out.println("Angebot preis : " + angebot.get(artikelnr).getPreis());

					System.out.println("Sortiment: "  + sortiment.get(artikelnr).getBezeichnung());
					System.out.println("Sortiment preis: " +sortiment.get(artikelnr).getPreis());

					this.angebotZwischenspeicher = angebot.get(artikelnr);
					this.sortimentZwischenspeicher = sortiment.get(artikelnr);

					this.angebotZwischenspeicher.setImAngebot(imAngebot);
					this.sortimentZwischenspeicher.setImAngebot(imAngebot);

					if(imAngebot) {
						tauscheEinkaufslisteProduktMitAngebotProdukt(l, getAngebotZwischenspeicher(), getSortimentZwischenspeicher());
						berechneErsparung(l);
					}
				}
			}
		return l;
	}
	
	private void tauscheEinkaufslisteProduktMitAngebotProdukt(Einkaufsliste l, Food angebot, Food alt) {
		
		
		System.out.println("----------------------");
		l.getProduktliste().get(alt.getArtikelNr()).setOriginalPreis(alt.getPreis());
		
		l.getProduktliste().get(alt.getArtikelNr()).setPreis(angebot.getPreis());
	}
	
	
	
	private void berechneErsparung(Einkaufsliste l) {
		
		for(Food f : l.getProduktliste().values()) {
			this.preisEndergebnis += f.getPreis() * l.getProdukteMitMenge().get(f.getArtikelNr());
		}
		
		this.gespart = DoubleManager.round(getSortimentPreisZwischenspeicher() - getPreisEndergebnis(), 2);
		berechneErsparungInProzent();
		l.setErsparnis(getGespart());
		l.setErsparnisInProzent(getErsparungInProzent());
	}
	
	private void berechneErsparungInProzent() {
		this.ersparungInProzent = (getGespart() * 100) / getPreisEndergebnis();
	}
	
}
