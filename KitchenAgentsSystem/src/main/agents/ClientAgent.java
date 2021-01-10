package main.agents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.Reteta;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import user_interface.AgentCreatorUI;
import user_interface.ClientUI;

public class ClientAgent extends Agent {

	private ClientUI gui;
	private String myName;
	private String selectedFood;
	
	protected void setup() {
		myName = this.getLocalName();
		System.out.println("New client " + myName);

		addBehaviour(new MenuRequestResponse());
		addBehaviour(new FoodRequestResponse());
		addBehaviour(new FoodCompletedResponse());
		addBehaviour(new PaymentConfirmation());
		
		gui = new ClientUI(this);
		gui.showGui();

	}

	protected void takeDown() {
		gui.dispose();
		System.out.println("Client left " + myName);
	}

	public void CreateButtonBehaviour() {
		addBehaviour(new MenuRequestPerformer());
	}
	
	public void FoodRequstButton(String recipeName)
	{
		selectedFood = recipeName;
		addBehaviour(new FoodRequestPerformer());
	}
	public void PayButton()
	{
		addBehaviour(new PayPerformer());
	}
	public void CloseAgent() {

		takeDown();
	}

	
	
	//------BEHAVIOURS-----------
	private class MenuRequestPerformer extends Behaviour {
		@Override
		public void action() {

			ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);
			AID restaurant = RestaurantAgent.GetInstance().getAID();
			cfp.addReceiver(restaurant);
			cfp.setConversationId("Menu");
			System.out.println("Menu send");
			myAgent.send(cfp);
			block();
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return true;
		}
	}
	
	private class FoodRequestPerformer extends Behaviour {
		@Override
		public void action() {

			ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);
			AID restaurant = RestaurantAgent.GetInstance().getAID();
			cfp.addReceiver(restaurant);
			cfp.setContent(selectedFood);
			cfp.setConversationId("Food");
			System.out.println("Food order send");
			myAgent.send(cfp);
			block();
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return true;
		}
	}
	
	private class PayPerformer extends Behaviour {
		@Override
		public void action() {

			ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);
			AID restaurant = RestaurantAgent.GetInstance().getAID();
			cfp.addReceiver(restaurant);
			cfp.setConversationId("Pay");
			System.out.println("pay send");
			myAgent.send(cfp);
			block();
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return true;
		}
	}
	//--Responses--
	private class MenuRequestResponse extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchConversationId("Menu "+getAID());
			MessageTemplate.and(mt, MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				try {
					JSONArray ja = new JSONArray(msg.getContent());
					List<Reteta> recipes = new ArrayList<>();
					for(int i = 0; i<ja.length();i++)
					{
						JSONObject jo = ja.getJSONObject(i);
						recipes.add(new Reteta(jo.getString("name"),jo.getString("desc"),jo.getInt("price"),jo.getInt("time")));
					}
					gui.ShowMenu(recipes);
					System.out.println("Menu recived");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				block();
			} else {
				block();
			}
		}
	}
	
	private class FoodRequestResponse extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchConversationId("Food "+getAID());
			MessageTemplate.and(mt, MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
			ACLMessage msg = myAgent.receive(mt);
			System.out.println();
			if (msg != null) {
				System.out.println("Food order waiting");
				block();
			} else {
				block();
			}
		}
	}
	
	private class PaymentConfirmation extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchConversationId("Pay "+getAID());
			MessageTemplate.and(mt, MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
			ACLMessage msg = myAgent.receive(mt);
			System.out.println();
			if (msg != null) {
				CloseAgent();
				block();
			} else {
				block();
			}
		}
	}
	
	private class FoodCompletedResponse extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchConversationId("Done "+getAID());
			MessageTemplate.and(mt, MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
			ACLMessage msg = myAgent.receive(mt);
			System.out.println();
			if (msg != null) {
				System.out.println("Food order completed client");
				gui.ShowPayPanel();
				block();
			} else {
				block();
			}
		}
	}
}
