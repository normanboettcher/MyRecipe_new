package agents;

import java.util.ArrayList;
import java.util.HashMap;

import general.Einkaufsliste;
import general.supermarkets.Rezepte;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.UnreadableException;

/**
 * Klasse, um Sender Agenten zu realisieren. Der Sender Agent uebermittelt die
 * Ergebnisse der Agentenkommunikation an die jeweiligen Servlets.
 * 
 * @author norman
 *
 */
public class SenderAgent extends Agent {
	
	//Attribute
	private String name;
	private Object object_to_send;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6637908621330116852L;
	
	/**
	 * Konstruktor des SendeAgenten. der {@code name} wird fest auf 'SendeAgent'
	 * gesetzt.
	 */
	public SenderAgent() {
		this.name = "SendeAgent";
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
	 * Methode, um das Onjekt, welches gesendet werden soll, fuer den Agenten als
	 * Attribut festzulegen.
	 * 
	 * @param l das zu sendende Objekt.
	 */
	@SuppressWarnings("unchecked")
	private void setObjectToSend(Object l) {
		if(l instanceof HashMap) {
			this.object_to_send = (HashMap<Integer, Einkaufsliste>) l;
		} else if (l instanceof ArrayList) {
			this.object_to_send = (ArrayList<Rezepte>) l;
		}
	}

	/**
	 * Methode zur Rueckgabe des Objektes.
	 * 
	 * @return object_to_send
	 */
	public Object getObjectToSend() {
		return object_to_send;
	}
	/**
	 * Agent wird registriert.
	 */
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
	/**
	 * Agent wird deregistriert.
	 */
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}

	/**
	 * private Klasse, um Verhalten des SendeAgenten zu realisieren.
	 * 
	 * @author norman
	 *
	 */
	private class SendeAgentBehaviour extends Behaviour {
		private boolean finished = false;
		/**
		 * 
		 */
		private static final long serialVersionUID = -3512239954477308987L;
		
		@Override
		public boolean done() {
			return finished;
		}
		
		@Override
		public void action() {
			String str1 = "";
			
			jade.lang.acl.ACLMessage ms = receive();
			String conv_id = "";
			if(ms != null) {
				conv_id = ms.getConversationId();
			} 
			
			if(ms != null && conv_id.equals("ProzessBeendetVergleich")) {
				str1 += "Start action() from [ " + getName() + " ]"
						+ " in SendeAgentBehaviour \n";
				try {
					
					@SuppressWarnings("unchecked")
					HashMap<Integer, Einkaufsliste> objekt = (HashMap<Integer, Einkaufsliste>) ms.getContentObject();
					
					setObjectToSend(objekt);
				
					str1 += "Agent: [ " + getName() + " ] konnte Nachricht "
							+ " von [ " + ms.getSender() + " ] empfangen. \n "
									+ "Prozess fuer den Vergleich ist beendet.";
					
					send(UeberwachungsAgent.sendToProtokollAgent(str1, "1"));
					this.finished = true;
				
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
			} else if(ms != null && conv_id.equals("ProzessBeendetQuery")) {
				str1 = "Start action() from [ " + getName() + " ]"
						+ " in SendeAgentBehaviour \n";
				try {
					
					ArrayList<Rezepte> objekt = (ArrayList<Rezepte>) ms.getContentObject();
					
					setObjectToSend(objekt);
					ArrayList<Rezepte> r= (ArrayList<Rezepte>) getObjectToSend();
					
					str1 += "Agent: [ " + getName() + " ] konnte Nachricht "
							+ " von [ " + ms.getSender() + " ] empfangen und Objekt "
									+ "Output zum Senden vorbereiten. "
											+ "Der Queryprozess ist beendet. \n";
					
					send(UeberwachungsAgent.sendToProtokollAgent(str1, "0"));
					this.finished = true;
				
				} catch (UnreadableException e) {
					e.printStackTrace();
				} 
			} else {
				block();
			}
		}
	}
}
