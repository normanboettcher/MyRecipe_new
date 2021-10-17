package general;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import database.transfer.SpeicherInDatenbank;
/**
 * Klasse um einen User zu realiseren.
 * @author norman
 *
 */
public class User {
	//Attribute
	private String vorname, name, email, pw;
	private Adresse adrs;
	private int id;
	private Einkaufsliste einkaufsliste;
	private HashMap<Integer, Einkaufsliste> einkauf_historie;
	
	/**
	 * Konsturktor der Klasse user
	 * @param id
	 * @param vorname
	 * @param name
	 * @param email
	 * @param adrs
	 * @param pw
	 */
	public User(int id, String vorname,String name, String email, Adresse adrs, String pw) {
		this.vorname = vorname;
		this.name = name;
		this.email = email;
		this.adrs = adrs;
		this.pw = pw;
		this.id = id;
	}

	/**
	 * Setzen der Einkaufsliste fuer einen User.
	 * 
	 * @param list die Einkaufsliste.
	 */
	public void setEinkaufsliste(Einkaufsliste list) {
		this.einkaufsliste = list;
		getEinkaufsliste().setUser(this);
		addEinkaufslisteZuHistorie(list);
	}

	/**
	 * Methode, um Einkaufsliste zur Historie hinzufuegen zu koennen.
	 * 
	 * @param l die Einkaufsliste.
	 */
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

	/**
	 * Hinzufuegen der Einkaufsliste in die Historie der DB.
	 * 
	 * @param l die Einkaufsliste.
	 */
	public void addEinkaufslisteZuHistorieInDB(Einkaufsliste l) {
		SpeicherInDatenbank.speicherEinkaufslisteInDatenbank(l);
	}
	
	//---------simple getters for user------------------
	/**
	 * Rueckgabe des Vornamens.
	 * 
	 * @return vorname
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Rueckgabe des Nachnamens.
	 * 
	 * @return name
	 */
	public String getNachname() {
		return name;
	}

	/**
	 * Rueckgabe des vollen Namens.
	 * 
	 * @return vorname + nachname
	 */
	public String getFullName() {
		return vorname + " " + name;
	}

	/**
	 * Rueckgabe der Email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Rueckgabe der Adresse. @link Adresse
	 * 
	 * @return
	 */
	public Adresse getAdresse() {
		return adrs;
	}

	/**
	 * Rueckgabe des Passworts.
	 * 
	 * @return pw
	 */
	public String getPasswort() {
		return pw;
	}

	/**
	 * Rueckgabe der ID.
	 * 
	 * @return id
	 */
	public int getID() {
		return id;
	}

	/**
	 * Methode um eine Einkaufsliste durch das Datum auszugeben.
	 * 
	 * @param date das Datum, nach dem gesucht wird.
	 * @return result Liste aller einkaufslisten an diesem Datum.
	 */
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

	/**
	 * Rueckgabe der Einkaufsliste.
	 * 
	 * @return einkaufsliste
	 */
	public Einkaufsliste getEinkaufsliste() {
		return einkaufsliste;
	}

	/**
	 * Rueckgabe der Historie von Einkaufslisten.
	 * 
	 * @return einkauf_historie
	 */
	public HashMap<Integer, Einkaufsliste> getEinkaufslisteHistorie() {
		return einkauf_historie;
	}
	
}
