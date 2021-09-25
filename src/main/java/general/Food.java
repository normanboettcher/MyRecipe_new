package general;

public class Food {
	
	private boolean vegetarian, vegan, local, bio;
	
	private String bez, manufacturer, image;
	private double price;

	public Food(String bez, double price, String manufacturer, String image,
			int veggy, int vegan, int local, int bio) {
		this.bez = bez;
		this.price = price;
		this.manufacturer = manufacturer;
		this.image = image;
		
		if(veggy == 0) {
			this.vegetarian = false;
		} else {
			this.vegetarian = true;
		}
		
		if(vegan == 0) {
			this.vegan = false;
		} else {
			this.vegan = true;
		}
		
		if(local == 0) {
			this.local = false;
		} else {
			this.local = true;
		}
		
		if(bio == 0) {
			this.bio = false;
		} else {
			this.bio = true;
		}
	}
	
	
	//-----------------Simple getters for Food-----------------
	public double getPrice() {return price;}
	public String getBezeichnung() {return bez;}
	public boolean getVeggy() {return vegetarian;}
	public boolean getVegan() {return vegan;}
	public String getManufacturer() {return manufacturer;}
	public String getImage() {return image;}
	public boolean getBio() {return bio;}
	public boolean getLocal() {return local;}
	
}
