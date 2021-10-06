package agents;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import general.supermarkets.Rezepte;
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

public class RecipeAgent {

	private static String dataPath = "/MyRecipe/myCBR/";
	private static String projectName = "Rezepte.prj";

	// Attributes for myCBR
	private Project project;
	private Concept RezepteConcept;
	private ICaseBase casebase;
	private AmalgamationFct rezeptGlobalSim;
	private Retrieval retrieve;

	// Attributes of our book, preparation for CBR
	private StringDesc titleDesc;
	private SymbolDesc kücheDesc;
	private SymbolDesc gerichteartDesc;
	private SymbolDesc eigenschaftenDesc;


	public RecipeAgent() {
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
			Thread.sleep(2000);
			RezepteConcept = project.getConceptByID("RezepteConcept");
			return true;
		} catch (Exception e) {
			System.err.println("[DEBUG] RecipeAgent.java: Projekt(pfad) konnte nicht gefunden werden.");
			return false;
		}
	}

	public List<Pair<Instance, Similarity>> startQuery(Rezepte rezepte) {
		// Get the values of the request
		titleDesc = (StringDesc) this.RezepteConcept.getAllAttributeDescs().get("Title");
		kücheDesc = (SymbolDesc) this.RezepteConcept.getAllAttributeDescs().get("Küche");
		gerichteartDesc = (SymbolDesc) this.RezepteConcept.getAllAttributeDescs().get("Gerichteart");
		eigenschaftenDesc = (SymbolDesc) this.RezepteConcept.getAllAttributeDescs().get("Eigenschaften");

		// Insert values into query
		try {
			retrieve = new Retrieval(RezepteConcept, casebase);
			retrieve.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
			Instance query = retrieve.getQueryInstance();
			query.addAttribute(titleDesc, titleDesc.getAttribute(rezepte.getTitle()));
			query.addAttribute(kücheDesc, kücheDesc.getAttribute(rezepte.getKüche()));
			query.addAttribute(gerichteartDesc, gerichteartDesc.getAttribute(rezepte.getGerichteart()));
			query.addAttribute(eigenschaftenDesc, eigenschaftenDesc.getAttribute(rezepte.getEigenschaften()));
		} catch (ParseException e) {
			System.err.println("[ERROR] RecipeAgent: Error while creating the query! " + e.getMessage());
		}

		// Send query
		retrieve.start();
		System.out.println("[DEBUG] RecipeAgent: Query successful!");
		return retrieve.getResult();
	}

	public ArrayList<Rezepte> print(List<Pair<Instance, Similarity>> result, int numberOfBestCases) {

		ArrayList<Rezepte> resultingRezepte = new ArrayList<Rezepte>();
		for (int i = 0; i < numberOfBestCases; i++) {
			// result is already ordered. So we can just add n Wireshark objects to the
			// list.
			Instance obj = RezepteConcept.getInstance(result.get(i).getFirst().getName());
			Rezepte rezepte = new Rezepte(obj.getAttForDesc(titleDesc).getValueAsString(),
					obj.getAttForDesc(kücheDesc).getValueAsString(),
					obj.getAttForDesc(gerichteartDesc).getValueAsString(),
					obj.getAttForDesc(eigenschaftenDesc).getValueAsString());

			resultingRezepte.add(rezepte);
			resultingRezepte.get(i).setSimilarity(result.get(i).getSecond().getValue());
			System.out.println(result.get(i).getFirst().getName() + " - Similarity: "
					+ Math.floor(result.get(i).getSecond().getValue() * 100) / 100);
		}
		return resultingRezepte;
	}

}
