package general;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import database.transfer.SpeicherInDatenbank;


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
	
	private void addEinkaufslisteZuHistorie(Einkaufsliste l) {
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
	
	public void addEinkaufslisteZuHistorieInDB(Einkaufsliste l) {
		SpeicherInDatenbank.speicherEinkaufslisteInDatenbank(l);
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
	
	public HashMap<Integer,Einkaufsliste> getEinkaufslisteByDate(Date date) {
		HashMap<Integer,Einkaufsliste> result = new HashMap<Integer, Einkaufsliste>();
		Date date_vergleich  = null;
		
		for (Entry<Integer, Einkaufsliste> entry : getEinkaufslisteHistorie().entrySet()) {
			
			date_vergleich = entry.getValue().getEinkaufslisteDate();
			
			if((date_vergleich.compareTo(date) == 0) && 
					(entry.getValue().getUser().getID() == getID())) {
				
				result.put(entry.getValue().getEinkaufslisteID(), entry.getValue());
			}
		}
		return result;
	}
	
	public Einkaufsliste getEinkaufsliste() {
		return einkaufsliste;
	}
	
	public HashMap<Integer, Einkaufsliste> getEinkaufslisteHistorie() {
		return einkauf_historie;
	}
	
}
