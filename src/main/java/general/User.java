package general;

import java.sql.Date;
import java.util.HashMap;

import managers.IdGenerator;


public class User {
	
	private String vorname, name, email, pw;
	private Adresse adrs;
	private int id;
	private Einkaufsliste einkaufsliste;
	private HashMap<Integer, Einkaufsliste> einkauf_historie;
	
	
	public User(int id, String vorname,String name, String email, Adresse adrs, String pw) {
		this.vorname = vorname;
		this.name = name;
		this.email = email;
		this.adrs = adrs;
		this.pw = pw;
		this.id = id;
	}
	
	public void setEinkaufsliste(Einkaufsliste list) {
		this.einkaufsliste = list;
		getEinkaufsliste().setUser(this);
		addEinkaufslisteZuHistorie(list);
	}
	
	public void addEinkaufslisteZuHistorie(Einkaufsliste l) {
		if(this.einkauf_historie == null) {
			this.einkauf_historie = new HashMap<Integer, Einkaufsliste>();
			l.setUser(this);
			this.einkauf_historie.put(l.getEinkaufslisteID(), l);
		}
		else {
			l.setUser(this);
			this.einkauf_historie.put(l.getEinkaufslisteID(), l);
		}
	}
	
	//---------simple getters for user------------------
	public String getVorname() {
		return vorname;
	}
	
	public String getNachname() {
		return name;
	}
	
	public String getFullName() {
		return vorname + " " + name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Adresse getAdresse() {
		return adrs;
	}
	
	public String getPasswort() {
		return pw;
	}
	
	public int getID() {
		return id;
	}
	
	public Einkaufsliste getEinkaufslisteByDate(Date date) {
		return einkauf_historie.get(date);
	}
	
	public Einkaufsliste getEinkaufsliste() {
		return einkaufsliste;
	}
	
	public HashMap<Integer, Einkaufsliste> getEinkaufslisteHistorie() {
		return einkauf_historie;
	}
	
}
