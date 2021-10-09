package agents;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
import de.dfki.mycbr.core.retrieval.RetrievalEngine;
import de.dfki.mycbr.core.similarity.AmalgamationFct;
import de.dfki.mycbr.core.similarity.IntegerFct;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.core.similarity.SymbolFct;
import de.dfki.mycbr.core.similarity.config.AmalgamationConfig;
import de.dfki.mycbr.core.similarity.config.NumberConfig;
import de.dfki.mycbr.core.similarity.config.StringConfig;
import de.dfki.mycbr.io.XMLExporter;
import de.dfki.mycbr.util.Pair;
import general.supermarkets.RezeptAnfrage;
import general.supermarkets.Rezepte;


public class RecipeAgent {
	
	private static String projectName = "myCBR/Rezepte.prj";

	// Attributes for myCBR
	private Project project;
	private Concept RezepteConcept;
	//private ICaseBase casebase;
	private Retrieval retrieve;

	// Attributes of our book, preparation for CBR
	private StringDesc titelDesc;
	private SymbolDesc kuecheDesc;
	private SymbolDesc gerichteartDesc;
	private SymbolDesc eigenschaftenDesc;
	private IntegerDesc rezepte_idDesc;


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
			File file = new File(projectName);
			project = new Project(file.getAbsolutePath());
			
			System.out.println(project.getPath());
			System.out.println(file.getAbsolutePath());
			
			while(project.isImporting()) {
				Thread.sleep(3000);
				System.out.println(".");
			}
			setRezepteKonzept(project.getConceptByID("RezepteConcept"));
			//System.out.println(project);

			return true;
		} catch (Exception e) {
			System.err.println("[DEBUG] RecipeAgent.java: Projekt(pfad) konnte nicht gefunden werden.");
			return false;
		}
	}
	
	private void setRezepteKonzept(Concept c) {
		this.RezepteConcept = c;
	}
	
	private Concept getRezepteConcept() {
		return RezepteConcept;
	}
	
	public List<Pair<Instance, Similarity>> startQuery(RezeptAnfrage rezepte) {
		// Get the values of the request
		titelDesc = (StringDesc) getRezepteConcept().getAllAttributeDescs().get("Titel");
		//titelDesc.addStringFct(StringConfig.LEVENSHTEIN, "titelfct",  true);
		kuecheDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Kueche");
		
		gerichteartDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Gerichteart");
	
		eigenschaftenDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Eigenschaften");
		rezepte_idDesc =  (IntegerDesc) getRezepteConcept().getAllAttributeDescs().get("Rezepte_Id");

		// Insert values into query
		try {
			//getRezepteConcept().addAmalgamationFct(AmalgamationConfig.WEIGHTED_SUM, "fct", true);
			
			//System.out.println(getRezepteConcept().getName());
			
			//Wenn nach keinem Rezepot gesucht wurde, dann ist die Gewichtung des Titels 0.
			if(rezepte.getTitel() == "") {
				getRezepteConcept().getActiveAmalgamFct().setWeight(titelDesc, 0);
			}
			
			//getRezepteConcept().getActiveAmalgamFct().setWeight(gerichteartDesc, 3);
			//getRezepteConcept().getActiveAmalgamFct().setWeight(kuecheDesc, 4);
			//getRezepteConcept().getActiveAmalgamFct().setWeight(eigenschaftenDesc, 2);
			//getRezepteConcept().getActiveAmalgamFct().setWeight(titelDesc, 3);
			//getRezepteConcept().getActiveAmalgamFct().setWeight(rezepte_idDesc, 0);
			
			retrieve = new Retrieval(getRezepteConcept(), project.getCB("Casebase"));
				
			retrieve.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
		
			Instance query = retrieve.getQueryInstance();
			
			System.out.println(titelDesc);
			System.out.println(kuecheDesc);
			System.out.println(gerichteartDesc);
			System.out.println(eigenschaftenDesc);
			
			
			//System.out.println(rezepte.getEigenschaften());
			//System.out.println(titelDesc.getAttribute(rezepte.getTitel()));
			
			
			query.addAttribute(titelDesc, titelDesc.getAttribute(rezepte.getTitel()));
			
			for(int i = 0; i < rezepte.getKueche().length; i++) {
				query.addAttribute(kuecheDesc, kuecheDesc.getAttribute(rezepte.getKueche()[i]));
				System.out.println(kuecheDesc.getAttribute(rezepte.getKueche()[i]));
			}
			
			for(int i = 0; i < rezepte.getGerichteart().length; i++) {
				query.addAttribute(gerichteartDesc, gerichteartDesc.getAttribute(rezepte.getGerichteart()[i]));
			}
			
			for(int i = 0; i < rezepte.getEigenschaften().length; i++) {
				query.addAttribute(eigenschaftenDesc, eigenschaftenDesc.getAttribute(rezepte.getEigenschaften()[i]));
			}
			
			System.out.println(query.getAttForDesc(gerichteartDesc).getValueAsString());
			System.out.println(query.getAttForDesc(eigenschaftenDesc).getValueAsString());
			System.out.println(query.getAttForDesc(kuecheDesc).getValueAsString());
			
		} catch (ParseException e) {
			System.err.println("[ERROR] RecipeAgent: Error while creating the query! " + e.getMessage());
		}

		// Send query
		retrieve.start();
		System.out.println("[DEBUG] RecipeAgent: Query successful!");
		return retrieve.getResult();
	}
	
	/**
	 * 
	 * @param result
	 * @param numberOfBestCases
	 * @return
	 */
	public ArrayList<Rezepte> print(List<Pair<Instance, Similarity>> result, int numberOfBestCases) {

		ArrayList<Rezepte> resultingRezepte = new ArrayList<Rezepte>();
		for (int i = 0; i < numberOfBestCases; i++) {
			// result is already ordered. So we can just add n Wireshark objects to the
			// list.
			Instance obj = RezepteConcept.getInstance(result.get(i).getFirst().getName());
			Rezepte rezepte = new Rezepte(obj.getAttForDesc(titelDesc).getValueAsString(),
					obj.getAttForDesc(kuecheDesc).getValueAsString(),
					obj.getAttForDesc(gerichteartDesc).getValueAsString(),
					obj.getAttForDesc(eigenschaftenDesc).getValueAsString(),
					Integer.parseInt(obj.getAttForDesc(rezepte_idDesc).getValueAsString()));
			
			resultingRezepte.add(rezepte);
			resultingRezepte.get(i).setSimilarity(result.get(i).getSecond().getValue());
			System.out.println(result.get(i).getFirst().getName() + " - Similarity: "
					+ Math.floor(result.get(i).getSecond().getValue() * 100) / 100);
		}
		return resultingRezepte;
	}

}
