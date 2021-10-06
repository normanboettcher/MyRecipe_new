package general.supermarkets;

public class Rezepte {

	public Rezepte(String title, String küche, String gerichteart, String eigenschaften) {
		this.title = title;
		this.küche = küche;
		this.gerichteart = gerichteart;
		this.eigenschaften = eigenschaften;
	}

	private String title;
	private String küche;
	private String gerichteart;
	private String eigenschaften;
	private double similarity; 
	

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public String getTitle() {
		return title;
	}

	public String getKüche() {
		return küche;
	}

	public String getGerichteart() {
		return gerichteart;
	}

	public String getEigenschaften() {
		return eigenschaften;
	}
}
