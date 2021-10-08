package general.supermarkets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import databaseConnection.DBConnection;
import general.Food;
import general.Supermarkt;

public class Lidl extends Supermarkt {
	
	private final int URSPRUNGSID = 2;
	
	public Lidl() {
		super();
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Lidl";
	}
	
	public int getUrsprungsID() {
		return URSPRUNGSID;
	}
	

}
