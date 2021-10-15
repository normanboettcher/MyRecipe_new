package agents;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;

import general.Einkaufsliste;
import general.Supermarkt;
import general.supermarkets.Lidl;
import general.supermarkets.Penny;
import general.supermarkets.Rewe;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.UnreadableException;

public class UeberwachungsAgent extends Agent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8503306574485571056L;
	private String name;
	
	public UeberwachungsAgent() {
		this.name = "Ueberwachung";
	}
	
	public String getAgentName() {
		return name;
	}
	
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setName("AktualisiereAngeboteAgent");		
		sd.setType("Aktualisierungs Agent");
		desc.addServices(sd);
		
		try {
			DFService.register(this, desc);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
		
		addBehaviour(new UeberwachungsVerhalten());
	}
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException fe) {
			System.out.println(fe.getMessage());
		}
	}

	
	private class UeberwachungsVerhalten extends Behaviour {
		private boolean finished = false;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1717651164253631008L;
		
		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return finished;
		}

		@Override
		public void action() {
			
			String conv_id = "";
			
			System.out.println("In Action von Uberwachungsverhalten");
			
			jade.lang.acl.ACLMessage aufforderung = blockingReceive();
			
			
			if(aufforderung != null) {
				conv_id = aufforderung.getConversationId();
			}
			
			if(aufforderung != null && conv_id.equals("AufforderungAnUeberwachung")) {
				
				System.out.println("Aufforderung von Angebot Agent erhalten.");
				
				System.out.println("Aufforderung entgegen genommen");
				
				jade.lang.acl.ACLMessage msg = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
				msg.addReceiver(new AID("AktualisierungsAgent", AID.ISLOCALNAME));
				
				int counter = holeStatus();
				
				int status = 0;
				
				//Sollten die Angebote bereits dreimal abgerufen worden sein ohne 
				//aktualisiert zu werden, werden sie aktualisiert und der status dafuer auf 
				// 1 gesetzt und dem Aktualisierungsagenten geschickt
				if(counter == 3) {
					status = 1;
				}
				
				try {
					Supermarkt[] maerkte = {new Lidl(), new Penny(), new Rewe()}; 
					
					HashMap<Integer, Einkaufsliste> l = (HashMap<Integer, Einkaufsliste>) aufforderung.getContentObject();
					
					Object[] o = {new UeberwachungsAgent(),maerkte, status, l};
					msg.setContentObject(o);
					msg.setConversationId("UpdateAnfrage");
					send(msg);
					System.out.println("Anfrage an den Aktualisierungsagenten gesendet..");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (aufforderung != null && conv_id.equals("UpdateAntwort")) {
							
					boolean b = false;
					try {
						if(aufforderung != null) {
							Object[] obs =  (Object[]) aufforderung.getContentObject();
							AktualisiereAngeboteAgent a = (AktualisiereAngeboteAgent) obs[1];
							HashMap<Integer, Einkaufsliste> l = (HashMap<Integer, Einkaufsliste>) obs[2];
				
								b = (boolean) obs[0];

								System.out.println(b);
								if(b == true) {
									//Wenn aktualisiert wurde, dann faengt status wieder von 0 an zu zaehlen
									schreibeStatus(0);
									jade.lang.acl.ACLMessage to_angebot_agent = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
									to_angebot_agent.addReceiver(new AID("AngebotAgent", AID.ISLOCALNAME));
									Object[] objects_to_send = {b, l};
									to_angebot_agent.setContentObject(objects_to_send);
									to_angebot_agent.setConversationId("UpdateVonUeberwachung");
									send(to_angebot_agent);
									this.finished = true;
								} else {
									int counter = holeStatus() + 1;
									schreibeStatus(counter);
									jade.lang.acl.ACLMessage to_angebot_agent = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
									to_angebot_agent.addReceiver(new AID("AngebotAgent", AID.ISLOCALNAME));
									Object[] objects_to_send = {b, l};
									to_angebot_agent.setContentObject(objects_to_send);
									to_angebot_agent.setConversationId("UpdateVonUeberwachung");
									send(to_angebot_agent);
									this.finished = true;
								}
							}
						} catch (UnreadableException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} else {
				block();
			}
				
		}

	
	public static int holeStatus() {
		int status = 0;
		BufferedReader bfr = null;
		try {
			bfr = new BufferedReader(
					new FileReader(System.getProperty("user.dir") + "/UeberwachungAgent/anfrage.txt"));
			StringBuilder sb = new StringBuilder();
			String line = bfr.readLine();
			
			status = Integer.parseInt(line);
			
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bfr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public static void schreibeStatus(int s) {
		File file = new File(System.getProperty("user.dir")+  "/UeberwachungAgent/anfrage.txt");
		file.delete();
		File file_new = new File(System.getProperty("user.dir")+  "/UeberwachungAgent/anfrage.txt");
		
		String status = Integer.toString(s);
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(file_new);
			writer.println(status);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
}
}
	

				
		
		

	

