package servlet.produkte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import agents.SenderAgent;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import general.supermarkets.RezeptAnfrage;
import general.supermarkets.Rezepte;
import jade.connector.JadeConnector;


@WebServlet("/RezepteServlet")
public class RezepteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<Pair<Instance, Similarity>> result;
	ArrayList<Rezepte> resultingRezepte; 
	
    /**
     * get Methode.
     * @param request request.
     * @param response response.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get parameters from the formular on index.jsp
		
		String inputTitel = request.getParameter("titel"); 
		String inputRezepte_id = request.getParameter("rezepte_id");
		
		String[] inputKueche = request.getParameterValues("kueche"); 
		String[] inputGerichteart = request.getParameterValues("gerichteart"); 
		String[] inputEigenschaften = request.getParameterValues("eigenschaften");
		
		try {
			@SuppressWarnings("unused")
			int inputIdParsed = 0;
			
			if(inputRezepte_id != null) {
				inputIdParsed = Integer.parseInt(inputRezepte_id);
			} 
			
			if(inputTitel == null) {
				inputTitel = "";
			}
			
			RezeptAnfrage queryRezepte = new RezeptAnfrage(inputTitel, inputKueche, inputGerichteart, inputEigenschaften); 
			
			SenderAgent sender = new SenderAgent();
			
			JadeConnector.runAgentsForCBRQuery(sender, queryRezepte);
			
			Thread.sleep(6000);
			
			@SuppressWarnings("unchecked")
			ArrayList<Rezepte>rezepte = (ArrayList<Rezepte>) sender.getObjectToSend();
			request.setAttribute("resultingRezepte", rezepte);
			
			request.getRequestDispatcher("JSP/rezepte.jsp").forward(request, response);
		} catch (Exception ex)  {
			request.setAttribute("error", "[DEBUG] RezepteServlet.java: Type Conversion Error! Please insert a number for the year. And don't mess around with the Award Boolean!"); 
			System.out.println("Error: " + ex.getMessage());
			request.getRequestDispatcher("JSP/error.jsp").forward(request, response);
		}
	}
	
	/**
	 * post- Methode.
	 * @param request request.
	 * @param response response.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
