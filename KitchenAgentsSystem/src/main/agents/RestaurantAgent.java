package main.agents;

import java.util.ArrayList;
import java.util.List;

import core.Reteta;
import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import user_interface.AgentCreatorUI;


public class RestaurantAgent extends Agent{

	private String myName;
	private AgentCreatorUI gui;
	private List<Reteta> recipes = new ArrayList<>();
	
	@Override
	protected void setup() {

		myName = this.getLocalName();
		System.out.println("Welcome to restaurant: " + myName +".");

		// Create and show the GUI
		gui = new AgentCreatorUI(this);
		gui.showGui();

	}

	@Override
	protected void takeDown() {
		System.out.println("Restaurant " + myName + " has been closed.");
	}
	
	private void SetupRecipes()
	{
		recipes = new ArrayList<>();
		recipes.add(new Reteta("Bonjour", "snitel de pui parizian cu cartofi taranesti, salata de varza + o chifla", 15));
		recipes.add(new Reteta("Meatball", "hiftelute cu sos tomat si piure de cartofi, castraveti murati + o chifla", 15));
		recipes.add(new Reteta("Red Chicken", "pulpe de pui cu sos de rosii, piure de cartofi, castraveti murati + o chifla", 15));
		recipes.add(new Reteta("M1", "supa de pui cu taitei de casa + chiftelute cu sos tomat si piure de cartofi, salata de varza + o chifla", 20));
		recipes.add(new Reteta("M2", "ciorba de perisoare + piept de pui la gratar cu orez, salata de varza + o chifla", 20));
		recipes.add(new Reteta("M3", "ciorba radauteana + copanele de pui cu sos si piure de cartofi, salata de varza + o chifla", 20));
		recipes.add(new Reteta("S1", "snitel de pui crocant cu sos de usturoi, cartofi copti, salata de varza + o chifla, DESERT: cozonac topit in lapte cu sos caramel", 20));
		recipes.add(new Reteta("S2", "pulpe de pui la gratar cu piure de cartofi, salata de muraturi + o chifla DESERT: clatite cu branza si smantana la cuptor", 20));
		recipes.add(new Reteta("S3", "asagna (350g) (ragu, parmezan, bechamel, paste, salsa de rosii) DESERT: mousse au chocolat", 20));
	}
	
	public String GetAgentName()
	{
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
}
