package main.agents;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import user_interface.AgentCreatorUI;

public class LuncherAgent extends Agent{
	
	private static LuncherAgent instance;
	
	public static LuncherAgent GetInstance()
	{
		return instance;
	}
	
	@Override
	protected void setup() {		
		instance=this;
		try {
			ContainerController cc = getContainerController();
			AgentController ac1 = cc.createNewAgent("Bucovina", "main.agents.RestaurantAgent", null);
			ac1.start();
		} catch (Exception e) {
		}

	}

	public void AddNewClient(String clientName)  {
		try {
			ContainerController cc = getContainerController();
			AgentController ac1 = cc.createNewAgent(clientName, "main.agents.ClientAgent", null);
			ac1.start();
		} catch (Exception e) {
		}
	}
}
