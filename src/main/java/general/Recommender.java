package general;

public class Recommender {
	public Recommender(String titel, int rezepte_id, int artikelnr, int anzahl, double artikelpreis) {
		this.titel = titel;
		this.rezepte_id = rezepte_id;
		this.artikelnr = artikelnr;
		this.anzahl = anzahl;
		this.artikelpreis = artikelpreis;
	}

	private String titel;
	private int rezepte_id;
	private int artikelnr;
	private int anzahl;
	private double artikelpreis;
	private double similarity;

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	private String getTitel() {
		return titel;
	}

	private int getRezepte_id() {
		return rezepte_id;
	}
	private int getArtikelnr() {
		return artikelnr;
	}
	private int getAnzahl() {
		return anzahl;
	}
	private double getArtikelpreis() {
		return artikelpreis;
	}

}
