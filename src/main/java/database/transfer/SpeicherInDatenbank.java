package database.transfer;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import databaseConnection.DBConnection;
import general.Einkaufsliste;
import general.Food;
import general.User;
import general.supermarkets.Rezepte;
import managers.IdGenerator;

/**
 * Klase, um Methoden zum Speichern in der DB zu realisieren.
 * 
 * @author norman
 *
 */
public class SpeicherInDatenbank {
	
	/**
	 * Methode um einen komplett neuen User in der Datenbank abzulegen.
	 * 
	 * @param usr der neue User.
	 */
	public static void speicherUserInDatenbank(User usr) {

		Connection con = DBConnection.getConnection();
		
		try {
			String speicherUserQuery = "INSERT INTO users (id, email, passwort,"
					+ "vorname, nachname,"
					+ "strasse, hausnummer, plz, ort)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			PreparedStatement schreibeUserInDatenbank = con.prepareStatement(speicherUserQuery);	
			
			schreibeUserInDatenbank.setInt(1, usr.getID());
			schreibeUserInDatenbank.setString(2, usr.getEmail());
			schreibeUserInDatenbank.setString(3, usr.getPasswort());
			schreibeUserInDatenbank.setString(4, usr.getVorname());
			schreibeUserInDatenbank.setString(5, usr.getNachname());
			schreibeUserInDatenbank.setString(6, usr.getAdresse().getStreet());
			schreibeUserInDatenbank.setString(7, usr.getAdresse().getNumber());
			schreibeUserInDatenbank.setString(8, usr.getAdresse().getPLZ());
			schreibeUserInDatenbank.setString(9, usr.getAdresse().getCity());
			schreibeUserInDatenbank.executeUpdate();
			schreibeUserInDatenbank.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Methode um Bild in DB zu speichern.
	 * 
	 * @param laden der Laden, fuer den das Bild gespeichert wird.
	 * @param f     der Name des Bildes.
	 */
	public static void speicherBildInDatenbank(String laden, File f) {
		Connection con = DBConnection.getConnection();
		
		try {
			String speicherBildQuery = "INSERT INTO images (id, pfad)"
					+ "VALUES"
					+ "(?, ?)";
			PreparedStatement schreibeBildInDatenbank = con.prepareStatement(speicherBildQuery);
			
			schreibeBildInDatenbank.setInt(1, IdGenerator.generiereBildID());
			schreibeBildInDatenbank.setString(2, "IMG/products/" 
			+ laden + "/" + f.getName());
			schreibeBildInDatenbank.executeUpdate();
			schreibeBildInDatenbank.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode zum Speichern der Angebote in der DB.
	 * 
	 * @param laden der Laden fuer den die Angebote gespeichert werden.
	 * @param f     Das Produkt, das in der DB gespeichert wird.
	 */
	public static void speicherAngeboteInDatenbank(String laden, Food f) {
		Connection con = DBConnection.getConnection();
		
		try {
			String speicherAngebot = "insert into " + laden + "_angebote "
					+ "values"
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = con.prepareStatement(speicherAngebot);
			
			
			stmt.setInt(1, f.getArtikelNr());
			stmt.setString(2, f.getBezeichnung());
			stmt.setDouble(3, f.getPreis());
			stmt.setString(4, f.getHersteller());
			stmt.setString(5, f.getKategorie());
			stmt.setInt(6, f.getVegan());
			stmt.setInt(7, f.getVeggy());
			stmt.setInt(8, f.getLokal());
			stmt.setInt(9, f.getBio());
			stmt.setString(10, f.getImage());
			stmt.setInt(11, f.getUrpsungsmarktID());		
			stmt.executeUpdate();
			stmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode, um Einkaufslisten in der DB abzulegen.
	 * 
	 * @param l Die zu speichernde Einkaufsliste.
	 */
	public static void speicherEinkaufslisteInDatenbank(Einkaufsliste l) {
		Connection con = DBConnection.getConnection();
		
		
		try {
			String speicherAngebot = "insert into einkauf_historie " 
					+ "values "
					+ "(?,?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = con.prepareStatement(speicherAngebot);
			
		
				stmt.setInt(5,l.getEinkaufslisteID());
				stmt.setDate(1, l.getEinkaufslisteDate());
				stmt.setString(2, l.getUser().getFullName());
				stmt.setDouble(3, l.getGesamtPreis());
				stmt.setInt(4, l.getUser().getID());
				for(int i : l.getProduktliste().keySet()) {
					stmt.setInt(6, l.getProduktliste().get(i).getUrpsungsmarktID());
				}
			
			stmt.executeUpdate();
			stmt.close();
			
			speicherZutatenAusEinkaufslisteInDB(l);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode, um Zutaten aus einer Einkaufsliste in der DB abzulegen.
	 * 
	 * @param l Die Einkaufsliste.
	 */
	private static void speicherZutatenAusEinkaufslisteInDB(Einkaufsliste l) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into produkte_aus_einkaufsliste "
					+ "values"
					+ "(?, ?, ?, ?, ?, ?, ?)");
			
			
			for(int i : l.getProduktliste().keySet()) {
				stmt.setDate(1, l.getEinkaufslisteDate());
				stmt.setInt(2, l.getUser().getID());
				stmt.setInt(3, l.getProduktliste().get(i).getArtikelNr());
				stmt.setInt(4, l.getProdukteMitMenge().get(i));
				stmt.setInt(5, l.getEinkaufslisteID());
				stmt.setInt(6, l.getProduktliste().get(i).getUrpsungsmarktID());
				stmt.setDouble(7, l.getProduktliste().get(i).getPreis());
				stmt.executeUpdate();
			}
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode, um Zutaten aus den Rezepten in der DB abzulegen.
	 * 
	 * @param r das Rezept.
	 */
	public static void speicherZutatenAusRezept(Rezepte r) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into zutaten_aus_rezepte "
					+ "values"
					+ "(?, ?, ?)");
			
			for(int i : r.getZutaten().keySet()) {
				stmt.setInt(1, r.getRezepte_id());
				stmt.setInt(2, i);
				stmt.setInt(3, r.getZutaten().get(i));
				stmt.executeUpdate();
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
