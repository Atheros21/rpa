package user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import core.Reteta;
import main.agents.ClientAgent;
import main.agents.RestaurantAgent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class ClientUI extends JFrame {

	private ClientAgent myAgent;
	JPanel payPanel = new JPanel();
	JPanel waitingPanel = new JPanel();
	JList list = new JList();
	List<Reteta> loadedRecipes;
	public ClientUI(ClientAgent a) {
		super(a.getLocalName());

		myAgent = a;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		});

		setResizable(false);
		getContentPane().setPreferredSize(new Dimension(800, 600));
		getContentPane().setLayout(null);
		
		
		waitingPanel.setVisible(false);
		
		
		payPanel.setVisible(false);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setVisible(false);
		menuPanel.setBackground(new Color(255, 241, 208));
		menuPanel.setBounds(0, 0, 800, 600);
		getContentPane().add(menuPanel);
		menuPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel header_Menu = new JPanel();
		header_Menu.setBackground(new Color(8, 103, 136));
		menuPanel.add(header_Menu, BorderLayout.NORTH);
		
		JLabel tile_Menu = new JLabel("Menu");
		tile_Menu.setForeground(new Color(255, 241, 208));
		tile_Menu.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		header_Menu.add(tile_Menu);
		
		JPanel content_menu = new JPanel();
		content_menu.setLayout(null);
		content_menu.setBackground(new Color(255, 241, 208));
		menuPanel.add(content_menu, BorderLayout.CENTER);
		
		JButton Comanda = new JButton("Order");
		Comanda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.FoodRequstButton(loadedRecipes.get(list.getSelectedIndex()).GetName());
				menuPanel.setVisible(false);
				waitingPanel.setVisible(true);
			}
		});
		Comanda.setBackground(new Color(7, 160, 195));
		Comanda.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		Comanda.setBounds(193, 430, 228, 67);
		content_menu.add(Comanda);
		
		JButton Comanda_1 = new JButton("Leave");
		Comanda_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.CloseAgent();
			}
		});
		Comanda_1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		Comanda_1.setBackground(new Color(221, 28, 26));
		Comanda_1.setBounds(454, 430, 228, 67);
		content_menu.add(Comanda_1);
		
		
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(21, 11, 758, 414);
		content_menu.add(list);
		payPanel.setBounds(0, 0, 800, 600);
		getContentPane().add(payPanel);
		payPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel header_Pay = new JPanel();
		header_Pay.setBackground(new Color(8, 103, 136));
		payPanel.add(header_Pay, BorderLayout.NORTH);
		
		JLabel tile_Pay = new JLabel("Payment");
		tile_Pay.setForeground(new Color(255, 241, 208));
		tile_Pay.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		header_Pay.add(tile_Pay);
		
		JPanel content_pay = new JPanel();
		content_pay.setLayout(null);
		content_pay.setBackground(new Color(255, 241, 208));
		payPanel.add(content_pay, BorderLayout.CENTER);
		
		JButton pay = new JButton("Pay");
		pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.PayButton();
			}
		});
		pay.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		pay.setBackground(new Color(7, 160, 195));
		pay.setBounds(284, 228, 228, 67);
		content_pay.add(pay);
		waitingPanel.setBackground(new Color(255, 241, 208));
		waitingPanel.setBounds(0, 0, 800, 600);
		getContentPane().add(waitingPanel);
		waitingPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel header_Waiting = new JPanel();
		header_Waiting.setBackground(new Color(8, 103, 136));
		waitingPanel.add(header_Waiting, BorderLayout.NORTH);
		
		JLabel tile_Waiting = new JLabel("Waiting");
		tile_Waiting.setForeground(new Color(255, 241, 208));
		tile_Waiting.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		header_Waiting.add(tile_Waiting);
		
		JPanel content_Waiting = new JPanel();
		content_Waiting.setLayout(null);
		content_Waiting.setBackground(new Color(255, 241, 208));
		waitingPanel.add(content_Waiting, BorderLayout.CENTER);
		
		JLabel lblYourFoodIs = new JLabel("Your food is cooking...");
		lblYourFoodIs.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourFoodIs.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblYourFoodIs.setBounds(240, 104, 317, 51);
		content_Waiting.add(lblYourFoodIs);
		
		JLabel lblThankYouFor = new JLabel("Thank you for patience");
		lblThankYouFor.setHorizontalAlignment(SwingConstants.CENTER);
		lblThankYouFor.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblThankYouFor.setBounds(241, 167, 317, 51);
		content_Waiting.add(lblThankYouFor);
		
		JPanel welcomePanel = new JPanel();
		welcomePanel.setBounds(0, 0, 800, 600);
		welcomePanel.setBackground(new Color(255, 241, 208));
		getContentPane().add(welcomePanel);
		welcomePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel header_wlecome = new JPanel();
		header_wlecome.setBackground(new Color(8, 103, 136));
		welcomePanel.add(header_wlecome, BorderLayout.NORTH);
		
		JLabel tile_welcome = new JLabel("Welcome, "+a.getLocalName());
		tile_welcome.setForeground(new Color(255, 241, 208));
		tile_welcome.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		header_wlecome.add(tile_welcome);
		
		JPanel content_welcome = new JPanel();
		content_welcome.setBackground(new Color(255, 241, 208));
		welcomePanel.add(content_welcome, BorderLayout.CENTER);
		content_welcome.setLayout(null);
		
		JButton showMenu_welcome = new JButton("Show menu");
		showMenu_welcome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.CreateButtonBehaviour();
				menuPanel.setVisible(true);
				welcomePanel.setVisible(false);
			}
		});
		showMenu_welcome.setBackground(new Color(7, 160, 195));
		showMenu_welcome.setBounds(176, 188, 189, 93);
		content_welcome.add(showMenu_welcome);
		
		JButton leave_welcome = new JButton("Leave");
		leave_welcome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.CloseAgent();
			}
		});
		leave_welcome.setBackground(new Color(221, 28, 26));
		leave_welcome.setBounds(400, 188, 189, 93);
		content_welcome.add(leave_welcome);
		

		}
	
	public void ShowMenu(List<Reteta> recipes)
	{
		DefaultListModel<Reteta> modelList = new DefaultListModel<>();
		loadedRecipes=recipes;
		for(int i = 0; i<recipes.size();i++)
		{
			modelList.add(i, recipes.get(i));
		}
		list.setModel(modelList);
		
	}
	
	public void ShowPayPanel()
	{
		payPanel.setVisible(true);
		waitingPanel.setVisible(false);
	}

	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) screenSize.getWidth() / 2;
		int centerY = (int) screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}
}
