import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MatchGUI {
	/* initializes the current player (which determines the color of the chip dropped) */
	private int currentPlayer = 1;

	/* initializes the board object of this MatchGUI object */
	private Board gameBoard = new Board();
	private int boardSize = gameBoard.getBoardSize();
	
	/* initializes the spaces labels */
	private JLabel[][] spaces;

	public MatchGUI() {
		/* initializes the frame object and the two panel objects */
		final JFrame boardFrame = new JFrame("Connect Four - Player " + currentPlayer + "'s Turn");
		JPanel boardPanel = (JPanel) boardFrame.getContentPane();
		
		/* initializes the buttons that the users click on to drop a chip into the board */
		JButton[] buttons = new JButton[boardSize];
		for (int i = 0; i < boardSize; i++) {
			buttons[i] = new JButton();
			buttons[i].setActionCommand(Integer.toString(i));
			buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int columnSpaceLeft = gameBoard.findRemainingColumnSpace(Integer.parseInt(e.getActionCommand()));
					if (columnSpaceLeft == -1) {
						/* if the chosen column is full */
						updateBoard();
						JOptionPane.showMessageDialog(null, "Please choose another column", "Column Full",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (Controller.isWon(gameBoard, Integer.parseInt(e.getActionCommand()), columnSpaceLeft,
							currentPlayer)) {
						/* if the current player has won */
						updateBoard();
						int n = JOptionPane.showConfirmDialog(boardFrame, "Play again?",
								"Player " + currentPlayer + " Wins!", JOptionPane.YES_NO_OPTION);
						if (n == 0) {
							new MatchGUI();
						}
						boardFrame.dispose();
					} else if (Controller.isDraw(gameBoard)) {
						/* if the game has ended in a draw */
						updateBoard();
						int n = JOptionPane.showConfirmDialog(boardFrame, "Play again?", "Draw",
								JOptionPane.YES_NO_OPTION);
						if (n == 0) {
							new MatchGUI();
						}
						boardFrame.dispose();
					} else {
						/* else continue the game as normal */
						updateBoard();
						currentPlayer = Controller.changePlayer(currentPlayer);
						boardFrame.setTitle("Connect Four - Player " + currentPlayer + "'s Turn");
					}
				}
			});
			
			/* adds the button to the boardPanel object */
			boardPanel.add(buttons[i]);
		}
		
		/* initializes the label object for the spaces */
		spaces = new JLabel[boardSize][boardSize];
		for (int column = 0; column < boardSize; column++) {
			for (int row = 0; row < boardSize; row++) {
				spaces[row][column] = new JLabel();
				spaces[row][column].setHorizontalAlignment(SwingConstants.CENTER);
				spaces[row][column].setBorder(new LineBorder(Color.black));
				boardPanel.add(spaces[row][column]);
			}
		}
		
		/* sets the boardPanel object's layout */
		boardPanel.setLayout(new GridLayout(boardSize + 1, boardSize));
		
		/* sets the content pane, size, visibility, and behavior of the frame object */
		boardFrame.setContentPane(boardPanel);
		boardFrame.setSize(boardSize * 85, boardSize * 85);
		boardFrame.setVisible(true);
		boardFrame.setLocationRelativeTo(null);
		boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Updates the color of the spaces chosen by the player
	 */
	public void updateBoard() {
		for (int row = 0; row < boardSize; row++) {
			for (int column = 0; column < boardSize; column++) {
				if (gameBoard.getSpaceOwnership(row, column, 1)) {
					spaces[row][column].setOpaque(true);
					spaces[row][column].setBackground(Color.red);
				} else if (gameBoard.getSpaceOwnership(row, column, 2)) {
					spaces[row][column].setOpaque(true);
					spaces[row][column].setBackground(Color.yellow);
				}
			}
		}
	}

}
