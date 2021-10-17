package general;

import java.io.Serializable;

import managers.DoubleManager;
/**
 * Methode zum Realsiieren eines Produktes.
 * @author norman
 *
 */
public class Food implements Serializable {
	//Attribute
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
	
	/**
	 * Konsturktor der Klasse Food
	 * 
	 * @param bez        Bezeichnung eines Produktes.
	 * @param preis      Preis eines PRoduktes.
	 * @param hersteller hersteller eines Produktes.
	 * @param image      Bild eines Produktes.
	 * @param veggy      1 oder 0.
	 * @param vegan      1 oder 0.
	 * @param lokal      1 oder 0.
	 * @param bio        1 oder 0.
	 * @param kategorie  Kategorie eines Produktes.
	 */
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

	/**
	 * Methode, um Ersparnis fuer ein Produkt zu berechnen.
	 */
	public void berechneErsparnisFuerFood() {
		this.gespart = getOriginPreis() - getPreis();
	}

	/**
	 * Rueckgabe der Ersparnis eines Produktes.
	 * 
	 * @return gespart
	 */
	public double getErsparnisFuerFood() {
		return DoubleManager.round(gespart, 2);
	}

	/**
	 * Rueckgabe der Ersparnis in Prozent fuer ein Produkt.
	 * 
	 * @return ersparnis in prozent
	 */
	public double getErsparnisInProzentFuerFood() {
		return DoubleManager.round((getErsparnisFuerFood() / getOriginPreis()) * 100, 2);
	}

	/**
	 * Methode, um den Preis fuer ein Produkt zu setzen.
	 * 
	 * @param p der Preis als double.
	 */
	public void setPreis(double p) {
		this.preis = p;
	}

	/**
	 * Setze den Ursprungsmarkt fuer ein Produkt.
	 * 
	 * @param id die ID des Marktes.
	 */
	public void setUrsprungsmarkt(int id) {
		this.ursprungsmarkt = id;
	}

	/**
	 * Rueckgabe des Ursprungsmarktes.
	 * 
	 * @return ursprungsmarkt.
	 */
	public int getUrpsungsmarktID() {
		return ursprungsmarkt;
	}

	/**
	 * Methode, um den Status zu setzen, ob ein Produkt im Angebot ist.
	 * 
	 * @param t true or false
	 */
	public void setImAngebot(boolean t) {
		this.imAngebot = t;
	}

	/**
	 * Rueckgabe des Status, ob Produkt im Angebot ist.
	 * 
	 * @return imAngebpot
	 */
	public boolean getImAngebot() {
		return imAngebot;
	}
	
	//-----------------Simple getters for Food-----------------
	/**
	 * Rueckgabe des Preises eines Produktes.
	 * 
	 * @return preis
	 */
	public double getPreis() {
		return preis;
	}

	/**
	 * Rueckgabe der Bezeichnung eines Produktes.
	 * 
	 * @return bez
	 */
	public String getBezeichnung() {
		return bez;
	}

	/**
	 * Ruckgabe, ob Veggy.
	 * 
	 * @return vegetarisch
	 */
	public int getVeggy() {
		return vegetarisch;
	}

	/**
	 * Rueckgabe, ob Vegan.
	 * 
	 * @return vegan
	 */
	public int getVegan() {
		return vegan;
	}

	/**
	 * Rueckgabe des Herstellers.
	 * 
	 * @return hersteller
	 */
	public String getHersteller() {
		return hersteller;
	}

	/**
	 * Rueckgabe des Imagepfades.
	 * 
	 * @return image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Rueckgabe ob Bio.
	 * 
	 * @return bio
	 */
	public int getBio() {
		return bio;
	}

	/**
	 * Rueckgabe, ob Lokal.
	 * 
	 * @return lokal
	 */
	public int getLokal() {
		return lokal;
	}

	/**
	 * Ruckgabe der Kategorie.
	 * 
	 * @return kategorie
	 */
	public String getKategorie() {
		return kategorie;
	}

	/**
	 * Rueckgabe des Rabatts eines Produktes.
	 * 
	 * @return rabatt
	 */
	public double getRabatt() {
		return rabatt;
	}

	/**
	 * Rueckgabe des Originalpreises eines Produkts.
	 * 
	 * @return originalpreis
	 */
	public double getOriginPreis() {
		return originalpreis;
	}

	/**
	 * Methode zum setzen der Artikelnummer eines Produkts.
	 * 
	 * @param nr die Akrtikelnr
	 */
	public void setArtikelNr(int nr) {
		this.artikelnr = nr;
	}

	/**
	 * Rueckgabe der Artikelnr.
	 * 
	 * @return artikelnr
	 */
	public int getArtikelNr() {
		return artikelnr;
	}

	/**
	 * Methode zum setzen des Rabatts eines Produktes.
	 * 
	 * @param r rabatt als double.
	 */
	public void setRabatt(double r) {
		this.rabatt = DoubleManager.round(r, 2);
	}

	/**
	 * Methode zum setzen des Originalpreises eines Produktes.
	 * 
	 * @param p der Originalpreis als double.
	 */
	public void setOriginalPreis(double p) {
		this.originalpreis = p;
	}
}
