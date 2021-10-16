package agents;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import general.supermarkets.RezeptAnfrage;
import general.supermarkets.Rezepte;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class RecipeAgent extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4018246584458218215L;
	private static String projectName = "/myCBR/Rezepte.prj";
	private static String project_path = System.getProperty("user.dir");

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
	}
	
	
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setName("RezepteAgent");		
		sd.setType("Rezepte Agent");
		desc.addServices(sd);
		
		try {
			DFService.register(this, desc);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
		
		addBehaviour(new RecipeAgentBehavior());
	}
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}
	
	
	private class RecipeAgentBehavior extends OneShotBehaviour {

		@Override
		public void action() {
			importProject();
			
			
		}
		
	}

	/**
	 * Funktion zur Importierung von bereits vorhandenen myCBR.prj Dateien.
	 * 
	 * @return Boolean, ob die Datei gefunden und geladen werden konnte
	 */
	private boolean importProject() {
		try {
			System.out.println(project_path);
			System.out.println(project_path + projectName);
			File file = new File(project_path + projectName);
			project = new Project(file.getCanonicalPath());
			
			//System.out.println(project.getPath());
		//	System.out.println(file.getAbsolutePath());
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
	
	public List<Pair<Instance, Similarity>> startQuery(RezeptAnfrage rezepte) throws ParseException {
		// Get the values of the request
		titelDesc = (StringDesc) getRezepteConcept().getAllAttributeDescs().get("Titel");
		//titelDesc.addStringFct(StringConfig.LEVENSHTEIN, "titelfct",  true);
		kuecheDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Kueche");
		gerichteartDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Gerichteart");
		//System.out.println("Description "+ getRezepteConcept().getAllAttributeDescs().get("Kueche"));
		eigenschaftenDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Eigenschaften");
		rezepte_idDesc =  (IntegerDesc) getRezepteConcept().getAllAttributeDescs().get("Rezepte_Id");

		//Wenn nach keinem Rezept gesucht wurde, dann ist die Gewichtung des Titels 0.
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
		
		//System.out.println(titelDesc);
		//System.out.println(kuecheDesc);
		//System.out.println(gerichteartDesc);
		//System.out.println(eigenschaftenDesc);
		
		
		//System.out.println(rezepte.getEigenschaften());
		//System.out.println(titelDesc.getAttribute(rezepte.getTitel()));
		
		
		//System.out.println("----Konnte Titel hinzufuegen: "+ query.addAttribute(titelDesc, titelDesc.getAttribute(rezepte.getTitel())));
		//System.out.println("----Konnte ID hinzufuegen: "+ query.addAttribute(rezepte_idDesc, 2));
		
		//System.out.println(rezepte.getKueche()[0]);
		query.addAttribute(kuecheDesc, kuecheDesc.getAttribute(rezepte.getKueche()[0]));
		//System.out.println("-----Konnte Kueche hinzufuegen: " + query.addAttribute(kuecheDesc, kuecheDesc.getAttribute(rezepte.getKueche()[0])));

		query.addAttribute(gerichteartDesc, gerichteartDesc.getAttribute(rezepte.getGerichteart()[0]));
		//System.out.println("-----Konnte Gerichteart hinzufuegen: " + query.addAttribute(gerichteartDesc, gerichteartDesc.getAttribute(rezepte.getGerichteart()[0])));
		
		
		query.addAttribute(eigenschaftenDesc, eigenschaftenDesc.getAttribute(rezepte.getEigenschaften()[0]));
		//System.out.println("-----Konnte Eigenschaften hinzufuegen: " + query.addAttribute(eigenschaftenDesc, eigenschaftenDesc.getAttribute(rezepte.getEigenschaften()[0])));
		//System.out.println(query.getAttForDesc(gerichteartDesc).getValueAsString());
		//System.out.println(query.getAttForDesc(eigenschaftenDesc).getValueAsString());
		//System.out.println(query.getAttForDesc(kuecheDesc).getValueAsString());

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
			//System.out.println(result.get(i).getFirst().getName() + " - Similarity: "
				//	+ Math.floor(result.get(i).getSecond().getValue() * 100) / 100);
		}
		return resultingRezepte;
	}

}
