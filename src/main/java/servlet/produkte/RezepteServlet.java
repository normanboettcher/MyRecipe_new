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
		String inputTitle = request.getParameter("title"); 
		String inputKüche = request.getParameter("küche"); 
		String inputGerichteart = request.getParameter("gerichteart"); 
		String inputEigenschaften = request.getParameter("eigenschaften"); 
		
		
		try {
			Rezepte queryRezepte = new Rezepte(inputTitle, inputKüche, inputGerichteart, inputEigenschaften); 
			RecipeAgent recipeAgent = new RecipeAgent(); 
			result = recipeAgent.startQuery(queryRezepte); 
			resultingRezepte = recipeAgent.print(result, 5);
			request.setAttribute("resultingRezepte", resultingRezepte);
			
			// Forward parameters back to the form for usability
			request.setAttribute("inputTitle", inputTitle);
			request.setAttribute("inputKüche", inputKüche);
			request.setAttribute("inputGerichteart", inputGerichteart);
			request.setAttribute("inputEigenschaften", inputEigenschaften);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (Exception ex)  {
			request.setAttribute("error", "[DEBUG] RezepteServlet.java: Type Conversion Error! Please insert a number for the year. And don't mess around with the Award Boolean!"); 
			System.out.println("Error: " + ex.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
