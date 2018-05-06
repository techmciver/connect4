import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LobbyGUI implements Runnable {
	boolean isHostOfLobby;
	String IPAddressToConnectTo;
	String player1;
	String player2;

	JFrame lobbyFrame;
	JLabel playerListLabel;

	//HashMap<String, String> playerList = new HashMap<String, String>();
	Queue<String> playerList = new PriorityQueue<String>();
	ServerSocket serverSocket = null;

	public LobbyGUI(boolean isHostOfLobby, String IPAddressToConnectTo) {
		this.isHostOfLobby = isHostOfLobby;
		this.IPAddressToConnectTo = IPAddressToConnectTo;
		createGUI(isHostOfLobby);
	}

	private void createGUI(final boolean isHostOfLobby) {
		/* initializes the frame object and the two panel objects */
		lobbyFrame = new JFrame("Lobby");
		JPanel playerListPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		/* initializes the label object */
		playerListLabel = new JLabel("Players in game: ");

		/* initializes the "Start Game" button object */
		JButton startGameButton = new JButton("Start Game");
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				// Make the "Start Game" button start a match with one of the players in the
				// lobby; maybe make it so whoever joined the lobby first will be first in line
				// to play

				if(playerList.size() < 2){
					System.out.println("Waiting for more players to join the lobby");
				}
				else{
					
					// if playerList.peek() == null, ask if current players want to play again because there are no waiting players to play 
					
						//new lobby case
						if(player1 != null && player2 != null){
							playerList.add(player1);
							playerList.add(player2);
						}
						
						if (playerList.peek() != null){
						player1 = playerList.poll();}else{System.out.println("Error; not enough players to start game.");}
						
						if (playerList.peek() != null){
						player2 = playerList.poll();}else{System.out.println("Error; not enough players to start game.");}
						
				
					
					//new MatchGUI should contain players 1 and 2  MatchGUI(Player1, Player2) 
					// to do so much control a lock system for critical sections 
					new MatchGUI(player1, player2, playerList);
					
				}

				// TODO (Optional) Make it so that the other players can spectate the game

			}
		});

		/* disables "Start Game" button if user is not the host of the lobby */
		if (!isHostOfLobby) {
			startGameButton.setEnabled(false);
		}

		/* initializes the "Quit Lobby" button object */
		JButton quitLobbyButton = new JButton("Quit Lobby");
		quitLobbyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* closes server socket if host of lobby */
				try {
					if (isHostOfLobby) {
						serverSocket.close();
						// TODO
						// Make it so that when the host quits the lobby, the other players receive a
						// pop-up window which tells them the host has left, which should bring them
						// back to the main menu
						
					}
				} catch (IOException e1) {
					System.out.println("Server: Unable to close server socket");
					System.exit(1);
				}

				try {
					//pop up window to notify that players in queue that the host quit the lobby
					JOptionPane.showMessageDialog(lobbyFrame, "Host has ended server", "Error",
                            JOptionPane.ERROR_MESSAGE);
					
					
					String playerName = "" + InetAddress.getLocalHost();
					playerList.remove(playerName);
					
					new MainMenuGUI();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				lobbyFrame.dispose();
				
			}
		});

		/*
		 * adds the playerListLabel object (and the spacing) to the playerListPanel
		 * object
		 */
		playerListPanel.add(playerListLabel);
		playerListPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		/* sets the buttonPanel object's layout */
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

		/*
		 * adds all the buttons (and the spacing inbetween each button) to the
		 * buttonPanel object
		 */
		buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		buttonPanel.add(startGameButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(70, 0)));
		buttonPanel.add(quitLobbyButton);

		/* adds the playerListPanel and buttonPanel objects to the frame object */
		lobbyFrame.add(playerListPanel, BorderLayout.NORTH);
		lobbyFrame.add(buttonPanel, BorderLayout.CENTER);

		/* sets the size, visibility, and behavior of the frame object */
		lobbyFrame.setSize(400, 200);
		lobbyFrame.setVisible(true);
		lobbyFrame.setLocationRelativeTo(null);
		lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void run() {
		/* if this is a thread of a host */
		if (isHostOfLobby) {
			try {
				/* initializes the socket objects */
				serverSocket = new ServerSocket(9090);
				System.out.println("Server: Listening for connections ...");

				/* loops the serverSocket to listen for incoming connections */
				while (true) {
					Socket server = serverSocket.accept();

					// TODO Get the IP address from the user that just connected and set it to
					// IPAddressThatJustEntered

					// TODO Make it so that when the user leaves the lobby, their name is removed
					// from the playerList HashMap

					String IPAddressThatJustEntered = "";
					String playerName = IPAddressThatJustEntered;
					

					/* adds new player to playerList if they are not yet in the playerList */
					if (!playerList.contains(IPAddressThatJustEntered)) {
						//playerList.puts(IPAddressThatJustEntered, playerName);
						playerList.add(IPAddressThatJustEntered);
						System.out.println("Server: Accepted connection from " + IPAddressThatJustEntered);
					}
					/* declines the connection if they are already in the playerList */
					else {
						System.out.println("Server: Rejected duplicate connection from " + IPAddressThatJustEntered);
					}
				}
			} catch (IOException e1) {
				System.out.println("Server: Server closed.");
			}
		}
		/* if this is a thread of a client */
		else if (!isHostOfLobby) {
			Socket socket = null;
			try {
				/* initialize the connection with the server */
				socket = new Socket(IPAddressToConnectTo, 9090);
				System.out.println("Client: Connection established successfully");
				
				// TODO Make sure the client's socket connection to the server is working
				// properly; I'm not sure if it might be working incorrectly, but just check it
				// and make sure it's behaving as expected.
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(lobbyFrame, "Please Enter a valid IP address");
				try {
					new MainMenuGUI();
					lobbyFrame.dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} finally {
				try {
					if (socket != null) {
						socket.close();
						System.out.println("Client: Socket closed..");
					}
				} catch (IOException e1) {
					System.out.println("Client: Unable to close socket");
					System.exit(1);
				}
			}
		}
	}
}
