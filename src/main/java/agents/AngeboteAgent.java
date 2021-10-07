package agents;

import java.util.HashMap;

import general.Einkaufsliste;
import general.Food;
import managers.DoubleManager;

public class AngeboteAgent {
	
	private Food angebotZwischenspeicher;
	private Food sortimentZwischenspeicher;
	private double sortimentPreisZwischenspeicher;
	private double preisEndergebnis;
	private double gespart;
	private double ersparungInProzent;
	
	public AngeboteAgent() {
		
	}
	
	public Food getAngebotZwischenspeicher() {
		return angebotZwischenspeicher;
	}
	
	public Food getSortimentZwischenspeicher() {
		return sortimentZwischenspeicher;
	}
	
	public double getSortimentPreisZwischenspeicher() {
		return DoubleManager.round(sortimentPreisZwischenspeicher, 2);
	}
	
	public double getPreisEndergebnis() {
		return DoubleManager.round(preisEndergebnis, 2);
	}
	
	public double getGespart() {
		return gespart;
	}
	
	public double getErsparungInProzent() {
		return DoubleManager.round(ersparungInProzent, 2);
	}
	
	public boolean produktImAngebot(HashMap<Integer, Food> sortiment, 
			HashMap<Integer, Food> angebot, int artikelnr) {
		
		boolean imAngebot = false;
		
		if ((sortiment.get(artikelnr) != null) && (angebot.get(artikelnr) != null)) {
			imAngebot = true;
			this.angebotZwischenspeicher = angebot.get(artikelnr);
			this.sortimentZwischenspeicher = sortiment.get(artikelnr);
		}
		return imAngebot;
	}
	
	public void tauscheEinkaufslisteProduktMitAngebotProdukt(Einkaufsliste l) {
		
		int mengeZwischenspeicher = l.getProdukteMitMenge().get(getSortimentZwischenspeicher().getArtikelNr());
		
		for(Food f : l.getProduktliste().values()) {
			this.sortimentPreisZwischenspeicher += f.getPreis() * l.getProdukteMitMenge().get(f.getArtikelNr());
		}
		
		l.loescheProduktVonListe(getSortimentZwischenspeicher(), mengeZwischenspeicher);
		
		l.addProduktZuListe(getAngebotZwischenspeicher(), 
			mengeZwischenspeicher);
	}
	
	public void berechneErsparung(Einkaufsliste l) {
		
		for(Food f : l.getProduktliste().values()) {
			this.preisEndergebnis += f.getPreis() * l.getProdukteMitMenge().get(f.getArtikelNr());
		}
		
		this.gespart = DoubleManager.round(getSortimentPreisZwischenspeicher() - getPreisEndergebnis(), 2);
	}
	
	public void berechneErsparungInProzent() {
		this.ersparungInProzent = (getGespart() * 100) / getPreisEndergebnis();
	}
	
}
