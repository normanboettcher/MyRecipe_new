package general.supermarkets;

public class Rezepte {

	public Rezepte(String title, String k�che, String gerichteart, String eigenschaften) {
		this.title = title;
		this.k�che = k�che;
		this.gerichteart = gerichteart;
		this.eigenschaften = eigenschaften;
	}

	private String title;
	private String k�che;
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

	public String getK�che() {
		return k�che;
	}

	public String getGerichteart() {
		return gerichteart;
	}

	public String getEigenschaften() {
		return eigenschaften;
	}
}
