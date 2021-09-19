package general;

import java.util.HashMap;

import general.Food;

public abstract class Supermarkt {
	
	protected String bez;
	
	private HashMap<Integer, Food> sortiment, angebote;
	
	protected Supermarkt(HashMap<Integer, Food> sortiment, HashMap<Integer, Food> angebote) {
		this.sortiment = sortiment;
		this.angebote = angebote;
	}
	
	protected abstract void setBezeichnung();
	
	public HashMap<Integer, Food> getAngebote() {
		return angebote;
	}
	
	public Food getAngebotByKey(int key) {
		return angebote.get(key);
	}
	
	public void addAngebot(int key, Food f) {
		angebote.put(key, f);
	}
	
	public void removeAngebotByKey(int key) {
		angebote.remove(key);
	}
	
	public HashMap<Integer, Food> getSortiment() {
		return sortiment;
	}
	
	public Food getSortimentByKey(int key) {
		return sortiment.get(key);
	}
	
	public void addSortiment(int key, Food f) {
		sortiment.put(key, f);
	}
	
	public void removeSortimentByKey(int key) {
		sortiment.remove(key);
	}
	
	

}
