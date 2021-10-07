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
	
	public Einkaufsliste(int id, Date date) {
		this.id = id;
		this.date = date;
		this.produkte_mit_menge = new HashMap<Integer, Integer>();
		this.produkte = new HashMap<Integer, Food>();
	}
	
	public void setUser(User u) {
		this.usr = u;
	}
	
	public void addProduktZuListe(Food f, int anzahl) {
		this.produkte_mit_menge.put(f.getArtikelNr(), anzahl);
		this.produkte.put(f.getArtikelNr(), f);
	}
	
	public void loescheProduktVonListe(Food f, int anzahl) {
		this.produkte_mit_menge.remove(f.getArtikelNr(), anzahl);
		this.produkte.remove(f.getArtikelNr(), f);
	}
	
	public void berechneGesamtpreis(HashMap<Integer, Food> produkte) {
		double preis = 0;
		
		for(int i : produkte.keySet()) {
			preis += produkte.get(i).getPreis() * getProdukteMitMenge().get(i);
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
