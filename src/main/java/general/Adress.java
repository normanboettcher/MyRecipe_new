package general;

public class Adress {
	
	private String street, plz, city, number;
	
	public Adress(String street, String number, String plz, String city) {
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
