package general;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

import database.transfer.LoescheAusDatenbank;
import database.transfer.SpeicherInDatenbank;
import databaseConnection.DBConnection;
import general.Food;
import managers.DoubleManager;

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
	
	/**
	 * Methode um den Rabatt für ein Produkt zufaellig festzulegen.
	 * @return Eine zufaellige Zahl zwischen 0.15 und 0.5. 
	 *  Dies entspricht einem Bereich von 15 bis 50 % Rabatt.
	 */
	private double getRabatt() {
		
		return (Math.random() * (0.5 - 0.15) + 0.15);
	}
	
	/**
	 * Mit dieser Methode wird dem Originalpreis der gegebene Rabatt abgezogen.
	 * @param rabatt der aktuelle zufaellige Rabatt.
	 * @param originpreis der Originalpreis des Produktes.
	 * @return double-value originalpreis abzueglich des Rabatts.
	 */
	private double abzugRabatt(double rabatt, double originpreis) {
		return DoubleManager.round(originpreis - (originpreis * rabatt), 2);
	}
	
	/**
	 * Diese Methode initialisiert die Angebote eines Supermarktes.
	 * Es wird eine zufaellige Anzahl von Produkten zufaellig in das 
	 * Angebotsortiment aufgenommen.
	 */
	public void initAngebote() {
		
		//Wenn bereits Angebote existieren, werden sie komplett durch neue ersetzt.
		LoescheAusDatenbank.loescheTabellenInhalt(getBezeichnung().toLowerCase() + "_angebote");
		
		//Mindestens 15 bis 30 Prozent des Sortiments sollen in das Angebot.
		double high = 0.3;
		double low = 0.15;
		int angeboteAnzahl = 0;
		
		//Zufaellige Festlegung der Anzahl von Angeboten.
		angeboteAnzahl = (int) (getSortiment().size() * (Math.random() * (high - low) + low));
		
		//Anzahl richtet sich nach der Anzahl an angeboten.
		for(int i = 0; i <= angeboteAnzahl; i++) {
				int nr = (int)(Math.random() * (getSortiment().size() - 2) + 2);	
				//System.out.println(nr);
				double rabatt = DoubleManager.round(getRabatt(), 2);
				Food object = getSortiment().get(nr);
				
				Food f = new Food(object.getBezeichnung(), 
						abzugRabatt(rabatt, object.getPreis()), 
						object.getHersteller(), object.getImage(),
						object.getVeggy(), object.getVegan(),
						object.getLokal(), object.getBio(),
						object.getKategorie());
				
				f.setOriginalPreis(object.getPreis());
				f.setRabatt(rabatt);
				addAngebot(i + 1, f);
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
		
		SpeicherInDatenbank.speicherAngeboteInDatenbank(getBezeichnung(), f);
		
		getAngebote().put(key, f);
	}
	
	public void removeAngebotByKey(int key) {
		angebote.remove(key);
	}
	
	public HashMap<Integer, Food> getSortiment() {
		return sortiment;
	}
	
	public void addProdukt(int key, Food f) {
		this.sortiment.put(key, f);
	}
	
	public Food getSortimentByKey(int key) {
		return sortiment.get(key);
	}
	
	public void removeSortimentByKey(int key) {
		sortiment.remove(key);
	}
	
	

}
