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
import java.util.ArrayList;
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
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class UeberwachungsAgent extends Agent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8503306574485571056L;
	private String name;
	private RezeptAnfrage anfrage; //Der Ueberwachungsagent muss die RezeptAnfrage an 
									//den RecipeAgent uebermitteln koennen.
	private String status;
	
	public UeberwachungsAgent() {
		this.name = "Ueberwachung";
	}
	
	public String getAgentName() {
		return name;
	}
	
	public void setStatus(String s) {
		this.status = s;
	}
	
	private String getStatus() {
		return status;
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
		
		if(getStatus().equals("start")) {
			addBehaviour(new Startverhalten());
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

	private class Startverhalten extends Behaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6118121119868365156L;
		private boolean finished = false;
		@Override
		public void action() {
			String str =  "";
			
			if(getStatus().equals("start")) {
				str = "Agent: [ " + getName() + " ] befindet sich im Start und  bereitet ";
				jade.lang.acl.ACLMessage msg_an_RecipeAgent = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
				msg_an_RecipeAgent.addReceiver(new AID("RezeptAgent", AID.ISLOCALNAME));
				msg_an_RecipeAgent.setConversationId("CBRImport");
				send(msg_an_RecipeAgent);
				
				str += "Nachricht : [ " + msg_an_RecipeAgent + " ] zum absenden vor."
						+ "Mit KonversationsID: [ " + msg_an_RecipeAgent.getConversationId() + " ] \n";
				
				str += "Agent : [ " + getName() + " ] uebersendet Fortschritt an ProtokollAgent \n"; 
				
				
				send(sendToProtokollAgent(str, "0"));
			}
			
			jade.lang.acl.ACLMessage empfang = blockingReceive();
			String conv_id = "";
			
			if(empfang != null) {
				conv_id = empfang.getConversationId();
			}
			
			if (empfang != null && conv_id.equals("CBRImportFertig")) {
				String str2 = "";
				jade.lang.acl.ACLMessage msg_for_query_to_recipe_agent = empfang.createReply();

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
				
				str2 = "Agent : [ " + getName() + " ] konnte Antwort von [ " + empfang.getSender()
					+ " ] empfangen mit der KonversationsID [ " + empfang.getConversationId() + " ]"
							+ " Die Antwort von [ " + getName() + " ] "
									+ "konnte mit [ " + msg_for_query_to_recipe_agent + " ]"
											+ " erfolgreich erstellt und abgeschickt werden. \n";
				send(sendToProtokollAgent(str2, "0"));
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return finished;
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
			String str1 = "";
				jade.lang.acl.ACLMessage aufforderung = blockingReceive();

				if(aufforderung != null) {
					conv_id = aufforderung.getConversationId();
				} else {
					block();
				}

				if(aufforderung != null && conv_id.equals("AufforderungAnUeberwachung")) {
					
					jade.lang.acl.ACLMessage msg = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
					msg.addReceiver(new AID("AktualisierungsAgent", AID.ISLOCALNAME));
					
					str1 = "Agent: [ " + getName() + " ] konnte Nachricht von "
							+ "[ " + aufforderung.getSender() + " ] entegegen nehmen. \n"
									+ "KonversationsID : [ " +aufforderung.getConversationId() + " ] \n";
					
					
					int counter = holeStatus();
					
					str1 += "Der Aktuelle Status zur Aktualisierung betraegt : [ " + counter + " ] \n";
					
					int status = 0;

					//Sollten die Angebote bereits dreimal abgerufen worden sein ohne 
					//aktualisiert zu werden, werden sie aktualisiert und der status dafuer auf 
					// 1 gesetzt und dem Aktualisierungsagenten geschickt
					if(counter >= 3) {
						str1 += "Der Status wurde auf 1 geaendert. Es stehen neue Angebote zur Verfuegung. \n";
						status = 1;
					} else {
						str1 += "Der Status bleibt 0. Es gibt noch keine neuen Angebote \n";
					}

					try {
						Supermarkt[] maerkte = {new Lidl(), new Penny(), new Rewe()}; 

						HashMap<Integer, Einkaufsliste> l = (HashMap<Integer, Einkaufsliste>) aufforderung.getContentObject();

						Object[] o = {new UeberwachungsAgent(),maerkte, status, l};
						msg.setContentObject(o);
						msg.setConversationId("UpdateAnfrage");
						send(msg);
						
						str1 += "Agent [ " + getName() + " ] versendet Nachricht [ " + msg + " ]"
								+ " mit KonversationID : [ " + msg.getConversationId() + " ] \n";
						
						send(sendToProtokollAgent(str1, "0"));
					
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
								String str = "Agent :  + [ " + getName() + " ] hat den Status erneut "
										+ "auf 0 gesetzt und die aktualsisierten Einkaufslisten inkl. neuer Angebote mit "
										+ " [ " + to_angebot_agent + " ] an AngeboteAgent geschickt. \n";
								send(to_angebot_agent);
								send(sendToProtokollAgent(str, "0"));
								this.finished = true;
							} else {
								int counter = holeStatus() + 1;
								schreibeStatus(counter);
								jade.lang.acl.ACLMessage to_angebot_agent = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
								to_angebot_agent.addReceiver(new AID("AngebotAgent", AID.ISLOCALNAME));
								Object[] objects_to_send = {b, l};
								to_angebot_agent.setContentObject(objects_to_send);
								to_angebot_agent.setConversationId("UpdateVonUeberwachung");
								String str = "Agent :  + [ " + getName() + " ] hat den Status um 1 auf "
										+ "[ " + counter + " ] erhöht und die aktualsisierten Einkaufslisten inkl alter Angebote mit"
										+ " [ " + to_angebot_agent + " ] an AngeboteAgent geschickt. \n";
								send(to_angebot_agent);
								send(sendToProtokollAgent(str, "0"));
								this.finished = true;
							}
						}
					} catch (UnreadableException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 

				else {
					block();
				}
			}
		}
	
	public static jade.lang.acl.ACLMessage sendToProtokollAgent(String str, String status) {
		jade.lang.acl.ACLMessage msg = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
		try {
			Object[] objects = {str, status};
			msg.setContentObject(objects);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg.addReceiver(new AID("ProtokollAgent", AID.ISLOCALNAME));
		return msg;
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

	

				
		
		

	

