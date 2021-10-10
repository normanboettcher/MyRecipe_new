package servlet.produkte;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import agents.EinkaufslistenVergleichsAgent;
import general.Einkaufsliste;

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
		
		HashMap<Integer, Einkaufsliste> einkaufslisten = agent.erstelleEinkaufslistenFuerAlleLaeden(checked_id); 
		
		System.out.println("einkaufslistenanzahl: " + einkaufslisten.size());
		
		agent.vergleicheEinkaufslisten(einkaufslisten);
		
		HashMap<Integer, Einkaufsliste> sortiert = agent.getEinkaufslistenSortiertNachPreis();
		
		request.setAttribute("result", sortiert);
		
		for(int i : sortiert.keySet()) {
			System.out.println(sortiert.get(i).getLaden());
		}
		
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
