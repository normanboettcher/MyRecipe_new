package jade.connector;

import java.util.HashMap;

import agents.AngeboteAgent;
import agents.EinkaufslistenVergleichsAgent;
import agents.ProtokollAgent;
import agents.SenderAgent;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

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
		
		SenderAgent sender = (SenderAgent) agenten.get("SendeAgent");
		ProtokollAgent protokoll_agent = new ProtokollAgent();
				
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
			ac = this.agent_container.acceptNewAgent("SendeAgent", sender);
			ac = this.agent_container.acceptNewAgent("ProtokollAgent", protokoll_agent);
			
			agent_container.getAgent("ProtokollAgent").start();
			agent_container.getAgent("Vergleichsagent").start();
			agent_container.getAgent("AngebotAgent").start();
			agent_container.getAgent("SendeAgent").start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
