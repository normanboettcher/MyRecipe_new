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
			PreparedStatement stmt = con.prepareStatement("select * from images "
					+ "LEFT JOIN penny_sortiment ON images.id = penny_sortiment.image_id");
			
			ResultSet r = stmt.executeQuery();
			
			while(r.next()) {
				Food f = new Food(r.getString("artikelbez"), r.getDouble("artikelpreis"),
						r.getString("hersteller"), r.getString("pfad").substring(16), 
						r.getInt("vegan"), r.getInt("vegetarisch"), r.getInt("lokal"),
						r.getInt("bio"));
				getSortiment().put(r.getInt("artikelNr"), f);
			}
			r.close();
			stmt.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}


}
