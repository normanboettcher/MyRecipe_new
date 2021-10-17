package general;
/**
 * Diese Klasse defniert die Eigenschaften einer Andresse.
 * @author norman
 *
 */
public class Adresse {
	//Attribute
	private String street, plz, city, number;

	/**
	 * Konstruktor der Klasse Adresse.
	 * 
	 * @param street die Strasse
	 * @param number Die Hausnummer
	 * @param plz    Die postleitzahl
	 * @param city   die Stadt.
	 */
	public Adresse(String street, String number, String plz, String city) {
		this.street = street;
		this.number = number;
		this.plz = plz;
		this.city = city;
	}

	/**
	 * Rueckgabe der Strasse.
	 * 
	 * @return street.
	 */
	public String getStreet() {return street;}

	/**
	 * Rueckgabe der Hausnummer.
	 * 
	 * @return number
	 */
	public String getNumber() {return number;}

	/**
	 * Rueckgabe der Postleitzahl.
	 * 
	 * @return plz
	 */
	public String getPLZ(){return plz;}

	/**
	 * Rueckgabe der Stadt.
	 * 
	 * @return city
	 */
	public String getCity() {return city;}
}
