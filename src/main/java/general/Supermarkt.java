package general;

import java.util.HashMap;

import general.Food;

public abstract class Supermarkt {
	
	protected String bez;
	
	private HashMap<Integer, Food> sortiment, angebote;
	
	protected Supermarkt() {	
		this.sortiment = new HashMap<Integer, Food>();
		this.angebote = new HashMap<Integer, Food>();
	}
	
	protected abstract void setBezeichnung();
	
	protected abstract void initSortiment();
	
	public HashMap<Integer, Food> getAngebote() {
		return angebote;
	}
	
	public String getBezeichnung() {
		return bez;
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
	
	public void removeSortimentByKey(int key) {
		sortiment.remove(key);
	}
	
	

}
