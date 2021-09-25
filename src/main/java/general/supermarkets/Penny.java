package general.supermarkets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import databaseConnection.DBConnection;
import general.Food;
import general.Supermarkt;

public class Penny extends Supermarkt {
	
	public Penny() {
		super();
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Penny";
	}
	
	@Override
	public void initSortiment() {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select ? from ? "
					+ "LEFT JOIN ? ON ?.image_id = ?.id");
			stmt.setString(1, "id");
			stmt.setString(2, "images");
			stmt.setString(3, "PennySortiment");
			stmt.setString(4, "PennySortiment");
			stmt.setString(5, "images");
			
			ResultSet r = stmt.executeQuery();
			
			while(r.next()) {
				Food f = new Food(r.getString("artikelbez"), r.getDouble("artikelpreis"),
						r.getString("hersteller"), r.getString("pfad"), 
						r.getInt("vegan"), r.getInt("vegetarisch"), r.getInt("lokal"),
						r.getInt("bio"));
				getSortiment().put(r.getInt("artikelNr"), f);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}


}
