package user_interface;

import jade.core.AID;
import main.agents.ClientAgent;
import main.agents.RestaurantAgent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class AgentCreatorUI extends JFrame {	
	private RestaurantAgent myAgent;
	private JTextField clientName_welcome;
	
	public AgentCreatorUI(RestaurantAgent a) {
		super(a.getLocalName());
		
		myAgent = a;
		
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
		
		setResizable(false);
		getContentPane().setPreferredSize(new Dimension(800, 600));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel error_welcome = new JLabel("Invalid name");
		JPanel welcomePanel = new JPanel();
		welcomePanel.setBackground(new Color(255, 241, 208));
		getContentPane().add(welcomePanel);
		welcomePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel header_wlecome = new JPanel();
		header_wlecome.setBackground(new Color(8, 103, 136));
		welcomePanel.add(header_wlecome, BorderLayout.NORTH);
		
		JLabel tile_welcome = new JLabel("Restaurant app manger ");
		tile_welcome.setForeground(new Color(255, 241, 208));
		tile_welcome.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		header_wlecome.add(tile_welcome);
		
		JPanel content_welcome = new JPanel();
		welcomePanel.add(content_welcome, BorderLayout.CENTER);
		content_welcome.setLayout(null);
		
		JPanel elements_welcome = new JPanel();
		elements_welcome.setBounds(194, 120, 400, 315);
		content_welcome.add(elements_welcome);
		elements_welcome.setLayout(null);
		
		JLabel clientNameLabel_welcome = new JLabel("Client Name:");
		clientNameLabel_welcome.setHorizontalAlignment(SwingConstants.CENTER);
		clientNameLabel_welcome.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		clientNameLabel_welcome.setBounds(66, 11, 267, 44);
		elements_welcome.add(clientNameLabel_welcome);
		
		clientName_welcome = new JTextField();
		clientName_welcome.setBounds(66, 54, 267, 36);
		clientName_welcome.setColumns(10);
		elements_welcome.add(clientName_welcome);
		
		JButton addClient_welcome = new JButton("Add client");
		addClient_welcome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clientName_welcome.getText().isEmpty())
				{
					error_welcome.setVisible(true);
				}
				else
				{
					a.AddNewClient(clientName_welcome.getText());
					clientName_welcome.setText("");
					error_welcome.setVisible(false);
				}
			}
		});
		addClient_welcome.setBackground(new Color(7, 160, 195));
		addClient_welcome.setBounds(127, 101, 135, 42);
		elements_welcome.add(addClient_welcome);
		
		
		error_welcome.setForeground(Color.RED);
		error_welcome.setHorizontalAlignment(SwingConstants.CENTER);
		error_welcome.setFont(new Font("Tahoma", Font.PLAIN, 24));
		error_welcome.setBounds(105, 154, 173, 36);
		error_welcome.setVisible(false);
		elements_welcome.add(error_welcome);
		
		JLabel restaurantName_welcome = new JLabel("<Name>");
		restaurantName_welcome.setHorizontalAlignment(SwingConstants.CENTER);
		restaurantName_welcome.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		restaurantName_welcome.setBounds(222, 11, 331, 52);
		content_welcome.add(restaurantName_welcome);
		restaurantName_welcome.setText("Restaurant "+a.GetAgentName());			
		}
	
	
	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}
}