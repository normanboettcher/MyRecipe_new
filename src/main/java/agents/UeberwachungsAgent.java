package agents;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import general.Einkaufsliste;
import general.Supermarkt;
import general.supermarkets.Lidl;
import general.supermarkets.Penny;
import general.supermarkets.Rewe;
import general.supermarkets.RezeptAnfrage;
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
	private RezeptAnfrage anfrage; //Der Ueberwachungsagent muss die RezeptAnfrage an 
								   //den RecipeAgent uebermitteln koennen.
	
	public UeberwachungsAgent() {
		this.name = "Ueberwachung";
	}
	
	public String getAgentName() {
		return name;
	}
	
	public void setRezeptAnfrage(RezeptAnfrage a) {
		this.anfrage = a;
	}
	
	protected RezeptAnfrage getRezeptAnfrage() {
		return anfrage;
	}
	
	protected void setup() {
		
		DFAgentDescription desc = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setName("UeberwachungAngeboteAgent");		
		sd.setType("Ueberwachungs Agent");
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
				
				System.out.println(counter);
				
				int status = 0;
				
				//Sollten die Angebote bereits dreimal abgerufen worden sein ohne 
				//aktualisiert zu werden, werden sie aktualisiert und der status dafuer auf 
				// 1 gesetzt und dem Aktualisierungsagenten geschickt
				if(counter >= 3) {
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
					
			} else if (aufforderung == null) {
				jade.lang.acl.ACLMessage msg_an_RecipeAgent = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
				msg_an_RecipeAgent.addReceiver(new AID("RezeptAgent", AID.ISLOCALNAME));
				msg_an_RecipeAgent.setConversationId("CBRImport");
				send(msg_an_RecipeAgent);
			} else if (aufforderung != null && conv_id.equals("CBRImportFertig")) {
				jade.lang.acl.ACLMessage msg_for_query_to_recipe_agent = aufforderung.createReply();
				
				msg_for_query_to_recipe_agent.setConversationId("CBRQuery");
				RezeptAnfrage anfrage = getRezeptAnfrage();
				try {
					msg_for_query_to_recipe_agent.setContentObject(anfrage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				send(msg_for_query_to_recipe_agent);
				this.finished = true;
			}
			else {
				block();
			}	
		}	
		public static int holeStatus() {
			Integer status = 0;
			DataInputStream dis = null;
			try {
				//bfr = new BufferedReader(
				//	new FileReader(System.getProperty("user.dir") + "/UeberwachungAgent/anfrage.txt"));
				//StringBuilder sb = new StringBuilder();
				//String line = bfr.readLine();

				//status = Integer.parseInt(line);
				dis = new DataInputStream(new FileInputStream(System.getProperty("user.dir") + "/UeberwachungAgent/anfrage.dat"));


				status = dis.readInt();

			} catch (EOFException eof) {
				//Ist ok
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				try {
					//	bfr.close();
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return status;
		}
	
		public static void schreibeStatus(int s) {
			Integer status = new Integer(s);
			File file = new File(System.getProperty("user.dir") + "/UeberwachungAgent/anfrage.dat");
			File file_new = null;

			if(file.exists()) {
				file.delete();
				file_new = new File(System.getProperty("user.dir") + "/UeberwachungAgent/anfrage.dat");
			} else {
				file_new = new File(System.getProperty("user.dir") + "/UeberwachungAgent/anfrage.dat");
			}

			DataOutputStream dous = null;
		
		try {
			dous =  new DataOutputStream(new FileOutputStream(file_new));
			//writer.println(status);
			//writer.close();
			System.out.println("Schreibe status: " + status);
			dous.writeInt(status);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//writer.close();
		}
	}
}
}
	

				
		
		

	

