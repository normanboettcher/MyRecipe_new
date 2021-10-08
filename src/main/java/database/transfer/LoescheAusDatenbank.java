package database.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import databaseConnection.DBConnection;

public class LoescheAusDatenbank {
	
	public static void loescheTabellenInhalt(String table) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("delete from " + table + " where artikelnr >= ?");
			stmt.setInt(1, 1);

			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
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
