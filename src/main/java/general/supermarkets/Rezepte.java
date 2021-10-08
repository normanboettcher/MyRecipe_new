package general.supermarkets;

public class Rezepte {

	public Rezepte(String title, String kueche, String gerichteart, String eigenschaften, int id) {
		this.title = title;
		this.kueche = kueche;
		this.gerichteart = gerichteart;
		this.eigenschaften = eigenschaften;
		this.id = id;
	}

	private String title;
	private String kueche;
	private String gerichteart;
	private String eigenschaften;
	private int id;
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

	public String getKueche() {
		return kueche;
	}

	public String getGerichteart() {
		return gerichteart;
	}

	public String getEigenschaften() {
		return eigenschaften;
	}
	public int getId() {
		return id;
	}
}
