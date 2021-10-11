package general;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class Einkaufsliste {
	
	private int id;
	private Date date;
	private double preis;
	private HashMap<Integer, Food> produkte;
	private HashMap<Integer, Integer> produkte_mit_menge;
	private User usr;
	private String laden;
	private double ersparnis;
	private double ersparnisInProzent;
	
	public Einkaufsliste(int id, Date date) {
		this.id = id;
		this.date = date;
		this.produkte_mit_menge = new HashMap<Integer, Integer>();
		this.produkte = new HashMap<Integer, Food>();
	}
	
	public void setErsparnisInProzent(double d) {
		this.ersparnisInProzent = d;
	}
	
	public double getErsparnisInProzent() {
		return ersparnisInProzent;
	}
	
	public void setErsparnis(double d) {
		this.ersparnis = d;
	}
	
	public double getErsparnis() {
		return ersparnis;
	}
	
	public void addLaden(String laden) {
		this.laden = laden;
	}
	
	public String getLaden() {
		return laden;
	}
	
	public void setUser(User u) {
		this.usr = u;
	}
	
	public void addProduktZuListe(Food f, int anzahl) {
		getProdukteMitMenge().put(f.getArtikelNr(), anzahl);
		getProduktliste().put(f.getArtikelNr(), f);
	}
	
	public void loescheProduktVonListe(Food f, int anzahl) {
		this.produkte_mit_menge.remove(f.getArtikelNr(), anzahl);
		this.produkte.remove(f.getArtikelNr(), f);
	}
	
	public void berechneGesamtpreis() {
		double preis = 0;
		
		for(int i : getProduktliste().keySet()) {
			preis += getProduktliste().get(i).getPreis() * getProdukteMitMenge().get(i);
		}
		
		this.preis = preis;
	}

	public int getEinkaufslisteID() {
		return id;
	}
	
	public Date getEinkaufslisteDate() {
		return date;
	}
	
	public double getGesamtPreis() {
		return preis;
	}

	public HashMap<Integer, Food> getProduktliste() {
		return produkte;
	}
	
	public HashMap<Integer, Integer> getProdukteMitMenge() {
		return produkte_mit_menge;
	}
	
	public User getUser() {
		return usr;
	}

}
