package database.transfer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.DBConnection;
import general.Einkaufsliste;
import general.Food;
import general.User;
import managers.IdGenerator;

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
	
	public static void speicherAngeboteInDatenbank(String laden, Food f) {
		Connection con = DBConnection.getConnection();
		
		try {
			String speicherAngebot = "insert into " + laden + "_angebote "
					+ "values"
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = con.prepareStatement(speicherAngebot);
			
			
			stmt.setInt(1, IdGenerator.generiereAngeboteID(laden));
			stmt.setString(2, f.getBezeichnung());
			stmt.setDouble(3, f.getPreis());
			stmt.setString(4, f.getHersteller());
			stmt.setString(5, f.getKategorie());
			stmt.setInt(6, f.getVegan());
			stmt.setInt(7, f.getVeggy());
			stmt.setInt(8, f.getLokal());
			stmt.setInt(9, f.getBio());
			stmt.setString(10, f.getImage());
			
			stmt.executeUpdate();
			stmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void speicherEinkaufslisteInDatenbank(Einkaufsliste l) {
		Connection con = DBConnection.getConnection();
		
		try {
			String speicherAngebot = "insert into ? " 
					+ "values"
					+ "(?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = con.prepareStatement(speicherAngebot);
			
			stmt.setString(1, "einkauf_historie");
			stmt.setInt(6, IdGenerator.generiereEinkaufslistenID());
			stmt.setDate(2, l.getEinkaufslisteDate());
			stmt.setString(3, l.getUser().getFullName());
			stmt.setDouble(4, l.getGesamtPreis());
			stmt.setInt(5, l.getUser().getID());
			
			stmt.executeUpdate();
			stmt.close();
			
			speicherZutatenAusEinkaufslisteInDB(l);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void speicherZutatenAusEinkaufslisteInDB(Einkaufsliste l) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into ? "
					+ "values"
					+ "(?, ?, ?, ?, ?)");
			
			stmt.setString(1, "produkte_aus_einkaufsliste");
			
			for(int i : l.getProduktliste().keySet()) {
				stmt.setDate(2, l.getEinkaufslisteDate());
				stmt.setInt(3, l.getUser().getID());
				stmt.setInt(4, l.getProduktliste().get(i).getArtikelNr());
				stmt.setInt(5, l.getProdukteMitMenge().get(i));
				stmt.setInt(6, l.getEinkaufslisteID());
				stmt.executeUpdate();
			}
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	
		
		Connection con = DBConnection.getConnection();
		
		
		
	}
}
