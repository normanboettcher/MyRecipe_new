package general;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;

import managers.DoubleManager;

/**
 * Klasse, um Einkaufsliste zu realisieren.
 * @author norman
 *
 */
public class Einkaufsliste implements Serializable {
	//Attribute
	/**
	 * 
	 */
	private static final long serialVersionUID = 7654645523434557746L;
	private int id;
	private Date date;
	private double preis;
	private HashMap<Integer, Food> produkte;
	private HashMap<Integer, Integer> produkte_mit_menge;
	private User usr;
	private String laden;
	private double ersparnis;
	private double ersparnisInProzent;
	
	/**
	 * Konstruktor der Klasse Einkaufsliste
	 * 
	 * @param id   Eine Einkaufsliste hat eine ID.
	 * @param date JEde Einkaufsliste hat ein Datum.
	 */
	public Einkaufsliste(int id, Date date) {
		this.id = id;
		this.date = date;
		this.produkte_mit_menge = new HashMap<Integer, Integer>();
		this.produkte = new HashMap<Integer, Food>();
	}
	
	/**
	 * Methode zum setzen der berechneten Ersparnis in Prozenbt.
	 * 
	 * @param d ersparnis als double.
	 */
	public void setErsparnisInProzent(double d) {
		this.ersparnisInProzent = d;
	}

	/**
	 * Rueckgabe der Ersparnis in Prozent.
	 * 
	 * @return Ersparnis in Prozent als double.
	 */
	public double getErsparnisInProzent() {
		return DoubleManager.round(ersparnisInProzent,2);
	}

	/**
	 * Methode zum setzen der ERsparnis als double.
	 * 
	 * @param d ersparnis.
	 */
	public void setErsparnis(double d) {
		this.ersparnis = d;
	}

	/**
	 * Rueckgabe der Ersparnis als double.
	 * 
	 * @return ersparnis.
	 */
	public double getErsparnis() {
		return DoubleManager.round(ersparnis, 2);
	}

	/**
	 * Methode zum hinzufuegen eines Ladens einer Liste.
	 * 
	 * @param laden der Laden als String.
	 */
	public void addLaden(String laden) {
		this.laden = laden;
	}

	/**
	 * Rueckgabe des Ladens.
	 * 
	 * @return laden
	 */
	public String getLaden() {
		return laden;
	}

	/**
	 * Methode zum setzen eines Users fuer eine Einkaufsliste.
	 * 
	 * @param u der User, der die Liste benutzt.
	 */
	public void setUser(User u) {
		this.usr = u;
	}

	/**
	 * Methode, um ein Produkt zur Einkaufsliste hinzuzufuegen.
	 * 
	 * @param f      Da Produkte, das hinzugefuegt wird.
	 * @param anzahl die Haeufigkeit, mit der ein Produkt in die Einkaufsliste
	 *               gelegt wird.
	 */
	public void addProduktZuListe(Food f, int anzahl) {
		getProdukteMitMenge().put(f.getArtikelNr(), anzahl);
		getProduktliste().put(f.getArtikelNr(), f);
	}

	/**
	 * Methode um Produkt von einer Liste loeschen zu koennen.
	 * 
	 * @param f      das zu loeschende Produkt.
	 * @param anzahl die zu loeschende Anzahl.
	 */
	public void loescheProduktVonListe(Food f, int anzahl) {
		this.produkte_mit_menge.remove(f.getArtikelNr(), anzahl);
		this.produkte.remove(f.getArtikelNr(), f);
	}

	/**
	 * Methode zum berechnen des Gesamtpreises einer Einkaufsliste.
	 */
	public void berechneGesamtpreis() {
		double preis = 0;
		
		for(int i : getProduktliste().keySet()) {
			preis += getProduktliste().get(i).getPreis() * getProdukteMitMenge().get(i);
		}
		
		this.preis = preis;
	}

	/**
	 * Rueckgabe der ID einer Einkaufsliste.
	 * 
	 * @return id
	 */
	public int getEinkaufslisteID() {
		return id;
	}

	/**
	 * Rueckgabe des Datums einer Einkaufsliste.
	 * 
	 * @return date
	 */
	public Date getEinkaufslisteDate() {
		return date;
	}

	/**
	 * Rueckgabe des Gesamtpreises einer Einkaufsliste.
	 * 
	 * @return gesamtpreis als double.
	 */
	public double getGesamtPreis() {
		return DoubleManager.round(preis, 2);
	}

	/**
	 * Rueckgabe der Produktliste der Einkaufsliste.
	 * 
	 * @return produkte
	 */
	public HashMap<Integer, Food> getProduktliste() {
		return produkte;
	}

	/**
	 * Rueckgabe der Produkte mit ihrer Menge.
	 * 
	 * @return produkte_mit_menge
	 */
	public HashMap<Integer, Integer> getProdukteMitMenge() {
		return produkte_mit_menge;
	}

	/**
	 * Rueckgabe des Users einer Einkaufsliste.
	 * 
	 * @return usr
	 */
	public User getUser() {
		return usr;
	}

}
