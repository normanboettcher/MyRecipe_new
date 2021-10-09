package general.supermarkets;

public class RezeptAnfrage {
	

	private String titel;
	private String[] kueche;
	private String[] gerichteart;
	private String[] eigenschaften;
	private double similarity; 
	

	public RezeptAnfrage(String titel, String[] kueche, String[] gerichteart, String[] eigenschaften) {
		this.titel = titel;
		this.kueche = kueche;
		this.gerichteart = gerichteart;
		this.eigenschaften = eigenschaften;
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

	public String[] getKueche() {
		return kueche;
	}

	public String[] getGerichteart() {
		return gerichteart;
	}

	public String[] getEigenschaften() {
		return eigenschaften;
	}

}
