package general.supermarkets;

import java.io.Serializable;

import managers.DoubleManager;
/**
 * Klasse, um RezeptAnfrage zu realisieren.
 * @author norman
 *
 */
public class RezeptAnfrage implements Serializable {
	//Attribute
	/**
	 * 
	 */
	private static final long serialVersionUID = -4211544371418073329L;
	private String titel;
	private String[] kueche;
	private String[] gerichteart;
	private String[] eigenschaften;
	private double similarity; 
	
	/**
	 * Konsturktor der Klasse RezeptAnfrage.
	 * 
	 * @param titel         Titel des Rezeptes.
	 * @param kueche        Kuechenart des Rezeptes.
	 * @param gerichteart   Gerichtearten des Rezeptes.
	 * @param eigenschaften Eigenschaften des Rezeptes.
	 */
	public RezeptAnfrage(String titel, String[] kueche, String[] gerichteart, String[] eigenschaften) {
		this.titel = titel;
		this.kueche = kueche;
		this.gerichteart = gerichteart;
		this.eigenschaften = eigenschaften;
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
	 * Setzen der similarity.
	 * 
	 * @param similarity die Similarity als double.
	 */
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	/**
	 * Rueckgabe des Titels.
	 * 
	 * @return titel
	 */
	public String getTitel() {
		return titel;
	}

	/**
	 * Rueckgabe der Kuechenarten.
	 * 
	 * @return kueche
	 */
	public String[] getKueche() {
		return kueche;
	}

	/**
	 * Rueckgabe der Gerichtearten.
	 * 
	 * @return gerichteart
	 */
	public String[] getGerichteart() {
		return gerichteart;
	}

	/**
	 * Rueckgabe der Eigenschaften.
	 * 
	 * @return eigenschaften
	 */
	public String[] getEigenschaften() {
		return eigenschaften;
	}

}
