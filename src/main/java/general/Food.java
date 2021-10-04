package general;

public class Food {
	
	private int vegetarisch, vegan, lokal, bio;
	
	private String bez, hersteller, image, kategorie;
	private double preis;

	public Food(String bez, double preis, String hersteller, String image,
			int veggy, int vegan, int lokal, int bio, String kategorie) {
		this.bez = bez;
		this.preis = preis;
		this.hersteller = hersteller;
		this.image = image;
		this.kategorie = kategorie;
	}
	
	
	//-----------------Simple getters for Food-----------------
	public double getPreis() {return preis;}
	public String getBezeichnung() {return bez;}
	public int getVeggy() {return vegetarisch;}
	public int getVegan() {return vegan;}
	public String getHersteller() {return hersteller;}
	public String getImage() {return image;}
	public int getBio() {return bio;}
	public int getLokal() {return lokal;}
	public String getKategorie() {return kategorie;}
	
}
