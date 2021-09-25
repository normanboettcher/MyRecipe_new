package general;

import managers.IdGenerator;
import general.Adresse;

public class User {
	
	private String vorname, name, email, pw;
	private Adresse adrs;
	private int id;
	
	public User(String vorname,String name, String email, Adresse adrs, String pw) {
		this.vorname = vorname;
		this.name = name;
		this.email = email;
		this.adrs = adrs;
		this.pw = pw;
		this.id = setID();
	}
	
	private int setID() {
		return IdGenerator.generiereUserID();
	}
	
	//---------simple getters for user------------------
	public String getVorname() {return vorname;}
	public String getNachname() {return name;}
	public String getFullName() {return vorname + " " + name;}
	public String getEmail() {return email;}
	public Adresse getAdress() {return adrs;}
	public String getPasswort() {return pw;}
	public int getID() {return id;}
}
