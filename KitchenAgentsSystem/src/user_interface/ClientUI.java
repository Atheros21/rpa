package user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.agents.ClientAgent;
import main.agents.RestaurantAgent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class ClientUI extends JFrame {

	private ClientAgent myAgent;

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
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(new Color(255, 241, 208));
		menuPanel.setBounds(0, 0, 784, 561);
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
		
		JList list = new JList();
		list.setBounds(237, 120, 234, 186);
		content_menu.add(list);
		
		JPanel welcomePanel = new JPanel();
		welcomePanel.setBounds(0, 0, 784, 561);
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

	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) screenSize.getWidth() / 2;
		int centerY = (int) screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}
}
