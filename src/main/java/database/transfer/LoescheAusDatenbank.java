package database.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import databaseConnection.DBConnection;

/**
 * Klasse, um Methoden zu definieren, die Objekte aus der Datenbank loeschen.
 * 
 * @author norman
 *
 */
public class LoescheAusDatenbank {
	/**
	 * Methode, um Tabelleninhalt zu loeschen
	 * 
	 * @param table  die Tabelle, aus der geloescht werden soll.
	 * @param spalte ID Spalte.
	 */
	public static void loescheTabellenInhalt(String table, String spalte) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("delete from " + table + "_angebote where "+ spalte + " >= ?");
			stmt.setInt(1, 1);
			//System.out.println(stmt);
			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode, um Angebote aus DB zu loeschen.
	 * 
	 * @param laden der Laden, dessen Angebote geloescht werden.
	 * @param key   die ID des zu loeschenden Produktes.
	 */
	public static void loescheAngebotAusDB(String laden, int key) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("delete from " + laden + 
					"_angebote where artikelnr = ?");
			stmt.setInt(1, key);
			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methoden zum loeschen des Sortiments aus der DB.
	 * 
	 * @param laden der Laden dessen Sortiment geloescht wird.
	 * @param key   die artikelnr.
	 */
	public static void loescheSortimentAusDB(String laden, int key) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("delete from " + laden + 
					"_sortiment where artikelnr = ?");
			stmt.setInt(1, key);
			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
