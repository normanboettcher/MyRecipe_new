package general.supermarkets;

import java.io.Serializable;
import java.util.HashMap;

import managers.DoubleManager;

/**
 * Klasse zur Realisierung eines Rezeptes.
 * @author norman
 *
 */
public class Rezepte implements Serializable {
	//Attribute
	/**
	 * 
	 */
	private static final long serialVersionUID = -5527515392357977679L;
	private String titel;
	private String kueche;
	private String gerichteart;
	private String eigenschaften;
	private int rezepte_id;
	private double similarity; 
	private HashMap<Integer, Integer> zutaten;
	
	/**
	 * Konstruktor der Klasse Rezept.
	 * 
	 * @param titel         der Titel des Rezeptes.
	 * @param kueche        Kuechenart des Rezeptes.
	 * @param gerichteart   Gerichteart des Rezeptes.
	 * @param eigenschaften Eigenschaften des Rezeptes.
	 * @param rezepte_id    ID eines Rezeptes.
	 */
	public Rezepte(String titel, String kueche, String gerichteart, String eigenschaften, int rezepte_id) {
		this.titel = titel;
		this.kueche = kueche;
		this.gerichteart = gerichteart;
		this.eigenschaften = eigenschaften;
		this.rezepte_id = rezepte_id;
		this.zutaten = new HashMap<Integer, Integer>();

	}

	/**
	 * Rueckgabe der Zutaten eines Rezeptes.
	 * 
	 * @return zutaten
	 */
	public HashMap<Integer, Integer> getZutaten() {
		return zutaten;
	}

	/**
	 * Hinzufuegen der Zutaten zu einem Rezept.
	 * 
	 * @param id    artikelnr.
	 * @param menge die menge der Zutat.
	 */
	public void hinzufuegen(int id, int menge) {
		this.zutaten.put(id, menge);
	}

	/**
	 * Rueckgabe der Similarity.
	 * 
	 * @return similarity
	 */
	public double getSimilarity() {
		return DoubleManager.round(similarity, 2);
	}

	/**
	 * Setzen einer Similarity.
	 * 
	 * @param similarity die similarity.
	 */
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	/**
	 * Ruckgabe des Titels.
	 * 
	 * @return titel
	 */
	public String getTitel() {
		return titel;
	}

	/**
	 * Rueckgabe der Kuechenart.
	 * 
	 * @return kueche
	 */
	public String getKueche() {
		return kueche;
	}

	/**
	 * Rueckgabe der Gerichteart.
	 * 
	 * @return gerichteart
	 */
	public String getGerichteart() {
		return gerichteart;
	}

	/**
	 * Rueckgabe der Eigenschaften.
	 * 
	 * @return eigenschaften
	 */
	public String getEigenschaften() {
		return eigenschaften;
	}

	/**
	 * Rueckgabe der Rezept ID.
	 * 
	 * @return rezepte_id
	 */
	public int getRezepte_id() {
		return rezepte_id;
	}
}
