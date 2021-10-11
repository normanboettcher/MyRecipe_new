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

import database.transfer.SpeicherInDatenbank;
import databaseConnection.DBConnection;
import general.Adresse;
import general.User;
import managers.IdGenerator;
import managers.PasswortManager;
import managers.RegExManager;

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
	
		String passwort = null;
		String passwort2 = null;
		
		try {
			passwort = PasswortManager.generateHash(req.getParameter("passwort"));
			passwort2 = PasswortManager.generateHash(req.getParameter("passwort2"));
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		checkEingaben(passwort, passwort2, req, res);	
	}
	
	private void checkEingaben(String passwort, String passwort2, HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		boolean fehler_aufgetreten = false;
		
		String email = req.getParameter("email");
		String vorname = req.getParameter("vor_name");
		String nachname = req.getParameter("nach_name");
		String strasse = req.getParameter("strasse");
		String hausnummer = req.getParameter("nr");
		String plz = req.getParameter("plz");
		String ort = req.getParameter("stadt");
		
		if(pruefeObEmailVorhanden(email)) {
			fehler_aufgetreten = true;
			boolean email_vorhanden = true;
			req.setAttribute("fehlermeldung_email", email_vorhanden);
		}
		
		if(!(passwort.equals(passwort2))) {
			boolean passwort_nicht_identisch = true;
			fehler_aufgetreten = true;
			req.setAttribute("fehlermeldung_passwort", passwort_nicht_identisch);
		}
		
		if(!(RegExManager.pruefeEmail(email))) {
			fehler_aufgetreten = true;
			boolean email_falsch = true;
			req.setAttribute("email_falsch", email_falsch);
		}
		
		if (!(RegExManager.pruefeHausnummer(hausnummer))) {
			fehler_aufgetreten = true;
			boolean hn_falsch = true;
			req.setAttribute("hn_falsch", hn_falsch);
		}
		
		if(!(RegExManager.pruefePasswort(passwort))) {
			fehler_aufgetreten = true;
			boolean pw_falsch = true;
			req.setAttribute("pw_falsch", pw_falsch);
		}
		
		if(!(RegExManager.pruefePLZ(plz))) {
			fehler_aufgetreten = true;
			boolean plz_falsch = true;
			req.setAttribute("plz_falsch", plz_falsch);
		}
		
		if(!(RegExManager.pruefeStadt(ort))) {
			fehler_aufgetreten = true;
			boolean city_falsch = true;
			req.setAttribute("city_falsch", city_falsch);
		}
		
		if(!(RegExManager.pruefeStrasse(strasse))) {
			fehler_aufgetreten = true;
			boolean str_falsch = true;
			req.setAttribute("str_falsch", str_falsch);
		}
		
		if(fehler_aufgetreten) {
			req.getRequestDispatcher("JSP/login.jsp").forward(req, res);
			
		} else {
			User usr = new User(IdGenerator.generiereUserID(),vorname ,nachname, email,
					new Adresse(strasse, hausnummer, plz, ort), passwort);

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
