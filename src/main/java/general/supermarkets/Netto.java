package general.supermarkets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import databaseConnection.DBConnection;
import general.Food;
import general.Supermarkt;

public class Netto extends Supermarkt {
	
	private final int URSPRUNGSID = 3;
	
	public Netto() {
		super();
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Netto";
	}
	
	public int getUrsprungsID() {
		return URSPRUNGSID;
	}
}
