package general;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import databaseConnection.DBConnection;

/**
 * Klasse zum Realisieren der Eigenschaften eines Supermarkts.
 * @author norman
 *
 */
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
	/**
	 * abstract, da jeder Supermarkt eine andere Bezeichnung implementiert.
	 */
	protected abstract void setBezeichnung();
	
	/**
	 * Methode, um Sortiment fuer einen Supermarkt zu initialisieren.
	 */
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
	/**
	 * Methode, um Angebote fuer diesen Supermarkt aus der DB zu holen.
	 */
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

	/**
	 * Rueckgabe der Angebote.
	 * 
	 * @return angebote
	 */
	public HashMap<Integer, Food> getAngebote() {
		return angebote;
	}

	/**
	 * Rueckgabe der Bezeichnung.
	 * 
	 * @return bez
	 */
	public String getBezeichnung() {
		return bez;
	}

	/**
	 * Rueckgabe eines Angebotes fuer die Artikelnr.
	 * 
	 * @param key die artikelnr.
	 * @return das angebot
	 */
	public Food getAngebotByKey(int key) {
		return angebote.get(key);
	}

	/**
	 * Methode, um Anbgebote hinzufuegen zu koennen.
	 * 
	 * @param key die Artikelnr.
	 * @param f   das Produkt.
	 */
	public void addAngebot(int key, Food f) {
		f.setArtikelNr(key);
		getAngebote().put(key, f);
	}

	/**
	 * Methode, um Angebot mit artikenr zu entfernen.
	 * 
	 * @param key die artikelnr des Produktes.
	 */
	public void removeAngebotByKey(int key) {
		angebote.remove(key);
	}

	/**
	 * Rueckgabe des Sortiments.
	 * 
	 * @return sortiment
	 */
	public HashMap<Integer, Food> getSortiment() {
		return sortiment;
	}

	/**
	 * Hinzufuegen eines Produktes zum Sortiment.
	 * 
	 * @param key die Artikelnr.
	 * @param f   das Produkt.
	 */
	public void addProduktToSortiment(int key, Food f) {
		f.setArtikelNr(key);
		this.sortiment.put(key, f);
	}

	/**
	 * Rueckgabe des Sortiments durch die Akrtikelnr.
	 * 
	 * @param key die artikelnr.
	 * @return das produkt.
	 */
	public Food getSortimentByKey(int key) {
		return sortiment.get(key);
	}

	/**
	 * Methode zum loeschen eines Produkts durch akrtikelnr.
	 * 
	 * @param key die artikelnr.
	 */
	public void removeSortimentByKey(int key) {
		sortiment.remove(key);
	}
}
