package general.supermarkets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import databaseConnection.DBConnection;
import general.Food;
import general.Supermarkt;

public class Rewe extends Supermarkt{

	public Rewe() {
		super();
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Rewe";
	}
	
	@Override
	public void initSortiment() {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select ? from ? "
					+ "LEFT JOIN ? ON ?.image_id = ?.id");
			stmt.setString(1, "id");
			stmt.setString(2, "images");
			stmt.setString(3, "ReweSortiment");
			stmt.setString(4, "ReweSortiment");
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
