package servlet.user;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaseConnection.DBConnection;
import general.Administrator;
import general.Adresse;
import general.User;
import managers.PasswortManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = null;
		try {
			password = PasswortManager.generateHash(request.getParameter("pw"));
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(pruefungUsername(username) == false || pruefungPasswort(password, username) == false) {
			
			request.setAttribute("usernameVorhanden", pruefungUsername(username));

			request.setAttribute("passwortKorrekt", pruefungPasswort(password, username));
			request.getRequestDispatcher("JSP/login.jsp").forward(request, response);
		} else {
			HttpSession session = request.getSession();
			/*
			 * Wenn Username und Passwort korrekt sind, muss die Rolle dieses Users ueberprueft werden.
			 */
			if(!(userIstAdmin(username))) {
				session.setAttribute("user", holeEingeloggtenUser(username));
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				session.setAttribute("admin", holeEingeloggtenAdmin(username));
				request.getRequestDispatcher("JSP/index.jsp").forward(request, response);
			}
		}
		
	}

	/**
	 * Diese Methode prueft, ob ein korekter username eingegeben wurde.
	 * 
	 * @param username der zu pruefende Username.
	 * @return true or false.
	 */
	private boolean pruefungUsername(String username) {
		boolean usernameVorhanden = false;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("select email from users");
			ResultSet r = stmt.executeQuery();
			
			while(r.next() && !(usernameVorhanden)) {
				if(r.getString("email").equals(username)) {
					usernameVorhanden = true;
				}
			}
			r.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return usernameVorhanden;
	}

	/**
	 * Diese Methode prueft das Passwort eines uebergebenen users.
	 * 
	 * @param passwort das zu pruefende Passwort.
	 * @param username der username, fuer den das Passwort geprueft wird.
	 * @return true or false.
	 */
	private boolean pruefungPasswort(String passwort, String username) {
		boolean passwortKorrekt = false;
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select passwort from users where email = ?");
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();
			
			while(r.next() && !(passwortKorrekt)) {
				if(r.getString("passwort").equals(passwort)) {
					passwortKorrekt = true;
				}
			}
			stmt.close();
			r.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		return passwortKorrekt;                                                                                                                              
	}

	/**
	 * Diese Methode holt einen Kunden aus der Datenbank, falls die Anmeldedaten mit
	 * einem Kunden uebereinstimmen.
	 * 
	 * @param username der Username, der sich eingeloggt hat.
	 * @return usr der User aus der Datenbank.
	 */
	private User holeEingeloggtenUser(String username) {
		User usr = null;
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from users where email = ?");
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();
			
			while(r.next()) {
				usr = new User(r.getString("vorname"), r.getString("nachname"), r.getString("email"), 
						new Adresse(r.getString("strasse"), r.getString("hausnummer"), r.getString("plz"), r.getString("ort")), 
						r.getString("passwort"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		return usr;
	}
	/**
	 * Diese Methode holt einen Administrator aus der Datenbank, falls die Anmeldedaten mit einem Admin uebereinstimmen.
	 * 
	 * @param username der Username, der sich eingeloggt hat.
	 * @return a der Admin aus der Datenbank.
	 */
	private Administrator holeEingeloggtenAdmin(String username) {
		Administrator a  = null;
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from users where email = ? AND rolle = ?");
			stmt.setString(1, username);
			stmt.setString(2, "admin");
			ResultSet r = stmt.executeQuery();
			
			while(r.next()) {
				a = new Administrator(r.getString("vorname"), r.getString("nachname"), r.getString("email"), 
						new Adresse(r.getString("strasse"), r.getString("hausnummer"), r.getString("plz"), r.getString("ort")), 
								r.getString("passwort"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		return a;
	}

	/**
	 * Diese Methode prueft, ob die Benutzerdaten des eingeloggten Users mit denen
	 * eines Admins uebereinstimmen oder nicht.
	 * 
	 * @param username der zu pruefende Username.
	 * @return true or false.
	 */
	private boolean userIstAdmin(String username) {
		boolean istAdmin = false;
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select rolle from users where email = ?");
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();
			
			while(r.next() && !(istAdmin)) {
				if(r.getString("rolle").equals("admin")) {
					istAdmin = true;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		return istAdmin;
	}

}
