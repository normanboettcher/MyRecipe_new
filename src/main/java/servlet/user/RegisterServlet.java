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

import databaseConnection.DBConnection;
import general.Adresse;
import managers.PasswortManager;
import general.User;
import database.transfer.SpeicherInDatenbank;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean fehler_aufgetreten = false;
		String fehlermeldung_email = "";
		String fehlermeldung_passwort = "";
		
		String email = req.getParameter("email");
		String vorname = req.getParameter("vor_name");
		String nachname = req.getParameter("nach_name");
		String strasse = req.getParameter("strasse");
		String hausnummer = req.getParameter("nr");
		String plz = req.getParameter("plz");
		String ort = req.getParameter("stadt");
		String passwort = null;
		String passwort2 = null;
		try {
			passwort = PasswortManager.generateHash(req.getParameter("passwort"));
			passwort2 = PasswortManager.generateHash(req.getParameter("passwort2"));
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		if(pruefeObEmailVorhanden(email)) {
			fehlermeldung_email = "Die Email-Adresse: '"+ email +"' ist bereits vergeben.";
			fehler_aufgetreten = true;
		}
		
		if(!(passwort.equals(passwort2))) {
			fehlermeldung_passwort = "Die Passw�rter stimmen nicht �berein";
			fehler_aufgetreten = true;
		}
		
		
		if(fehler_aufgetreten) {
			req.setAttribute("fehlermeldung_email", fehlermeldung_email);
			req.setAttribute("fehlermeldung_passwort", fehlermeldung_passwort);
			
			req.getRequestDispatcher("JSP/registerFail.jsp").forward(req, res);
			
		} else {
			User usr = new User(vorname, nachname, email, new Adresse(strasse, hausnummer, plz, ort), passwort);

			SpeicherInDatenbank.speicherUserInDatenbank(usr);
			
			req.getRequestDispatcher("JSP/login.jsp").forward(req, res);
		}
	}
	
	private boolean pruefeObEmailVorhanden(String email) {
		boolean email_vorhanden = false;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("select email from users");
			ResultSet r = stmt.executeQuery();
			
			while(r.next() && !email_vorhanden) {
				if(r.getString("email").equals(email)) {
					email_vorhanden = true;
				}
			}
			stmt.close();
			r.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		return email_vorhanden;
	}

}
