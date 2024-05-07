/*CSCI 204, Fall 2023, @author Abigail Champeny-Johns */

package achi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FeltBoard extends JPanel implements ActionListener {
	private static final long serialVersionUID = 7148504528835036003L;
	private static int BOARD_SIZE;
	private static final int CELL_SIZE = 300;
	private AnimationPanel animationPanel;
	private static GameBoard gameBoard;
	private int currentPlayer = 1;
	private int cellsPlayed;
	private boolean dropPhase = true;
	private int frameNumber = 0;
	private Timer timer;
	BufferedImage myPicture1;
	BufferedImage myPicture2;
	private String playerOneName;
    private String playerTwoName;

	/*
	 * constructor and also introduces the Extra Credit images, sets the board size
	 * and MouseListener
	 */
	public FeltBoard() {
		cellsPlayed = 0;
		try {
			myPicture1 = ImageIO.read(new File("heart photo.png"));
		} catch (IOException ex) {
		}
		try {
			myPicture2 = ImageIO.read(new File("sunemoji.png"));
		} catch (IOException ex) {
		}

		this.setPreferredSize(new Dimension(900, 900));
		playerOneName = JOptionPane.showInputDialog("Enter Player One's name:");
        playerTwoName = JOptionPane.showInputDialog("Enter Player Two's name:");
		gameBoard = new GameBoard(playerOneName, playerTwoName);
		BOARD_SIZE = gameBoard.getGameCells().length;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleMouseClick(e.getX(), e.getY());
			}
		});
		timer = new Timer(50, this); // Adjust delay as needed (50 milliseconds here)
		timer.start();
	}

	private void handleMouseClick(int x, int y) {
		int clickedRow = y / CELL_SIZE;
		int clickedCol = x / CELL_SIZE;

		if (dropPhase && cellsPlayed < 8) {
			handleDropPhase(clickedRow, clickedCol);
		} else {
			handleMovePhase(clickedRow, clickedCol);
		}
		if (gameBoard.printWinner()) {
			game();
		}
	}

	/*
	 * interprets mouse clicks on the game board, calculates which cell was clicked
	 * based on the coordinates, and then directs the game logic to the appropriate
	 * handling method depending on the current phase of the game.
	 */
	private void handleDropPhase(int row, int col) {
		if (cellsPlayed < 8) {
			if (gameBoard.isDropPhaseMoveLegal(row, col)) {
				gameBoard.dropPhasePutPieceAtLocation(row, col, currentPlayer);
				repaint();
				cellsPlayed++;
				currentPlayer = (currentPlayer == 1) ? -1 : 1;
			} else {
				System.out.println("Invalid move. Spot is already occupied. Please try again.");
			}
		} else {
			dropPhase = false;
			System.out.println("Drop phase is over. Transitioning to the move phase.");
		}
	}

	private int fromRow = -1;
	private int fromCol = -1;

	/*
	 * handles the game logic for the move phase, the player can only move their own
	 * piece to an adjacent empty cell. If the player clicks a cell that doesn't
	 * contain their piece, it informs them that the move is invalid.
	 * 
	 */
	private void handleMovePhase(int clickedRow, int clickedCol) {
		// First click: Select the player's piece
		if (gameBoard.isCellOccupiedByPlayer(GameBoard.getWhoseTurnIsIt(), clickedRow, clickedCol)) {
			fromRow = clickedRow;
			fromCol = clickedCol;

			int[] emptyCell = gameBoard.findEmptyCell();
			int emptyRow = emptyCell[0];
			int emptyCol = emptyCell[1];

			// Check if the clicked cell is adjacent to the empty cell
			moveAndRepaint(emptyRow, emptyCol);
		} else {
			System.out.println("Invalid move: Please select your own piece.");
		}

	}

	/*
	 * logic for moving a piece on the game board. It checks if the move is valid
	 * before executing it and handles the graphical update by triggering a repaint
	 * after the move.
	 * 
	 */
	private void moveAndRepaint(int emptyRow, int emptyCol) {
		if (gameBoard.isMovePhaseMoveLegal(fromRow, fromCol, emptyRow, emptyCol)) {
			gameBoard.movePieceFromLocation(fromRow, fromCol, emptyRow, emptyCol);
			repaint(); // Repaint the panel after making the move
		} else {
			System.out.println("Invalid move: Please choose an adjacent cell.");
			fromRow = -1;
			fromCol = -1;
		}
	}

	/*
	 * handles the graphical representation of the game board by drawing the grid
	 * lines and rendering the images representing the game pieces based on the
	 * ownership values
	 * 
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Drawing the board grid
		g.setColor(Color.BLACK);
		for (int i = 1; i < BOARD_SIZE; i++) {
			g.drawLine(0, i * CELL_SIZE, CELL_SIZE * BOARD_SIZE, i * CELL_SIZE);
			g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, CELL_SIZE * BOARD_SIZE);
		}

		// Drawing the pieces
		GameCell[][] cells = gameBoard.getGameCells();
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				int cellValue = cells[row][col].getWhoOwnsMe();
				if (cellValue == 1) { // Player 1 owns this cell (draw rectangle)
					g.drawImage(myPicture1, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
				} else if (cellValue == -1) { // Player 2 owns this cell (draw oval)
					g.drawImage(myPicture2, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
				}
			}
		}
	}

	/*
	 * initializes the game board by creating a FeltBoard panel, sets up its
	 * appearance and properties, creates a JFrame to contain the panel, and
	 * displays the JFrame to start the game application.
	 * 
	 */
	public static void game() {
		FeltBoard panel = new FeltBoard();
		panel.setBackground(Color.WHITE);
		JFrame frame = new JFrame("Achi - Phase One");
		frame.setSize(900, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	public static void main(String[] args) {
		game();
	}

//*indicates an animation process controlled by a timer. 
	public void stopAnimation() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}
	/*
	 * increments the frame number and repaints the component within a certain limit
	 * 40 frames in this case) before stopping the animation and resetting the frame
	 * count.
	 * 
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (frameNumber < 40) {
			frameNumber++;
			repaint();
		} else {
			stopAnimation();
			frameNumber = 0;
		}

	}
}
