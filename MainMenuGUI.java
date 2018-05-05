import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class MainMenuGUI {
	public MainMenuGUI() throws IOException{
		/* initializes the frame object and the two panel objects */
		final JFrame mainMenuFrame = new JFrame("Main Menu");
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		/* initializes the label object */
		JLabel titleLabel = new JLabel("Welcome to Connect Four");
		
		/* initializes the "Host Game" button object and its button functionality */
		JButton hostGameButton = new JButton("Host Game");
		hostGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LobbyGUI lobbyGUI = new LobbyGUI(true, null);
				Thread t = new Thread(lobbyGUI);
				t.start();
				mainMenuFrame.dispose();
			}
		});
		
		/* initializes the "Join Game" button object and its button functionality */
		JButton joinGameButton = new JButton("Join Game");
		joinGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String IPAddressToConnectTo = JOptionPane.showInputDialog(mainMenuFrame, "Please enter IP address of host");
				LobbyGUI lobbyGUI = new LobbyGUI(false, IPAddressToConnectTo);
				Thread t = new Thread(lobbyGUI);
				t.start();
				mainMenuFrame.dispose();
			}
		});
		
		/* initializes the "Exit Menu" button object and its button functionality */
		JButton exitMenuButton = new JButton("Exit Menu");
		exitMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainMenuFrame.dispose();
			}
		});

		/* adds the titleLabel object to the titlePanel object */
		titlePanel.add(titleLabel);
		
		/* sets the buttonPanel object's layout */
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		
		/* adds all the buttons (and the spacing inbetween each button) to the buttonPanel object*/
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonPanel.add(hostGameButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		buttonPanel.add(joinGameButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		buttonPanel.add(exitMenuButton);
		
		/* adds the titlePanel and buttonPanel objects to the frame object */
		mainMenuFrame.add(titlePanel, BorderLayout.PAGE_START);
		mainMenuFrame.add(buttonPanel, BorderLayout.CENTER);
		
		/* sets the size, visibility, and behavior of the frame object */
		mainMenuFrame.setSize(400, 200);
		mainMenuFrame.setVisible(true);
		mainMenuFrame.setLocationRelativeTo(null);
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
