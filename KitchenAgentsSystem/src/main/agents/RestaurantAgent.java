package main.agents;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.BankAccount;
import core.Reteta;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.introspection.AddedBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import user_interface.AgentCreatorUI;

public class RestaurantAgent extends Agent {

	private String myName;
	private AgentCreatorUI gui;
	private List<Reteta> recipes = new ArrayList<>();
	private static RestaurantAgent instance;
	private long totalTime;
	
	@Override
	protected void setup() {

		myName = this.getLocalName();
		System.out.println("Welcome to restaurant: " + myName + ".");
		SetupRecipes();
		instance = this;

		addBehaviour(new MenuRequest());
		addBehaviour(new FoodRequest());
		addBehaviour(new PayRequest());
		
		
		totalTime = 0;
		// Create and show the GUI
		gui = new AgentCreatorUI(this);
		gui.showGui();

	}

	@Override
	protected void takeDown() {
		System.out.println("Restaurant " + myName + " has been closed.");
	}

	private void SetupRecipes() {
		recipes = new ArrayList<>();
		recipes.add(new Reteta("Bonjour", "snitel de pui parizian cu cartofi taranesti, salata de varza + o chifla", 15,
				3));
		recipes.add(new Reteta("Meatball", "hiftelute cu sos tomat si piure de cartofi, castraveti murati + o chifla",
				15, 2));
		recipes.add(new Reteta("Red Chicken",
				"pulpe de pui cu sos de rosii, piure de cartofi, castraveti murati + o chifla", 15, 4));
		recipes.add(new Reteta("M1",
				"supa de pui cu taitei de casa + chiftelute cu sos tomat si piure de cartofi, salata de varza + o chifla",
				20, 4));
		recipes.add(new Reteta("M2", "ciorba de perisoare + piept de pui la gratar cu orez, salata de varza + o chifla",
				20, 5));
		recipes.add(new Reteta("M3",
				"ciorba radauteana + copanele de pui cu sos si piure de cartofi, salata de varza + o chifla", 20, 6));
		recipes.add(new Reteta("S1",
				"snitel de pui crocant cu sos de usturoi, cartofi copti, salata de varza + o chifla, DESERT: cozonac topit in lapte cu sos caramel",
				20, 5));
		recipes.add(new Reteta("S2",
				"pulpe de pui la gratar cu piure de cartofi, salata de muraturi + o chifla DESERT: clatite cu branza si smantana la cuptor",
				20, 4));
		recipes.add(new Reteta("S3",
				"lasagna (350g) (ragu, parmezan, bechamel, paste, salsa de rosii) DESERT: mousse au chocolat", 20, 4));
	}

	public String GetAgentName() {
		return myName;
	}

	public void AddNewClient(String clientName) {
		try {
			ContainerController cc = getContainerController();
			AgentController ac1 = cc.createNewAgent(clientName, "main.agents.ClientAgent", null);
			ac1.start();
		} catch (Exception e) {
		}
	}

	public static RestaurantAgent GetInstance() {
		return instance;
	}

	private Reteta GetRecipeByName(String name)
	{
		for(int i = 0;i<recipes.size();i++)
		{
			if(recipes.get(i).GetName().equals(name))
			{
				return recipes.get(i);
			}
		}
		return null;
	}
	
	// -------BEHAVIOURS-------
	private class MenuRequest extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchConversationId("Menu");
			mt.and(mt, MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				String messageContent = msg.getContent();
				ACLMessage reply = msg.createReply();
				JSONObject jo;
				JSONArray ja;
				try {
					ja = new JSONArray();
					String replyMessage = "";
					for (int i = 0; i < recipes.size(); i++) {
						jo = new JSONObject();
						jo.put("name", recipes.get(i).GetName());
						jo.put("desc", recipes.get(i).GetDescription());
						jo.put("price", recipes.get(i).GetCost());
						jo.put("time", recipes.get(i).GetTime());
						ja.put(jo);
					}
					reply.setPerformative(ACLMessage.CONFIRM);
					reply.setContent(ja.toString());
					reply.setConversationId("Menu " + msg.getSender());
					myAgent.send(reply);

				} catch (JSONException e) {
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setConversationId("Menu");
					myAgent.send(reply);
					e.printStackTrace();
				}

				block();
			} else {
				block();
			}
		}
	}
	
	private class PayRequest extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchConversationId("Pay");
			MessageTemplate.and(mt, MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				String messageContent = msg.getContent();
				ACLMessage reply = msg.createReply();
				System.out.println("Payment recived");
				reply.setPerformative(ACLMessage.CONFIRM);
				reply.setConversationId("Pay " + msg.getSender());			
				myAgent.send(reply);
				
			} else {
				block();
			}
		}
	}
	
	private class FoodRequest extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt =  MessageTemplate.MatchConversationId("Food");
			MessageTemplate.and(mt,MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				String messageContent = msg.getContent();
				ACLMessage reply = msg.createReply();
				System.out.println("Food order recived restaurant -> " +messageContent);
				reply.setPerformative(ACLMessage.CONFIRM);
				reply.setConversationId("Food " + msg.getSender());
				long value = GetRecipeByName(messageContent).GetTime() * 1000;
				totalTime +=value;
				addBehaviour( new WakerBehaviour( myAgent, totalTime)
			      {
			         protected void handleElapsedTimeout() {        
			            totalTime -=value;
			            
			            ACLMessage reply2 = msg.createReply();
						System.out.println("Food order completed restaurant");
						reply2.setPerformative(ACLMessage.CONFIRM);
						reply2.setConversationId("Done " + msg.getSender());
						myAgent.send(reply2);
			         }
			      });
				myAgent.send(reply);

				block();
			} else {
				block();
			}
		}
	}
	
}
