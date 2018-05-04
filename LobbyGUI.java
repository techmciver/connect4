import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LobbyGUI {
	String playerList = "";
	
	public LobbyGUI(int isHostOfLobby) {
		/* initializes the frame object and the two panel objects */
		JFrame lobbyFrame = new JFrame("Lobby");
		JPanel playerListPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		/* initializes the label object */
		JLabel playerListLabel = new JLabel(playerList);

		/* initializes the "Start Game" button object */
		JButton startGameButton = new JButton("Start Game");
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MatchGUI();
				lobbyFrame.dispose();
			}
		});
		
		/* disables "Start Game" button if user is not the host of the lobby */
		if (isHostOfLobby == 0) {
			startGameButton.setEnabled(false);
		}
		
		/* initializes the "Quit Lobby" button object */
		JButton quitLobbyButton = new JButton("Quit Lobby");
		quitLobbyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new MainMenuGUI();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lobbyFrame.dispose();
			}
		});

		/* adds the playerListLabel object (and the spacing) to the playerListPanel object */
		playerListPanel.add(playerListLabel);
		playerListPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		/* sets the buttonPanel object's layout */
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		
		/* adds all the buttons (and the spacing inbetween each button) to the buttonPanel object*/
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
}
