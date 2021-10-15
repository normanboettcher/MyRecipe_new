package general;

import java.io.Serializable;
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

public abstract class Supermarkt implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3796200850152707931L;

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
	
	public void holeAngebote() {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from " + getBezeichnung()
					+ "_angebote");
			ResultSet r = stmt.executeQuery();
			Food f = null;
			while(r.next()) {
				f = new Food(r.getString("artikelbez"), r.getDouble("artikelpreis"), 
						r.getString("hersteller"), r.getString("image_pfad"), r.getInt("vegetarisch"), r.getInt("vegan"),
						r.getInt("lokal"), r.getInt("bio"), r.getString("kategorie"));
				f.setArtikelNr(r.getInt("artikelnr"));
				addAngebot(f.getArtikelNr(), f);
			}
			stmt.close();
			r.close();
		} catch(SQLException e) {
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
	
	public void removeAngeboteByKeyFromDB(int key) {
		LoescheAusDatenbank.loescheAngebotAusDB(getBezeichnung(), key);
	}
	
	public void removeSortimentByKeyFromDB(int key) {
		LoescheAusDatenbank.loescheSortimentAusDB(getBezeichnung(), key);
	}
}
