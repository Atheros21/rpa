package main.agents;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.BankAccount;
import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class BankAgent extends Agent {
	private static final String filePathEnd = "/data/bankdata.json";
	private static final String accountCreatedMessage = "Your account has been created";
	private static final String accountFailedToCreate = "We were unable to create your account, the id already exists";
	private static final String invalidInformation = "The information you inserted is in invalid format";

	private static BankAgent instance;
	private String myName;
	private List<BankAccount> accounts = new ArrayList<BankAccount>();

	@Override
	protected void setup() {
		System.out.println("Bank online");
		instance = this;
		LunchTestAgents();
		SetupIoFile();

		/*
		 * try { WriteDataToFile(); } catch (JSONException e) { e.printStackTrace(); }
		 */

		myName = this.getLocalName();
		System.out.println("Hey from <" + myName + ">.");
		addBehaviour(new AccountCreateRequest());
		addBehaviour(new AccountLoginRequest());
		addBehaviour(new FoundsInspectRequest());
		addBehaviour(new DepositFoundsRequest());
		addBehaviour(new ExtractFoundsRequest());
		
	}

	@Override
	protected void takeDown() {
		System.out.println("Goodbye");
	}

	private void SetupIoFile() {
		if (!CheckForDataFile()) {
			try {
				CreateEmptyDataFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			ReadDataFromFile();
		}
	}

	private boolean CheckForDataFile() {
		File f = new File(System.getProperty("user.dir") + filePathEnd);
		return (f.exists() && !f.isDirectory());
	}

	private void CreateEmptyDataFile() throws IOException {
		File f = new File(System.getProperty("user.dir") + filePathEnd);
		f.createNewFile();
		accounts.add(new BankAccount(0, 0, 0));
		try {
			WriteDataToFile();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void WriteDataToFile() throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < accounts.size(); i++) {
			JSONObject jo = new JSONObject();
			jo.put("id", accounts.get(i).GetId());
			jo.put("pin", accounts.get(i).GetPin());
			jo.put("value", accounts.get(i).GetValue());
			jsonArray.put(jo);
		}

		String jsonData = jsonArray.toString();
		try {
			Files.writeString(Path.of(System.getProperty("user.dir") + filePathEnd), jsonData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ReadDataFromFile() {
		try {
			String data = Files.readString(Path.of(System.getProperty("user.dir") + filePathEnd));
			JSONArray jsonArray = new JSONArray(data);
			accounts = new ArrayList<BankAccount>();

			for (int i = 0; i < jsonArray.length(); i++) {
				BankAccount ba = new BankAccount(jsonArray.getJSONObject(i).getInt("id"),
						jsonArray.getJSONObject(i).getInt("pin"), jsonArray.getJSONObject(i).getInt("value"));
				accounts.add(ba);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void LunchTestAgents() {
		try {
			ContainerController cc = getContainerController();
			AgentController ac1 = cc.createNewAgent("Client1", "main.agents.ClientAgent", null);
			AgentController ac2 = cc.createNewAgent("Client2", "main.agents.ClientAgent", null);
			AgentController ac3 = cc.createNewAgent("Client3", "main.agents.ClientAgent", null);
			ac1.start();
			// ac2.start();
			// ac3.start();
		} catch (Exception e) {
		}

	}

	public static BankAgent GetInstance() {
		return instance;
	}

	private class AccountCreateRequest extends CyclicBehaviour {
		public void action() {
			ReadDataFromFile();
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String messageContent = msg.getContent();
				ACLMessage reply = msg.createReply();
				JSONObject jo;
				try {
					jo = new JSONObject(messageContent);
					boolean result = true;

					for (int i = 0; i < accounts.size(); i++) {
						if (accounts.get(i).GetId() == jo.getInt("id")) {
							result = false;
						}
					}

					if (result) {
						accounts.add(new BankAccount(jo.getInt("id"), jo.getInt("pin"), jo.getInt("value")));
						WriteDataToFile();
						reply.setPerformative(ACLMessage.CONFIRM);
						reply.setContent(accountCreatedMessage);
						reply.setConversationId("create-account");
						myAgent.send(reply);
					} else {
						reply.setPerformative(ACLMessage.REFUSE);
						reply.setContent(accountFailedToCreate);
						reply.setConversationId("create-account");
						myAgent.send(reply);
					}

				} catch (JSONException e) {
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent(invalidInformation);
					reply.setConversationId("create-account");
					myAgent.send(reply);
					e.printStackTrace();
				}

				block();
			} else {
				block();
			}
		}
	}

	private class AccountLoginRequest extends CyclicBehaviour {
		public void action() {
			ReadDataFromFile();
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				String messageContent = msg.getContent();
				ACLMessage reply = msg.createReply();
				JSONObject jo;

				try {
					jo = new JSONObject(messageContent);
					boolean result = true;

					for (int i = 0; i < accounts.size(); i++) {
						if (accounts.get(i).GetId() == jo.getInt("id")
								&& accounts.get(i).GetPin() == jo.getInt("pin")) {
							reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
							myAgent.send(reply);
							block();
							break;
						}
					}
					System.out.println(jo.getInt("id") + " " + jo.getInt("pin"));
					reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
					myAgent.send(reply);
					block();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					block();
				}
			} else {
				block();
			}
		}
	}

	private class FoundsInspectRequest extends CyclicBehaviour {
		public void action() {
			ReadDataFromFile();
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			mt = mt.and(mt, MessageTemplate.MatchConversationId("inspect-founds"));
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				String messageContent = msg.getContent();
				ACLMessage reply = msg.createReply();
				JSONObject jo;

				try {
					jo = new JSONObject(messageContent);
					for (int i = 0; i < accounts.size(); i++) {
						if (accounts.get(i).GetId() == jo.getInt("id")
								&& accounts.get(i).GetPin() == jo.getInt("pin")) {
							reply.setPerformative(ACLMessage.INFORM);
							reply.setContent(" " + accounts.get(i).GetValue());
							myAgent.send(reply);
							block();
						}
					}
					block();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					block();
				}
			} else {
				block();
			}
		}
	}

	private class DepositFoundsRequest extends CyclicBehaviour {
		public void action() {
			ReadDataFromFile();
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			mt = mt.and(mt, MessageTemplate.MatchConversationId("Deposit"));
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				String messageContent = msg.getContent();
				ACLMessage reply = msg.createReply();
				JSONObject jo;

				try {
					jo = new JSONObject(messageContent);
					for (int i = 0; i < accounts.size(); i++) {
						if (accounts.get(i).GetId() == jo.getInt("id")
								&& accounts.get(i).GetPin() == jo.getInt("pin")) {

							accounts.get(i).SetValue(accounts.get(i).GetValue() + jo.getInt("value"));
							WriteDataToFile();
							block();
							break;
						}
					}
					block();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					block();
				}
			} else {
				block();
			}
		}
	}

	private class ExtractFoundsRequest extends CyclicBehaviour {
		public void action() {
			ReadDataFromFile();
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			mt = mt.and(mt, MessageTemplate.MatchConversationId("Extract"));
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				String messageContent = msg.getContent();
				ACLMessage reply = msg.createReply();
				JSONObject jo;
				try {
					jo = new JSONObject(messageContent);
					for (int i = 0; i < accounts.size(); i++) {
						BankAccount currentAccount = accounts.get(i);
						if (currentAccount.GetId() == jo.getInt("id") && currentAccount.GetPin() == jo.getInt("pin")) {
							if (currentAccount.GetValue() >= jo.getInt("value")) {
								reply.setPerformative(ACLMessage.INFORM);
								reply.setContent(" " + jo.getInt("value"));
								currentAccount.SetValue(currentAccount.GetValue()-jo.getInt("value"));
								WriteDataToFile();
								myAgent.send(reply);
								block();
							} else {
								reply.setPerformative(ACLMessage.FAILURE);
								myAgent.send(reply);
								block();
							}

						}
					}
					block();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					block();
				}
			} else {
				block();
			}
		}
	}
}
