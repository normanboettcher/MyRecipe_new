package jade.connector;

import java.util.HashMap;

import agents.AktualisiereAngeboteAgent;
import agents.AngeboteAgent;
import agents.EinkaufslistenVergleichsAgent;
import agents.ProtokollAgent;
import agents.RecipeAgent;
import agents.SenderAgent;
import agents.UeberwachungsAgent;
import general.supermarkets.RezeptAnfrage;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
/**
 * Klasse, um eine Jade Runtime zu realisieren.
 * @author norman
 *
 */
public class JadeConnector {
	//Attribute
	private static jade.core.Runtime rt;
	private static Profile profile;
	private static ProfileImpl start_profil;
	private static AgentContainer agent_container;
	@SuppressWarnings("unused")
	private static AgentContainer main_container;
	@SuppressWarnings("unused")
	private static AgentController ac;
	
	/**
	 * Methode zum Starten einer Runtime mit mehreren Agenten.
	 * 
	 * @param agenten Agenten, die uebergeben werden.
	 */
	public static void startAgentsZumBerechnen(HashMap<String, Agent> agenten) {
		
		EinkaufslistenVergleichsAgent vergleichsagent = (EinkaufslistenVergleichsAgent) agenten.get("Vergleichsagent");
		AngeboteAgent angebote = (AngeboteAgent) agenten.get("AngebotAgent");
		
		SenderAgent sender = (SenderAgent) agenten.get("SendeAgent");
		ProtokollAgent protokoll_agent = new ProtokollAgent();
		AktualisiereAngeboteAgent a_agent = new AktualisiereAngeboteAgent();
		UeberwachungsAgent ue_agent = new UeberwachungsAgent();
		
		vergleichsagent.setStatus("start");
		ue_agent.setStatus("");
		
		rt = jade.core.Runtime.instance();
		rt.setCloseVM(true);
		//create default profile and main container
		profile = new ProfileImpl();
		main_container = rt.createMainContainer(profile);
		
		System.out.println("Main Container and default profile created");
		
		//now set default profile to start a container
		start_profil = new ProfileImpl();
		
		System.out.println("Now launching agent container.." + start_profil);
		agent_container = rt.createAgentContainer(start_profil);
		
		System.out.println("Launch AgentContainer after " + start_profil);
		System.out.println("Containers created");
		
		try {
			ac = agent_container.acceptNewAgent("Vergleichsagent", vergleichsagent);
			ac = agent_container.acceptNewAgent("AngebotAgent", angebote);
			ac = agent_container.acceptNewAgent("SendeAgent", sender);
			ac = agent_container.acceptNewAgent("ProtokollAgent", protokoll_agent);
			ac = agent_container.acceptNewAgent("UeberwachungAgent", ue_agent);
			ac = agent_container.acceptNewAgent("AktualisierungsAgent", a_agent);
			
			
			agent_container.getAgent("ProtokollAgent").start();
			agent_container.getAgent("Vergleichsagent").start();
			agent_container.getAgent("AngebotAgent").start();
			agent_container.getAgent("UeberwachungAgent").start();
			agent_container.getAgent("AktualisierungsAgent").start();
			agent_container.getAgent("SendeAgent").start();
			
		} catch (StaleProxyException e) {
			e.printStackTrace();
		} catch (ControllerException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Methode, um Agenten fuer CBR- Prozess zu starten.
	 * 
	 * @param s       Ein Sender Agent der mit uebergeben wird.
	 * @param anfrage Die Anfrage fuer das CBR.
	 */
	public static void runAgentsForCBRQuery(SenderAgent s, RezeptAnfrage anfrage) {
		UeberwachungsAgent ue_agent =new UeberwachungsAgent();
		RecipeAgent r_agent = new RecipeAgent();
		SenderAgent s_agent = s;
		ProtokollAgent p_agent = new ProtokollAgent();
		
		ue_agent.setRezeptAnfrage(anfrage);
		ue_agent.setStatus("start");
		
		rt = jade.core.Runtime.instance();
		rt.setCloseVM(true);
		//create default profile and main container
		profile = new ProfileImpl();
		main_container = rt.createMainContainer(profile);
		
		System.out.println("Main Container and default profile created");
		
		//now set default profile to start a container
		start_profil = new ProfileImpl();
		
		System.out.println("Now launching agent container.." + start_profil);
		agent_container = rt.createAgentContainer(start_profil);
		
		System.out.println("Launch AgentContainer after " + start_profil);
		System.out.println("Containers created");
		
		System.out.println("Launching Vergleichsagent and AngebotAgent on main container...");
		
		try {
			ac = agent_container.acceptNewAgent("UeberwachungAgent", ue_agent);
			ac = agent_container.acceptNewAgent("RezeptAgent", r_agent);
			ac = agent_container.acceptNewAgent("ProtokollAgent", p_agent);
			ac = agent_container.acceptNewAgent("SendeAgent", s_agent);
			
			agent_container.getAgent("ProtokollAgent").start();
			agent_container.getAgent("UeberwachungAgent").start();
			agent_container.getAgent("RezeptAgent").start();
			agent_container.getAgent("SendeAgent").start();
			
		} catch (StaleProxyException e) {
			e.printStackTrace();
		} catch (ControllerException e) {
			e.printStackTrace();
		} 
	}
}

