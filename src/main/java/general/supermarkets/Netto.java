package general.supermarkets;

import java.util.HashMap;

import general.Food;
import general.Supermarkt;

public class Netto extends Supermarkt {
	
	protected Netto(HashMap<Integer, Food> sortiment, HashMap<Integer, Food> angebote) {
		super(sortiment, angebote);
		setBezeichnung();
	}

	@Override
	protected void setBezeichnung() {
		this.bez = "Netto";
	}

}
