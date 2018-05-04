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
		JFrame mainMenuFrame = new JFrame("Main Menu");
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		/* initializes the label object */
		JLabel titleLabel = new JLabel("Welcome to Connect Four");
		
		/* initializes the "Host Game" button object and its button functionality */
		JButton hostGameButton = new JButton("Host Game");
		hostGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LobbyGUI(1);
				mainMenuFrame.dispose();
				// create a server socket which listens for incoming connections
				System.out.println("Server launching ..");
				ServerSocket serverSocket;
				try {
					serverSocket = new ServerSocket(9090);
					// need a button to stop creating connection
					while (true) {
						System.out.println("accepting connection ..");
						Socket s = serverSocket.accept();
					}
				} catch (IOException e1) {
					System.out.println("server unable to launch");
				}		
			}
		});
		
		/* initializes the "Join Game" button object and its button functionality */
		JButton joinGameButton = new JButton("Join Game");
		joinGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String IPAddressToConnectTo = JOptionPane.showInputDialog(mainMenuFrame, "Please enter IP address of host");
				try {
					// initialize the connection with the server
					Socket s = new Socket(IPAddressToConnectTo, 9090);
					System.out.println("Connection established successfully.");
					new LobbyGUI(0);
					mainMenuFrame.dispose();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(mainMenuFrame, "Please Enter a valid IP address");
				} 
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
