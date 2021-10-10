package hochladen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.transfer.SpeicherInDatenbank;
import databaseConnection.DBConnection;
import general.supermarkets.Rezepte;

public class HinzufuegenInDB {
	
	public static void main(String[] args) {
		
		String titel = "";
		

		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt_table = con.prepareStatement("create table rezepte ("
					+ " rezepte_id integer,"
					+ "  titel varchar(250))");
			//stmt_table.executeUpdate();
			//stmt_table.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
;		
		for(int i = 0; i < 40; i++) {
			try {
				PreparedStatement stmt = con.prepareStatement("insert into rezepte"
						+ " values"
						+ " (?, ?)");
				stmt.setInt(1, i);
				stmt.setString(2, "");
				//stmt.executeUpdate();
				//stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		for(int i = 0; i < 40; i++) {
			
			int random_anz_zutat = (int) (Math.random() * (7- 3) + 3);
			int random_artikelNr = (int) (Math.random() * (94 - 2) + 2);
			
			try {
				PreparedStatement stmt = con.prepareStatement("select * from rezepte"
						+ " where rezepte_id = ?");
				stmt.setInt(1, i);
				ResultSet r = stmt.executeQuery();
				
				while(r.next()) {
					titel = r.getString("titel");
				}
				stmt.close();
				r.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			Rezepte r = new Rezepte(titel, "","","", i);
			
			for(int j = 0; j < random_anz_zutat; j++) {
				r.hinzufuegen(random_artikelNr, (int) (Math.random() * (5 - 2) + 2));
			}
			
			//SpeicherInDatenbank.speicherZutatenAusRezept(r);
		}
	}

}
