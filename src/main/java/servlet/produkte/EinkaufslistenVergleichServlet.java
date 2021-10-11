package servlet.produkte;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import agents.AngeboteAgent;
import agents.EinkaufslistenVergleichsAgent;
import general.Einkaufsliste;
import general.Food;

/**
 * Servlet implementation class EinkaufslistenVergleichServlet
 */
@WebServlet("/EinkaufslistenVergleichServlet")
public class EinkaufslistenVergleichServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EinkaufslistenVergleichServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int checked_id = Integer.parseInt(request.getParameter("rezept_checked"));
		EinkaufslistenVergleichsAgent agent = new EinkaufslistenVergleichsAgent();
		AngeboteAgent angebot_agent = new AngeboteAgent();
		
		HashMap<Integer, Einkaufsliste> einkaufslisten_alt = agent.erstelleEinkaufslistenFuerAlleLaeden(checked_id); 
		HashMap<Integer, Einkaufsliste> einkaufslisten_neu = new HashMap<Integer, Einkaufsliste>();
		
		agent.vergleicheEinkaufslisten(einkaufslisten_alt);
		
		for(int i : einkaufslisten_alt.keySet()) {
			Einkaufsliste liste_mit_angebot = angebot_agent.produktImAngebot(i, einkaufslisten_alt.get(i));
			einkaufslisten_neu.put(liste_mit_angebot.getEinkaufslisteID(), liste_mit_angebot);
		}
		
		agent.vergleicheEinkaufslisten(einkaufslisten_neu);
		
		HashMap<Integer, Einkaufsliste> sortiert = agent.getEinkaufslistenSortiertNachPreis();
		
		request.setAttribute("result", sortiert);
		
		//System.out.println(sortiert.size());
		
		//System.out.println(sortiert.get(0).getProduktliste().size());
	
		request.getRequestDispatcher("JSP/einkaufslistenVergleich.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
