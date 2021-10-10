package general.supermarkets;

import java.util.HashMap;

import general.Food;

public class Rezepte {
	

	private String titel;
	private String kueche;
	private String gerichteart;
	private String eigenschaften;
	private int rezepte_id;
	private double similarity; 
	private HashMap<Integer, Integer> zutaten;
	

	public Rezepte(String titel, String kueche, String gerichteart, String eigenschaften, int rezepte_id) {
		this.titel = titel;
		this.kueche = kueche;
		this.gerichteart = gerichteart;
		this.eigenschaften = eigenschaften;
		this.rezepte_id = rezepte_id;
		this.zutaten = new HashMap<Integer, Integer>();

	}

	
	public HashMap<Integer, Integer> getZutaten() {
		return zutaten;
	}
	
	public void hinzufuegen(int id, int menge) {
		this.zutaten.put(id, menge);
	}
	
	public int getZutatById(int id) {
		return this.zutaten.get(id);
	}
	
	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public String getTitel() {
		return titel;
	}

	public String getKueche() {
		return kueche;
	}

	public String getGerichteart() {
		return gerichteart;
	}

	public String getEigenschaften() {
		return eigenschaften;
	}
	public int getRezepte_id() {
		return rezepte_id;
	}
}
