package jade.connector;

import java.util.HashMap;

import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import agents.AngeboteAgent;
import agents.EinkaufslistenVergleichsAgent;

public class JadeConnector {
	
	private jade.core.Runtime rt;
	private Profile profile;
	private ProfileImpl start_profil;
	private AgentContainer agent_container;
	private AgentContainer main_container;
	private AgentController ac;
	
	public JadeConnector(HashMap<String, Agent> agenten)  {
		
		
		EinkaufslistenVergleichsAgent vergleichsagent = (EinkaufslistenVergleichsAgent) agenten.get("Vergleichsagent");
		AngeboteAgent angebote = (AngeboteAgent) agenten.get("AngebotAgent");
		
		System.out.println("Vergleichagent ist null: " + agenten.get("Vergleichsagent") == null);
		System.out.println("Angebotagent ist null: " + agenten.get("AngebotAgent") == null);
		
		this.rt = jade.core.Runtime.instance();
		rt.setCloseVM(true);
		//create default profile and main container
		this.profile = new ProfileImpl();
		this.main_container = rt.createMainContainer(profile);
		
		System.out.println("Main Container and default profile created");
		
		//now set default profile to start a container
		this.start_profil = new ProfileImpl();
		
		System.out.println("Now launching agent container.." + start_profil);
		this.agent_container = rt.createAgentContainer(start_profil);
		
		System.out.println("Launch AgentContainer after " + start_profil);
		System.out.println("Containers created");
		
		System.out.println("Launching Vergleichsagent and AngebotAgent on main container...");
		
		try {
			ac = this.agent_container.acceptNewAgent("Vergleichsagent", vergleichsagent);
			ac = this.agent_container.acceptNewAgent("AngebotAgent", angebote);
			
			System.out.println("Ich starte jetzt....deine scheiss Agenten");
			
			agent_container.getAgent("Vergleichsagent").start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
	}
	

}
