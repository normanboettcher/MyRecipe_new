package servlet.produkte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import agents.RecipeAgent;
import general.supermarkets.Rezepte;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;


@WebServlet("/RezepteServlet")
public class RezepteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<Pair<Instance, Similarity>> result;
	ArrayList<Rezepte> resultingRezepte; 
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get parameters from the formular on index.jsp
		
		String inputTitel = request.getParameter("titel"); 
		String inputRezepte_id = request.getParameter("rezepte_id");
		
		String inputKueche = request.getParameter("kueche"); 
		String inputGerichteart = request.getParameter("gerichteart"); 
		String inputEigenschaften = request.getParameter("eigenschaften");
		
		System.out.println(inputKueche);
		
		
		try {
			int inputIdParsed = 0;
			
			if(inputRezepte_id != null) {
				inputIdParsed = Integer.parseInt(inputRezepte_id);
			} 
			
			if(inputTitel == null) {
				inputTitel = "";
			}
			
			Rezepte queryRezepte = new Rezepte(inputTitel, inputKueche, inputGerichteart, inputEigenschaften, inputIdParsed); 
			RecipeAgent recipeAgent = new RecipeAgent(); 
			result = recipeAgent.startQuery(queryRezepte); 
			System.out.println(result == null);
			resultingRezepte = recipeAgent.print(result, 7);
			System.out.println(resultingRezepte == null);
			
			for(int i = 0; i < resultingRezepte.size(); i++ ) {
				System.out.println(resultingRezepte.get(i).getTitel() + "-" + resultingRezepte.get(i).getSimilarity() + " Prozent");
			}

			request.setAttribute("resultingRezepte", resultingRezepte);
			
			// Forward parameters back to the form for usability
			request.setAttribute("inputTitle", inputTitel);
			request.setAttribute("inputKueche", inputKueche);
			request.setAttribute("inputGerichteart", inputGerichteart);
			request.setAttribute("inputEigenschaften", inputEigenschaften);
			request.setAttribute("inputRezepte_id", inputIdParsed);
			
			request.getRequestDispatcher("JSP/rezepte.jsp").forward(request, response);
		} catch (Exception ex)  {
			request.setAttribute("error", "[DEBUG] RezepteServlet.java: Type Conversion Error! Please insert a number for the year. And don't mess around with the Award Boolean!"); 
			System.out.println("Error: " + ex.getMessage());
			request.getRequestDispatcher("JSP/error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
