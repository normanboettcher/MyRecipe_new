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
import jade.connector.JadeConnector;
import jade.core.Agent;

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
		HashMap<String, Agent> agenten_map = new HashMap<String, Agent>();
		EinkaufslistenVergleichsAgent vergleich = new EinkaufslistenVergleichsAgent(checked_id);
		AngeboteAgent angebote = new AngeboteAgent();
		
		agenten_map.put(vergleich.getAgentName(), vergleich);
		agenten_map.put(angebote.getAgentName(), angebote);
		
		System.out.println("Start Agents and let them communicate");
		new JadeConnector(agenten_map);
		
		System.out.println("Okay...now we are here....but did it really work? Who knows");
		HashMap<Integer, Einkaufsliste> sortiert = vergleich.getEinkaufslistenSortiertNachPreis();
		
		System.out.println(sortiert == null);
		
		for(Entry<Integer, Einkaufsliste> l : sortiert.entrySet()) {
			System.out.println(l.getValue().getEinkaufslisteID() + " " + l.getValue().getGesamtPreis() + " " + l.getValue().getErsparnis());
		}
		
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
