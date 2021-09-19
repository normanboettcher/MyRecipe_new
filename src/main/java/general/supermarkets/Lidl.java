package general.supermarkets;

import java.util.HashMap;

import general.Food;
import general.Supermarkt;

public class Lidl extends Supermarkt {
	
	protected Lidl(HashMap<Integer, Food> sortiment, HashMap<Integer, Food> angebote) {
		super(sortiment, angebote);
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Lidl";
	}

}
