package general.supermarkets;

import java.util.HashMap;

import general.Food;
import general.Supermarkt;

public class Rewe extends Supermarkt{

	protected Rewe(HashMap<Integer, Food> sortiment, HashMap<Integer, Food> angebote) {
		super(sortiment, angebote);
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Rewe";
	}

}
