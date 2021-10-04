package general;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

import databaseConnection.DBConnection;
import general.Food;

public abstract class Supermarkt {
	
	protected String bez;
	
	private HashMap<Integer, Food> sortiment, angebote;
	
	protected Supermarkt() {	
		this.sortiment = new HashMap<Integer, Food>();
		this.angebote = new HashMap<Integer, Food>();
	}
	
	protected abstract void setBezeichnung();
	
	protected abstract void initSortiment();
	
	public void initAngebote() {
		Connection con = DBConnection.getConnection();
		//Mindestens 15 bis 30 Prozent des Sortiments sollen ins Angebot
		double high = 0.3;
		double low = 0.15;
		int angeboteAnzahl = 0;
		
		do {
			angeboteAnzahl = (int) (getSortiment().size() * (Math.random() * (high - low) + low));
		} while(angeboteAnzahl == 0);
		
		PreparedStatement stmt; 
		ResultSet r;
		
		for(int i = 0; i <= angeboteAnzahl; i++) {
			try {
				stmt = con.prepareStatement("select * from ?_sortiment where artikelnr = ?");
				stmt.setString(1,getBezeichnung().toLowerCase());
				//Zufaellig werden Produkte aus dem Sortiment gewählt.
				stmt.setInt(2, (int)(Math.random() * (getSortiment().size() - 2) + 2));
				
				r = stmt.executeQuery();
				
				Food f = new Food(r.getString("artikelbez"), r.getDouble("artikelpreis"),
						r.getString("hersteller"), r.getString("pfad").substring(16), 
						r.getInt("vegan"), r.getInt("vegetarisch"), r.getInt("lokal"),
						r.getInt("bio"), r.getString("hersteller"));
				r.close();
				stmt.close();
				
				addAngebot(i, f);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
		
		
		angebote.put(key, f);
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
