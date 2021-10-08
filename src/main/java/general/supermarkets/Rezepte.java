package general.supermarkets;

public class Rezepte {

	public Rezepte(String titel, String kueche, String gerichteart, String eigenschaften, int rezepte_id) {
		this.titel = titel;
		this.kueche = kueche;
		this.gerichteart = gerichteart;
		this.eigenschaften = eigenschaften;
		this.rezepte_id = rezepte_id;
	}

	private String titel;
	private String kueche;
	private String gerichteart;
	private String eigenschaften;
	private int rezepte_id;
	private double similarity; 
	

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
