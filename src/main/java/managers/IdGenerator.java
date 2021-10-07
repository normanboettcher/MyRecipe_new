package managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.DBConnection;

/**
 * Diese Klasse definiert einen ID-Geerator, der fuer das erstelle der ID's von
 * Ojekten zustaendig ist, die eine ID benoetigen.
 * 
 * @author Norman Boettcher
 *
 */
public class IdGenerator {
	/**
	 * Diese Methode erstellt eine Rechnungs-Id fuer eine neu erstellte Rechnung.
	 * @return die maximale ID aus der Datebank + 1
	 */
	public static int generiereUserID() {
		int id = 0;
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select max(id) as max from users");
			ResultSet r = stmt.executeQuery();
			
			while(r.next()) {
				id = r.getInt("max");
			}
			stmt.close();
			r.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return id + 1;
	}
	
	public static int generiereBildID() {
		int id = 0;
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select max(id) as max from images");
			ResultSet r = stmt.executeQuery();
			
			while(r.next()) {
				id = r.getInt("max");
			}
			stmt.close();
			r.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return id + 1;
	}
	
	public static int generiereAngeboteID(String laden) {
		int id = 0;
		Connection con = DBConnection.getConnection();
		

		try {
			PreparedStatement stmt = con.prepareStatement("select max(artikelnr) as max from " + laden +"_angebote");
			
			ResultSet r = stmt.executeQuery();
			
			while(r.next()) {
				id = r.getInt("max");
			}
			stmt.close();
			r.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return id + 1;
		
	}
	
	public static int generiereEinkaufslistenID() {
		int id = 0;
		Connection con = DBConnection.getConnection();
		

		try {
			PreparedStatement stmt = con.prepareStatement("select max(einkaufsliste_id) as max from einkauf_historie");
			
			ResultSet r = stmt.executeQuery();
			
			while(r.next()) {
				id = r.getInt("max");
			}
			stmt.close();
			r.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return id + 1;
	}
	
	public static void main(String[] args) {
		System.out.print(generiereUserID());
	}
}
