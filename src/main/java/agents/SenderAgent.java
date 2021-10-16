package agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import general.Einkaufsliste;
import general.supermarkets.Rezepte;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class SenderAgent extends Agent {
	
	
	private String name;
	private Object object_to_send;
	private boolean ready_to_send;
	
	public SenderAgent() {
		this.name = "SendeAgent";
		this.ready_to_send = false;
	}
	
	public String getAgentName() {
		return name;
	}
	
	private void setObjectToSend(Object l) {
		if(l instanceof HashMap) {
			this.object_to_send = (HashMap<Integer, Einkaufsliste>) l;
		} else if (l instanceof ArrayList) {
			this.object_to_send = (ArrayList<Rezepte>) l;
		}
		
	}
	
	public Object getObjectToSend() {
		return object_to_send;
	}
	
	public boolean readyToSend() {
		return ready_to_send;
	}
	
	
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setName("SendeAgent");		
		sd.setType("Sender Agent");
		desc.addServices(sd);
		
		try {
			DFService.register(this, desc);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
		
		addBehaviour(new SendeAgentBehaviour());
	}
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6637908621330116852L;
	
	
	private class SendeAgentBehaviour extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3512239954477308987L;

		@Override
		public void action() {
			String str1 = "";
			String str2 = "";
			
			jade.lang.acl.ACLMessage ms = blockingReceive();
			String conv_id = "";
			if(ms != null) {
				ms.getConversationId();
			} else {
				block();
			}
			
			if(ms != null && conv_id.equals("ProzessBeendetVergleich")) {
				str1 = "Start action() from [ " + getName() + " ]"
						+ " in SendeAgentBehaviour";
				try {
					
					HashMap<Integer, Einkaufsliste> objekt = (HashMap<Integer, Einkaufsliste>) ms.getContentObject();
					
					setObjectToSend(objekt);
					
					str2 = "Agent: [ " + getName() + " ] konnte Nachricht "
							+ " von [ " + ms.getSender() + " ] empfangen und Objekt "
									+ "[ " + objekt + " ] zum Senden vorbereiten";
					
					ArrayList<String> s = new ArrayList<String>();
					s.add(str1); s.add(str2);
					
					Object[] objects = {s, "1"};
					
					jade.lang.acl.ACLMessage send_to_protocoll = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
					send_to_protocoll.setContentObject(objects);
					send_to_protocoll.addReceiver(new AID("ProtokollAgent", AID.ISLOCALNAME));
					send(send_to_protocoll);
				
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(ms != null && conv_id.equals("ProzessBeendetQuery")) {
				str1 = "Start action() from [ " + getName() + " ]"
						+ " in SendeAgentBehaviour";
				try {
					
					ArrayList<Rezepte> objekt = (ArrayList<Rezepte>) ms.getContentObject();
					
					setObjectToSend(objekt);
					
					str2 = "Agent: [ " + getName() + " ] konnte Nachricht "
							+ " von [ " + ms.getSender() + " ] empfangen und Objekt "
									+ "[ " + objekt + " ] zum Senden vorbereiten. "
											+ "Der Queryprozess ist beendet.";
					
					ArrayList<String> s = new ArrayList<String>();
					s.add(str1); s.add(str2);
					
					Object[] objects = {s, "1"};
					
					jade.lang.acl.ACLMessage send_to_protocoll = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
					send_to_protocoll.setContentObject(objects);
					send_to_protocoll.addReceiver(new AID("ProtokollAgent", AID.ISLOCALNAME));
					send(send_to_protocoll);
				
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				block();
			}
		}
	}
}
