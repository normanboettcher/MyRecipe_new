package database.transfer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.DBConnection;
import general.User;
import managers.IdGenerator;

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
			schreibeUserInDatenbank.setString(6, usr.getAdress().getStreet());
			schreibeUserInDatenbank.setString(7, usr.getAdress().getNumber());
			schreibeUserInDatenbank.setString(8, usr.getAdress().getPLZ());
			schreibeUserInDatenbank.setString(9, usr.getAdress().getCity());
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
			+ laden + "/" +f.getName());
			schreibeBildInDatenbank.executeUpdate();
			schreibeBildInDatenbank.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	
		
		Connection con = DBConnection.getConnection();
		
		
		
	}
}
