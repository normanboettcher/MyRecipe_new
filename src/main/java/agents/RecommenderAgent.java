package agents;


import de.dfki.mycbr.core.ICaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.retrieval.Retrieval;


public class RecommenderAgent {

	private static String dataPath = "/MyRecipe/myCBR/";
	private static String projectName = "Rezepte.prj";
	
	// Attributes for myCBR
		private Project project;
		private Concept RecommenderConcept;
		private ICaseBase casebase;
		private Retrieval retrieve;
		
		public RecommenderAgent() {
			importProject();
		}
		/**
		 * Funktion zur Importierung von bereits vorhandenen myCBR.prj Dateien.
		 * 
		 * @return Boolean, ob die Datei gefunden und geladen werden konnte
		 */
		private boolean importProject() {
			try {
				project = new Project(dataPath + projectName);
				Thread.sleep(5000);
				RecommenderConcept = project.getConceptByID("RecommenderConcept");
				return true;
			} catch (Exception e) {
				System.err.println("[DEBUG] RecipeAgent.java: Projekt(pfad) konnte nicht gefunden werden.");
				return false;
			}
		}

}
