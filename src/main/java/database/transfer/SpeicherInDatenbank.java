package database.transfer;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.DBConnection;
import general.Adresse;
import general.Einkaufsliste;
import general.Food;
import general.User;
import general.supermarkets.Rezepte;
import managers.DatumsManager;
import managers.IdGenerator;
import managers.PasswortManager;

public class SpeicherInDatenbank {
	
	/**
	 * Methode um einen komplett neuen User in der Datenbank abzulegen.
	 * 
	 * @param usr der neue User.
	 */
	public static void speicherUserInDatenbank(User usr) {

		Connection con = DBConnection.getConnection();
		
		try {
			String speicherUserQuery = "INSERT INTO users (id, email, passwort,"
					+ "vorname, nachname,"
					+ "strasse, hausnummer, plz, ort)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			PreparedStatement schreibeUserInDatenbank = con.prepareStatement(speicherUserQuery);	
			
			schreibeUserInDatenbank.setInt(1, usr.getID());
			schreibeUserInDatenbank.setString(2, usr.getEmail());
			schreibeUserInDatenbank.setString(3, usr.getPasswort());
			schreibeUserInDatenbank.setString(4, usr.getVorname());
			schreibeUserInDatenbank.setString(5, usr.getNachname());
			schreibeUserInDatenbank.setString(6, usr.getAdresse().getStreet());
			schreibeUserInDatenbank.setString(7, usr.getAdresse().getNumber());
			schreibeUserInDatenbank.setString(8, usr.getAdresse().getPLZ());
			schreibeUserInDatenbank.setString(9, usr.getAdresse().getCity());
			schreibeUserInDatenbank.executeUpdate();
			schreibeUserInDatenbank.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void speicherBildInDatenbank(String laden, File f) {
		Connection con = DBConnection.getConnection();
		
		try {
			String speicherBildQuery = "INSERT INTO images (id, pfad)"
					+ "VALUES"
					+ "(?, ?)";
			PreparedStatement schreibeBildInDatenbank = con.prepareStatement(speicherBildQuery);
			
			schreibeBildInDatenbank.setInt(1, IdGenerator.generiereBildID());
			schreibeBildInDatenbank.setString(2, "IMG/products/" 
			+ laden + "/" + f.getName());
			schreibeBildInDatenbank.executeUpdate();
			schreibeBildInDatenbank.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void speicherAngeboteInDatenbank(String laden, Food f) {
		Connection con = DBConnection.getConnection();
		
		try {
			String speicherAngebot = "insert into " + laden + "_angebote "
					+ "values"
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = con.prepareStatement(speicherAngebot);
			
			
			stmt.setInt(1, f.getArtikelNr());
			stmt.setString(2, f.getBezeichnung());
			stmt.setDouble(3, f.getPreis());
			stmt.setString(4, f.getHersteller());
			stmt.setString(5, f.getKategorie());
			stmt.setInt(6, f.getVegan());
			stmt.setInt(7, f.getVeggy());
			stmt.setInt(8, f.getLokal());
			stmt.setInt(9, f.getBio());
			stmt.setString(10, f.getImage());
			stmt.setInt(11, f.getUrpsungsmarktID());		
			stmt.executeUpdate();
			stmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void speicherEinkaufslisteInDatenbank(Einkaufsliste l) {
		Connection con = DBConnection.getConnection();
		
		
		try {
			String speicherAngebot = "insert into einkauf_historie " 
					+ "values "
					+ "(?,?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = con.prepareStatement(speicherAngebot);
			
		
				stmt.setInt(5,l.getEinkaufslisteID());
				stmt.setDate(1, l.getEinkaufslisteDate());
				stmt.setString(2, l.getUser().getFullName());
				stmt.setDouble(3, l.getGesamtPreis());
				stmt.setInt(4, l.getUser().getID());
				for(int i : l.getProduktliste().keySet()) {
					stmt.setInt(6, l.getProduktliste().get(i).getUrpsungsmarktID());
				}
			
			stmt.executeUpdate();
			stmt.close();
			
			speicherZutatenAusEinkaufslisteInDB(l);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void speicherZutatenAusEinkaufslisteInDB(Einkaufsliste l) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into produkte_aus_einkaufsliste "
					+ "values"
					+ "(?, ?, ?, ?, ?, ?, ?)");
			
			
			for(int i : l.getProduktliste().keySet()) {
				stmt.setDate(1, l.getEinkaufslisteDate());
				stmt.setInt(2, l.getUser().getID());
				stmt.setInt(3, l.getProduktliste().get(i).getArtikelNr());
				stmt.setInt(4, l.getProdukteMitMenge().get(i));
				stmt.setInt(5, l.getEinkaufslisteID());
				stmt.setInt(6, l.getProduktliste().get(i).getUrpsungsmarktID());
				stmt.setDouble(7, l.getProduktliste().get(i).getPreis());
				stmt.executeUpdate();
			}
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void speicherZutatenAusRezept(Rezepte r) {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into zutaten_aus_rezepte "
					+ "values"
					+ "(?, ?, ?)");
			
			for(int i : r.getZutaten().keySet()) {
				stmt.setInt(1, r.getRezepte_id());
				stmt.setInt(2, i);
				stmt.setInt(3, r.getZutaten().get(i));
				stmt.executeUpdate();
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		//LoescheAusDatenbank.loescheTabellenInhalt("einkauf_historie", "einkaufsliste_id");
		//LoescheAusDatenbank.loescheTabellenInhalt("produkte_aus_einkaufsliste", "einkaufsliste_id");
		
		for(int i = 5; i < 15; i++) {
			SpeicherInDatenbank.speicherUserInDatenbank(new User(i, "User " + i, "Nachname " + i, 
					"email" + i + "@webmail.com", new Adresse("Street" + i, i + "a", "1234" + i, "City" + i), PasswortManager.generateHash("passwort")));
		}
		
		Connection con = DBConnection.getConnection();
		String[] laeden = {"", "lidl", "penny", "rewe", "netto"};
		Food f = null;
		int random = 0;
		int random_user = 0;
		String laden = "";
		PreparedStatement stmt;
		PreparedStatement stmt_2;
		ResultSet r;
		ResultSet r_2;
		Einkaufsliste l = null; 
		User u = null;
		
		for(int i = 0; i < 100; i++) {
			
			random = (int) (Math.random() * (4- 1) + 1);
			random_user = (int) (Math.random() * (15 - 5) + 5);
		
			laden = laeden[random];
			try {
				
				stmt_2 = con.prepareStatement("select * from users where id = ?");
				stmt_2.setInt(1, random_user);
				r_2 = stmt_2.executeQuery();

				l = new Einkaufsliste(IdGenerator.generiereEinkaufslistenID(), DatumsManager.aktuellesDatum());
				
				int random_anzahl_produkte = (int) (Math.random() * (10 - 4)+ 4);
				
				
				for(int j = 0; j < random_anzahl_produkte; j++) {
					stmt = con.prepareStatement("select * from " + laden + "_sortiment"
							+ " left join images "
							+ "on " + laden + "_sortiment.image_id = images.id "
									+ "where artikelnr = ?");
					int random_artikel_nr = (int) (Math.random() * (93 - 2) + 2);
					
					stmt.setInt(1, random_artikel_nr);
					r = stmt.executeQuery();
					
					while(r.next()) {
						f = new Food(r.getString("artikelbez"), r.getDouble("artikelpreis"),
								r.getString("hersteller"), r.getString("pfad"), r.getInt("vegetarisch"), 
								r.getInt("vegan"), r.getInt("lokal"), r.getInt("bio"), r.getString("kategorie"));
						f.setArtikelNr(r.getInt("artikelnr"));
					}
					int random_anzahl = (int) (Math.random() * (6 - 2) + 2);
					
					l.addProduktZuListe(f, random_anzahl);
				}
			
				while(r_2.next()) {
					u = new User(r_2.getInt("id"), r_2.getString("vorname"), r_2.getString("nachname") ,
							r_2.getString("email"), new Adresse(r_2.getString("strasse"), r_2.getString("hausnummer"),
									r_2.getString("plz"), r_2.getString("ort")), r_2.getString("passwort"));
				}
				
				PreparedStatement sucheLaden = con.prepareStatement("select laden_id from laeden where bez = ?");
				sucheLaden.setString(1, laden);
				
				ResultSet laden_result_set = sucheLaden.executeQuery();
				int laden_id_result = 0;
				
				while(laden_result_set.next()) {
					laden_id_result = laden_result_set.getInt("laden_id");
				}
				
				l.berechneGesamtpreis();
				
				for(int key : l.getProduktliste().keySet()) {
					l.getProduktliste().get(key).setUrsprungsmarkt(laden_id_result);
				}
				
				u.setEinkaufsliste(l);
				SpeicherInDatenbank.speicherEinkaufslisteInDatenbank(l);
				
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
