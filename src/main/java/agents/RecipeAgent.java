package agents;

import java.io.File;
import java.io.IOException;
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
import de.dfki.mycbr.core.similarity.config.StringConfig;
import de.dfki.mycbr.util.Pair;
import general.supermarkets.RezeptAnfrage;
import general.supermarkets.Rezepte;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.UnreadableException;
/**
 * Klasse, um einen RezeptAgenten mit CB zu realiseren.
 * @author norman
 *
 */
public class RecipeAgent extends Agent {
	//Attribute
	/**
	 * 
	 */
	private static final long serialVersionUID = 4018246584458218215L;
	private static String projectName = "/myCBR/Rezepte.prj";
	private static String project_path = System.getProperty("user.dir");

	// Attributes for myCBR
	private Project project;
	private Concept RezepteConcept;
	// private ICaseBase casebase;
	private Retrieval retrieve;

	private String name;

	// Attributes of our book, preparation for CBR
	private StringDesc titelDesc;
	private SymbolDesc kuecheDesc;
	private SymbolDesc gerichteartDesc;
	private SymbolDesc eigenschaftenDesc;
	private IntegerDesc rezepte_idDesc;
	
	/**
	 * Konstruktor des RezeptAgenten. Der {@code name} wird fest auf 'RezeptAgent'
	 * gelegt.
	 */
	public RecipeAgent() {
		this.name = "RezeptAgent";
	}

	/**
	 * Rueckgabe des Agentennamens.
	 * 
	 * @return name
	 */
	public String getAgentName() {
		return name;
	}
	/**
	 * Agent wird registriert.
	 */
	protected void setup() {

		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();

		sd.setName("RezeptAgent");
		sd.setType("Rezepte Agent");
		desc.addServices(sd);

		try {
			DFService.register(this, desc);
		} catch (FIPAException fe) {
			System.out.println(fe.getMessage());
		}

		addBehaviour(new RecipeAgentBehavior());
	}
	/**
	 * Agent wird deregistriert.
	 */
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}

	/**
	 * private Klasse, um das Verhalten des RezeptAgenten zu realisieren.
	 * 
	 * @author norman
	 *
	 */
	private class RecipeAgentBehavior extends Behaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2258555579999397566L;
		boolean finished = false;
		
		/**
		 * In der action wird ein CBR- Projekt impoertiert und ggf., bei Aufforderung
		 * durch einen Ueberwachungsagenten eine CBR- Query getaetigt.
		 */
		public void action() {
			String str = "";
			jade.lang.acl.ACLMessage empfang = blockingReceive();
			String conv_id = "";

			if (empfang != null) {
				conv_id = empfang.getConversationId();
			} else {
				empfang = blockingReceive();
			}

			if (empfang != null && conv_id.equals("CBRImport")) {
				importProject();
				str = "Agent: [ " + getName() + " ] hat Anfrage von [ " + empfang.getSender() + " ] "
						+ "entegegen genommen. Mit KonversationID : [ " + empfang.getConversationId() + " ]" + " \n";
				jade.lang.acl.ACLMessage antwort = empfang.createReply();

				str += "Agent : [ " + getName() + " ] hat Antwort [ " + antwort + " ] vorbereitet. \n";
				str += "KonversationsID : [ " + antwort.getConversationId() + " ] \n";
				str += "Dadurch ist der CBRImport erfolgreich abgeschlossen \n";

				antwort.setConversationId("CBRImportFertig");
				send(antwort);

				send(UeberwachungsAgent.sendToProtokollAgent(str, "0"));

			} else if (empfang != null && conv_id.equals("CBRQuery")) {
				RezeptAnfrage r = null;
				try {
					r = (RezeptAnfrage) empfang.getContentObject();
					List<Pair<Instance, Similarity>> rezept_nach_query = startQuery(r);
					ArrayList<Rezepte> fertige_rezepte_auswahl = getFertigeRezepte(rezept_nach_query, 5);

					jade.lang.acl.ACLMessage antwort_zu_sender = new jade.lang.acl.ACLMessage(
							jade.lang.acl.ACLMessage.INFORM);

					antwort_zu_sender.setConversationId("ProzessBeendetQuery");
					antwort_zu_sender.addReceiver(new AID("SendeAgent", AID.ISLOCALNAME));
					antwort_zu_sender.setContentObject(fertige_rezepte_auswahl);
					send(antwort_zu_sender);
					this.finished = true;

					String str2 = "Agent : [ " + getName() + " ] hat die Aufforderung zur Query " + "von [ "
						+ empfang.getSender() + " ] mit der KonversationsID " + "[ " + empfang.getConversationId()
						+ " ] empfangen. \n " + "Methode startQuery() wurde gestartet :  startQuery(r) "
						+ " \n  Agent : [ " + getName() + " ] uebersendet " + "Ergebnis der Query mit [ "
						+ antwort_zu_sender + " ]" + " an SendeAgenten. \n";
				// Schreibe Zwischenstand in Datei
				send(UeberwachungsAgent.sendToProtokollAgent(str2, "2"));

			} catch (UnreadableException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			block();
		}
	}
	
		/**
		 * 
		 */
	public boolean done() {
		return finished;
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

			// System.out.println(project.getPath());
			// System.out.println(file.getAbsolutePath());
			while (project.isImporting()) {
				Thread.sleep(3000);
				System.out.println(".");
			}
			setRezepteKonzept(project.getConceptByID("RezepteConcept"));
			// System.out.println(project);

			return true;
		} catch (Exception e) {
			System.err.println("[DEBUG] RecipeAgent.java: Projekt(pfad) konnte nicht gefunden werden.");
			return false;
		}
	}

	/**
	 * Methode zum setzen des Konzeptes.
	 * 
	 * @param c das Konzept aus der CB.
	 */
	private void setRezepteKonzept(Concept c) {
		this.RezepteConcept = c;
	}

	/**
	 * Rueckgabe des Konzeptes.
	 * 
	 * @return RezepteConcept
	 */
	private Concept getRezepteConcept() {
		return RezepteConcept;
	}
	
	/**
	 * private Methode, um eine query an die CB zu starten.
	 * 
	 * @param rezepte Die Eigenschaften der Anfrage fuer ein Rezept aus der CB.
	 * @return das Ergebnis der Query.
	 * @throws ParseException
	 */
	private List<Pair<Instance, Similarity>> startQuery(RezeptAnfrage rezepte) throws ParseException {
		// Get the values of the request
		titelDesc = (StringDesc) getRezepteConcept().getAllAttributeDescs().get("Titel");
		// titelDesc.addStringFct(StringConfig.LEVENSHTEIN, "titelfct", true);
		kuecheDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Kueche");
		gerichteartDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Gerichteart");
		// System.out.println("Description "+
		// getRezepteConcept().getAllAttributeDescs().get("Kueche"));
		eigenschaftenDesc = (SymbolDesc) getRezepteConcept().getAllAttributeDescs().get("Eigenschaften");
		rezepte_idDesc = (IntegerDesc) getRezepteConcept().getAllAttributeDescs().get("Rezepte_Id");

		// Wenn nach keinem Rezept gesucht wurde, dann ist die Gewichtung des Titels 0.
		if (rezepte.getTitel() == "") {
			getRezepteConcept().getActiveAmalgamFct().setWeight(titelDesc, 0);
		} else {
			titelDesc.addStringFct(StringConfig.LEVENSHTEIN, "default function", true);
			getRezepteConcept().getActiveAmalgamFct().setWeight(titelDesc, 2);
		}


		retrieve = new Retrieval(getRezepteConcept(), project.getCB("Casebase"));
		retrieve.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
		Instance query = retrieve.getQueryInstance();

		query.addAttribute(kuecheDesc, kuecheDesc.getAttribute(rezepte.getKueche()[0]));
		
		query.addAttribute(gerichteartDesc, gerichteartDesc.getAttribute(rezepte.getGerichteart()[0]));
		
		query.addAttribute(eigenschaftenDesc, eigenschaftenDesc.getAttribute(rezepte.getEigenschaften()[0]));
	
		// Send query
		retrieve.start();
		System.out.println("[DEBUG] RecipeAgent: Query successful!");
		return retrieve.getResult();
	}

	/**
	 * Methode, um die Rezepte als ArrayList aus der Query wiederzugeben.
	 * 
	 * @param result            die fertigen Rezepte.
	 * @param numberOfBestCases die Anzahl der besten Faelle, die ausgegeben werden
	 *                          muessen.
	 * @return resultingRezepte die fertigen Rezepte.
	 */
	public ArrayList<Rezepte> getFertigeRezepte(List<Pair<Instance, Similarity>> result, int numberOfBestCases) {

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
			// System.out.println(result.get(i).getFirst().getName() + " - Similarity: "
			// + Math.floor(result.get(i).getSecond().getValue() * 100) / 100);
		}
		return resultingRezepte;
	}
}
