package main.agents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.JSONException;
import org.json.JSONObject;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import user_interface.AgentCreatorUI;
import user_interface.ClientUI;

public class ClientAgent extends Agent {

	private ClientUI gui;
	private String myName;

	protected void setup() {
		myName = this.getLocalName();
		System.out.println("New client "+myName);

		gui = new ClientUI(this);
		gui.showGui();

	}


	protected void takeDown() {
		gui.dispose();
		System.out.println("Client left "+myName);
	}

	public void CloseAgent() {
		
		takeDown();
	}
}
