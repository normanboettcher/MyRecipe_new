package general;

public class Adresse {
	
	private String street, plz, city, number;
	
	public Adresse(String street, String number, String plz, String city) {
		this.street = street;
		this.number = number;
		this.plz = plz;
		this.city = city;
	}
	
	public String getStreet() {return street;}
	public String getNumber() {return number;}
	public String getPLZ(){return plz;}
	public String getCity() {return city;}
}
