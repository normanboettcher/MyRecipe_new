package general.foods;

import general.Food;

public class Fruit extends Food {
	
	public Fruit(String bez, double price, String manufacturer) {
		super(bez, price, manufacturer);
		setVeggy();
		setVegan();
	}
	
	@Override
	protected void setVeggy() {
		this.vegetarian = true;
	}

	@Override
	protected void setVegan() {
		this.vegan = true;
	}
	
}
