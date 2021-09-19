package general;

public abstract class Food {
	
	protected boolean vegetarian, vegan, local;
	
	private String bez, manufacturer;
	private double price;

	protected Food(String bez, double price, String manufacturer) {
		this.bez = bez;
		this.price = price;
		this.manufacturer = manufacturer;
	}
	
	//------------------Abstract methods for Foods------------
	/**
	 * Abstract class setVeggy().
	 * Every food categorie implements another veggy value {true, false}.
	 */
	protected abstract void setVeggy();
	/**
	 * Abstract class setVeggy().
	 * Every food categorie implements another vegan value {true, false}.
	 */
	protected abstract void setVegan();
	
	//-----------------Simple getters for Food-----------------
	public double getPrice() {return price;}
	public String getBezeichnung() {return bez;}
	public boolean getVeggy() {return vegetarian;}
	public boolean getVegan() {return vegan;}
	public String getManufacturer() {return manufacturer;}
	
}
