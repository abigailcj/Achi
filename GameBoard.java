package achi;
import javax.swing.JOptionPane;
public class GameBoard {
	private GameCell[][] gameCells;
	private int rowOfEmptyCell;
	private int colOfEmptyCell;
	private static final int BOARD_SIZE = 3;
	private static int whoseTurnIsIt = 1;
	private String playerOneName, playerTwoName;

	/*
	 * Constructor and iterates through each cell of the gameCells array and
	 * initializes them by creating new GameCell objects.
	 */
	public GameBoard(String myPlayerOneName, String myPlayerTwoName) {
		gameCells = new GameCell[BOARD_SIZE][BOARD_SIZE];
		this.playerOneName = myPlayerOneName;
		this.playerTwoName = myPlayerTwoName;
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				gameCells[row][col] = new GameCell();
			}
		}
	}

//*It returns a 2D array of GameCell objects
	public GameCell[][] getGameCells() {
		return gameCells;
	}

	/** If we're in the drop phase, is this a legal move? */
	// Inside GameBoard class...

	public boolean isDropPhaseMoveLegal(int row, int col) {
		return gameCells[row][col].getWhoOwnsMe() == 0;
	}

	// *This method returns a boolean value (true or false).
	// *It checks if the provided row and col values are within the valid range for
	// the game board.
	public boolean isValidInput(int row, int col) {
		return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
	}

	/**
	 * During the "Drop Phase", drop a piece at the specified location.
	 * Pre-condition: The move has been checked to be legal.
	 */
	// Inside GameBoard class...
	public void dropPhasePutPieceAtLocation(int row, int col, int currentPlayer) {
		int player = currentPlayer;
		gameCells[row][col].setWhoOwnsMe(player);
		// Don't switch player here if the drop phase doesn't involve alternating turns
		System.out.print("The current board is:\n" + this);
//		printWinner(); // Check for a winner after each drop phase move
	}

	/**
	 * During the Move Phase, exactly one cell should be empty. Find that empty cell
	 * and remember where it is.
	 */
	public int[] findEmptyCell() {
		int[] emptyCell = new int[2];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				if (gameCells[row][col].getWhoOwnsMe() == 0) {
					emptyCell[0] = row;
					emptyCell[1] = col;
					return emptyCell;
				}
			}
		}
		return emptyCell; // Return an empty cell if not found (may need to handle this case)
	}

	/** If we're in the move phase, is this a legal move? */
	public boolean isMovePhaseMoveLegal(int fromRow, int fromCol, int toRow, int toCol) {
		if (!isCellOccupiedByPlayer(whoseTurnIsIt, fromRow, fromCol)) {
			System.out.println("Invalid move: Please try again.");
			return false;
		}
		if (!isLegalDiagonalMove(fromRow, fromCol, toRow, toCol)
				&& !isLegalRowOrColMove(fromRow, fromCol, toRow, toCol)) {
			System.out.println("Invalid move: Please try again.");
			return false;
		}
		if (gameCells[toRow][toCol].getWhoOwnsMe() == 0) {
			return true;
		} else {
			System.out.println("Invalid move: Please try again.");
			return false;
		}
	}

	/*
	 * This is a private function, so we don't need to document it for coders
	 * outside of this class.
	 * 
	 * The algorithm was discussed in class. During the move phase, this checks
	 * whether a requested move is legal because it's moving 1 step horizontally or
	 * vertically to the vacant position.
	 */
	private boolean isLegalRowOrColMove(int fromRow, int fromCol, int toRow, int toCol) {
		return (Math.abs(fromRow - toRow) + Math.abs(fromCol - toCol) == 1);
	}

	/*
	 * A diagonal move is legal if either the piece being moved is in the center, or
	 * the place they're moving to is the center.
	 */
	public boolean isLegalDiagonalMove(int fromRow, int fromCol, int toRow, int toCol) {
		if ((rowOfEmptyCell == 1) && (colOfEmptyCell == 1)) {
			return true;
		} else
			return (fromRow == 1 && fromCol == 1) || (toRow == 1 && toCol == 1);
	}

	/*
	 * checks if the cell is occupied by the player and returns based on if it is or
	 * not
	 */
	public boolean isCellOccupiedByPlayer(int player, int row, int col) {
		return gameCells[row][col].getWhoOwnsMe() == player;
	}

	/**
	 * If the requested move is legal, make the move. Pre-condition: The move has
	 * been checked to be legal.
	 */
	public void movePieceFromLocation(int fromRow, int fromCol, int toRow, int toCol) {
		if (isMovePhaseMoveLegal(fromRow, fromCol, toRow, toCol)) {
			gameCells[toRow][toCol].setWhoOwnsMe(gameCells[fromRow][fromCol].getWhoOwnsMe());
			gameCells[fromRow][fromCol].setWhoOwnsMe(0);
			changePlayer();
//			printWinner(); // Check for a winner after each move phase move
		} else {
			System.out.println("Invalid move in move phase. Please try again.");
		}
	}

	/*
	 * This static method returns the value of the static variable whoseTurnIsIt,
	 * which represents whose turn it is in the game. It provides access to the
	 * current player's turn.
	 */
	public static int getWhoseTurnIsIt() {
		return whoseTurnIsIt;
	}

	/*
	 * This method changes the player's turn by toggling the value of whoseTurnIsIt.
	 * It checks if whoseTurnIsIt is equal to 1; if so, it changes it to -1, and
	 * vice versa.
	 * 
	 */
	public void changePlayer() {
		whoseTurnIsIt = (whoseTurnIsIt == 1) ? -1 : 1;
	}

	/*
	 * This method checks if any player has won in any of the rows on the game
	 * board. It iterates through each row and checks if all three cells in a row
	 * belong to the same player.
	 * 
	 */
	private boolean checkRowWins() {
	    for (int row = 0; row < BOARD_SIZE; row++)
	        if (gameCells[row][0].getWhoOwnsMe() != 0 && gameCells[row][0].getWhoOwnsMe() == gameCells[row][1].getWhoOwnsMe() && gameCells[row][0].getWhoOwnsMe() == gameCells[row][2].getWhoOwnsMe()) {
	            int winnerPlayer = (gameCells[row][0].getWhoOwnsMe() == -1 ? 2 : 1);
	            String winnerName = (winnerPlayer == 1 ? playerOneName : playerTwoName);
	            int option = JOptionPane.showConfirmDialog(null, "Winner is " + winnerName + ". Would you like to restart the game?", "Game Over", JOptionPane.YES_NO_OPTION);
	            if (option == JOptionPane.YES_OPTION) {
	            	resetGame();
		            return true;
	            }
	            else {
	            	 System.exit(0);
	            }
	        }
	    return false;
	}

	/*
	 * This method checks if any player has won in any of the columns on the game
	 * board. It iterates through each column and checks if all three cells in a
	 * column belong to the same player.
	 * 
	 */
	private boolean checkColWins() {
	    for (int col = 0; col < BOARD_SIZE; col++)
	        if (gameCells[0][col].getWhoOwnsMe() != 0 && gameCells[0][col].getWhoOwnsMe() == gameCells[1][col].getWhoOwnsMe() && gameCells[0][col].getWhoOwnsMe() == gameCells[2][col].getWhoOwnsMe()) {
	            int winnerPlayer = (gameCells[0][col].getWhoOwnsMe() == -1 ? 2 : 1);
	            String winnerName = (winnerPlayer == 1 ? playerOneName : playerTwoName);
	            int option = JOptionPane.showConfirmDialog(null, "Winner is " + winnerName + ". Would you like to restart the game?", "Game Over", JOptionPane.YES_NO_OPTION);
	            if (option == JOptionPane.YES_OPTION) resetGame();
	            else {
	            	 System.exit(0);
	            }
	            return true;
	        }
	    return false;
	}
	public void resetGame() {

        JOptionPane.showMessageDialog(null, "The game has been reset.");
        

        // Notify the user that the game has been reset
        }
	/*
	 * This method checks for both row-based and column-based wins by calling
	 * checkRowWins() and checkColWins(), respectively.
	 */

	public boolean printWinner() {
		boolean rowWins = checkRowWins();
		boolean colWins = checkColWins();
		return rowWins || colWins;
	}

	/**
	 * Convert this data structure into a readable string. May be used for debugging
	 * or for program output
	 */
	public String toString() {
		String stringSoFar = "";
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				stringSoFar += gameCells[row][col];
				stringSoFar += (col < 2) ? "," : "\n";
			}
		}
		return stringSoFar;
	}
}

