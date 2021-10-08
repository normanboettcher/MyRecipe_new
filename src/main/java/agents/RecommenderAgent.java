package agents;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.dfki.mycbr.core.ICaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.BooleanDesc;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.AmalgamationFct;
import de.dfki.mycbr.core.similarity.IntegerFct;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.core.similarity.SymbolFct;
import de.dfki.mycbr.core.similarity.config.AmalgamationConfig;
import de.dfki.mycbr.core.similarity.config.NumberConfig;
import de.dfki.mycbr.core.similarity.config.StringConfig;
import de.dfki.mycbr.io.XMLExporter;
import de.dfki.mycbr.util.Pair;
import general.supermarkets.Rezepte;

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
