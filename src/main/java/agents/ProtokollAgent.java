package agents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import managers.DatumsManager;

public class ProtokollAgent extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8184434646383054043L;
	
	private File protokoll;
	private String ordner;
	private PrintWriter w;
	
	public ProtokollAgent() {
		String project_path = System.getProperty("user.dir");
		this.protokoll = new File(project_path + "/Agent_Protokolle/Protokoll" + LocalDateTime.now());
	
		try {
			this.w = new PrintWriter(getFile()); 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setName("ProtokollAgent");		
		sd.setType("Protokoll Agent");
		desc.addServices(sd);
		
		try {
			DFService.register(this, desc);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
		
		addBehaviour(new ProtokollAgentBehaviour());
	}
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}
	
	private class ProtokollAgentBehaviour extends CyclicBehaviour {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 7255643436722375201L;

		@Override
		public void action() {
			jade.lang.acl.ACLMessage empfangen = receive();
			
			ArrayList<String> strings;
			String status;
			
			if(empfangen != null) {
				Object[] objects;
				try {
					objects = (Object[]) empfangen.getContentObject();
					strings = (ArrayList<String>) objects[0];
					status = (String) objects[1];
					
					//System.out.println(status);
					
					writeProtokollToFile(strings, status);
					
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				block();
			}
		}
	}
	
	private File getFile() {
		return protokoll;
	}
	
	private PrintWriter getPrintWriter() {
		return w;
	}
	
	private String getOrdner() {
		return ordner;
	}
	
	private void writeProtokollToFile(ArrayList<String> str, String status) {
		
		switch(status) {
			case "0":

				getPrintWriter().println(("===================="
						+ "PRINT NEXT STEPS TO PROTOCOLL==============="));
				for(int i = 0; i < str.size(); i++) {
					getPrintWriter().println("");
					getPrintWriter().println(str.get(i) + "\n");
				}
				
				getPrintWriter().println("================NEXT STEPS FOLLOW================" + "\n" + "\n");
				
				break;
			case "1":
				
				getPrintWriter().println("============= END OF PROTOCOLL=============");
				getPrintWriter().close();
				
				break;
			default:
					break;
		}
	}
	
	
}
