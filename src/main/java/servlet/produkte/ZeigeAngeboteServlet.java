package servlet.produkte;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general.supermarkets.Lidl;
import general.supermarkets.Netto;
import general.supermarkets.Penny;
import general.supermarkets.Rewe;

/**
 * Servlet implementation class ZeigeAngeboteServlet
 */
@WebServlet("/ZeigeAngeboteServlet")
public class ZeigeAngeboteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZeigeAngeboteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String supermarkt = request.getParameter("supermarkt");
		HttpSession session = request.getSession();
		
		//System.out.println(supermarkt);
		
		switch(supermarkt) {
			case "lidl" : 
				Lidl lidl = new Lidl();
				lidl.holeAngebote();
				session.setAttribute("supermarkt", lidl);
				request.getRequestDispatcher("JSP/angebote.jsp").forward(request, response);
				break;
			case "penny":
				Penny penny = new Penny();
				penny.holeAngebote();
				session.setAttribute("supermarkt", penny);
				request.getRequestDispatcher("JSP/angebote.jsp").forward(request, response);
				break;
			case "rewe":
				Rewe rewe = new Rewe();
				rewe.holeAngebote();
				session.setAttribute("supermarkt", rewe);
				request.getRequestDispatcher("JSP/angebote.jsp").forward(request, response);
				break;
			case "netto":
				Netto netto = new Netto();
				netto.holeAngebote();
				session.setAttribute("supermarkt", netto);
				request.getRequestDispatcher("JSP/angebote.jsp").forward(request, response);
				break;
			default:
				break;
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
