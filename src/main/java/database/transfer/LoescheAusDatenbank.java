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

}
