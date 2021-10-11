package general;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import database.transfer.LoescheAusDatenbank;
import database.transfer.SpeicherInDatenbank;
import databaseConnection.DBConnection;
import general.Food;
import general.supermarkets.Lidl;
import general.supermarkets.Netto;
import general.supermarkets.Penny;
import general.supermarkets.Rewe;
import managers.DoubleManager;
import managers.RabattManager;

public abstract class Supermarkt {
	
	protected String bez;
	
	private HashMap<Integer, Food> sortiment, angebote;
	
	/**
	 * Konsturktor der Klasse Supermarkt.
	 */
	protected Supermarkt() {	
		this.sortiment = new HashMap<Integer, Food>();
		this.angebote = new HashMap<Integer, Food>();
	}
	
	protected abstract void setBezeichnung();
	
	public void initSortiment() {
		Connection con = DBConnection.getConnection();
		ResultSet r;
		PreparedStatement stmt;
		
		try {
			 stmt = con.prepareStatement("select * from images "
					+ "LEFT JOIN " + getBezeichnung().toLowerCase() + 
					"_sortiment ON images.id = " + getBezeichnung().toLowerCase() + "_sortiment.image_id");
			
			//System.out.println(stmt);
			
			r = stmt.executeQuery();
			
			while(r.next()) {
				Food f = new Food(r.getString("artikelbez"), r.getDouble("artikelpreis"),
						r.getString("hersteller"), r.getString("pfad").substring(16), 
						r.getInt("vegan"), r.getInt("vegetarisch"), r.getInt("lokal"),
						r.getInt("bio"), r.getString("hersteller"));
				getSortiment().put(r.getInt("artikelNr"), f);
			}
			r.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	private HashMap<Integer, Integer> randomNumbers(int intervall) {
		HashMap<Integer, Integer> list = new HashMap<Integer, Integer>();
		int nr = 0;
		while(list.size() <= intervall) {
			nr = (int)(Math.random() * (getSortiment().size() - 2) + 2);
			list.put(nr, nr);
		}
		return list;
	
	}
	
	/**
	 * Diese Methode initialisiert die Angebote eines Supermarktes.
	 * Es wird eine zufaellige Anzahl von Produkten zufaellig in das 
	 * Angebotsortiment aufgenommen.
	 */
	public void initAngebote() {
		
		//Mindestens 15 bis 30 Prozent des Sortiments sollen in das Angebot.
		double high = 0.3;
		double low = 0.15;
		int angeboteAnzahl = 0;
		
		//Zufaellige Festlegung der Anzahl von Angeboten.
		angeboteAnzahl = (int) (getSortiment().size() * (Math.random() * (high - low) + low));
				
		HashMap<Integer, Integer> random_artikelnr = randomNumbers(angeboteAnzahl);
		int[] arr = new int[random_artikelnr.size()];
		int counter = 0;
		
		LoescheAusDatenbank.loescheTabellenInhalt(getBezeichnung().toLowerCase(), "artikelnr");
		
		for(int key : random_artikelnr.keySet()) {
			arr[counter] = key;
			//System.out.println(arr[counter]);
			counter++;
		}
		
		
		//Anzahl richtet sich nach der Anzahl an angeboten.
		for(int i = 0; i <= angeboteAnzahl; i++) {
			
				double rabatt = DoubleManager.round(RabattManager.getRandomRabatt(), 2);
				System.out.println(arr[i]);
				Food object = getSortiment().get(arr[i]);
				
				System.out.println(object.getBezeichnung());
				
				Food f = new Food(object.getBezeichnung(), 
						RabattManager.abzugRabatt(rabatt, object.getPreis()), 
						object.getHersteller(), object.getImage(),
						object.getVeggy(), object.getVegan(),
						object.getLokal(), object.getBio(),
						object.getKategorie());
				f.setArtikelNr(arr[i]);
				f.setOriginalPreis(object.getPreis());
				f.setRabatt(rabatt);
				
				switch(getBezeichnung()) {
				
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
				addAngebot(arr[i], f);
				addAngebotToDB(f);
		}
		
		for(Entry<Integer, Food> f : getAngebote().entrySet()) {
			System.out.println(f.getValue().getArtikelNr() + " " + f.getValue().getBezeichnung() + " " + f.getValue().getImage() + " " + f.getValue().getPreis());
		}
	}
	
	public HashMap<Integer, Food> getAngebote() {
		return angebote;
	}
	
	public String getBezeichnung() {
		return bez;
	}
	
	public Food getAngebotByKey(int key) {
		return angebote.get(key);
	}
	
	public void addAngebot(int key, Food f) {
		f.setArtikelNr(key);
		getAngebote().put(key, f);
	}
	
	public void removeAngebotByKey(int key) {
		angebote.remove(key);
	}
	
	public HashMap<Integer, Food> getSortiment() {
		return sortiment;
	}
	
	public void addProduktToSortiment(int key, Food f) {
		f.setArtikelNr(key);
		this.sortiment.put(key, f);
	}
	
	public Food getSortimentByKey(int key) {
		return sortiment.get(key);
	}
	
	public void removeSortimentByKey(int key) {
		sortiment.remove(key);
	}
	
	public void addAngebotToDB(Food f) {
		SpeicherInDatenbank.speicherAngeboteInDatenbank(getBezeichnung(), f);
	}
	
	public void removeAngeboteByKeyFromDB(int key) {
		LoescheAusDatenbank.loescheAngebotAusDB(getBezeichnung(), key);
	}
	
	public void removeSortimentByKeyFromDB(int key) {
		LoescheAusDatenbank.loescheSortimentAusDB(getBezeichnung(), key);
	}
}
