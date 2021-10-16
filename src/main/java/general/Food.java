package general;

import java.io.Serializable;

import managers.DoubleManager;

public class Food implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4655826009192803502L;

	private int vegetarisch, vegan, lokal, bio, artikelnr;
	
	private String bez, hersteller, image, kategorie;
	private double preis, originalpreis, gespart;
	
	private double rabatt;
	
	private int ursprungsmarkt;
	
	private boolean imAngebot;

	public Food(String bez, double preis, String hersteller, String image,
			int veggy, int vegan, int lokal, int bio, String kategorie) {
		this.bez = bez;
		this.preis = preis;
		this.hersteller = hersteller;
		this.image = image;
		this.kategorie = kategorie;
		this.bio = bio;
		this.lokal = lokal;
		this.vegan = vegan;
		this.vegetarisch = veggy;
	}
	
	public void berechneErsparnisFuerFood() {
		this.gespart = getOriginPreis() - getPreis();
	}
	
	public double getErsparnisFuerFood() {
		return DoubleManager.round(gespart, 2);
	}
	
	public double getErsparnisInProzentFuerFood() {
		return DoubleManager.round((getErsparnisFuerFood() / getOriginPreis()) * 100, 2);
	}
	
	public void setPreis(double p) {
		this.preis = p;
	}
	
	public void setUrsprungsmarkt(int id) {
		this.ursprungsmarkt = id;
	}
	
	public int getUrpsungsmarktID() {
		return ursprungsmarkt;
	}
	
	public void setImAngebot(boolean t) {
		this.imAngebot = t;
	}
	
	public boolean getImAngebot() {
		return imAngebot;
	}
	
	//-----------------Simple getters for Food-----------------
	public double getPreis() {
		return preis;
	}
	
	public String getBezeichnung() {
		return bez;
	}
	
	public int getVeggy() {
		return vegetarisch;
	}
	
	public int getVegan() {
		return vegan;
	}
	
	public String getHersteller() {
		return hersteller;
	}
	
	public String getImage() {
		return image;
	}
	
	public int getBio() {
		return bio;
	}
	
	public int getLokal() {
		return lokal;
	}
	
	public String getKategorie() {
		return kategorie;
	}
	
	public double getRabatt() {
		return rabatt;
	}
	
	public double getOriginPreis() {
		return originalpreis;
	}
	
	public void setArtikelNr(int nr) {
		this.artikelnr = nr;
	}
	
	public int getArtikelNr() {
		return artikelnr;
	}

	public void setRabatt(double r) {
		this.rabatt = DoubleManager.round(r, 2);
	}
	
	public void setOriginalPreis(double p) {
		this.originalpreis = p;
	}
	
}
