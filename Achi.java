package achi;

import java.util.Scanner;

//public class Achi {
//
//	private static Scanner getInput;	// Read input
//	private static GameBoard gameBoard;	// The board on which we play
//	private static int whoseTurnIsIt;	// 1 for player 1, -1 for player 2
//
//	/** Driver for the program. */
//	public static void main(String[] args) {
//		initializeGameVariables();
//		System.out.println("Enter moves in the format 'n m'");
//		dropPhase();
//		movePhase();
//	}
//
//	/** Create initial values for the scanner, the gameboard, and 
//	 *  who will start the game.
//	 */
//	private static void initializeGameVariables() {
//		getInput = new Scanner(System.in);
//		gameBoard = new GameBoard(playerOneName, playerTwoName);
//		whoseTurnIsIt = 1;
//	}
//	
//	/** The game keeps track of whose turn it currently is. */
//	public static int getWhoseTurnIsIt() {
//		return whoseTurnIsIt;
//	}
//
//	/** Change the player whose turn it is. */
//	public static void changePlayer() {
//		whoseTurnIsIt *= -1;
//	}
//	
///* The methods below don't really manipulate any of the data in
// * the program, so they can go in this class or in the gameBoard class.
// * I've put them here just to balance the work done in those two classes.
// */
//
//	/** Manage the drop phase of the game. */
//	private static void dropPhase() {
//		System.out.println("\nDrop phase of Achi");
//		for (int move = 1; move <= 8; move++) {
//			dropPhaseMove();
//		}
//	}
//
//	/** Manage the move phase of the game. */
//	private static void movePhase() {
//		System.out.println("\nMove phase of Achi");
//		gameBoard.findEmptyCell();
//		while (true) {
//			movePhaseMove();
//		}
//	}
//
//	/** Make one move in the Drop Phase */
//	private static void dropPhaseMove() {
//	    int row, col;
//
//	    // Get input from the user, check for validity of the move
//	    do {
//	        System.out.print("What move do you make? ");
//	        row = getInput.nextInt();
//	        col = getInput.nextInt();
//
//	        if (isValidInput(row, col) && gameBoard.isDropPhaseMoveLegal(row, col)) {
//	            gameBoard.dropPhasePutPieceAtLocation(row, col, col);
//	        } else {
//	            System.out.println("Invalid move. Please try again.");
//	        }
//	    } while (!isValidInput(row, col) || !gameBoard.isDropPhaseMoveLegal(row, col));
//	}
//	/*This method checks if the provided row and col values fall within the boundaries of the game board.
//	 * 
//	 */
//	private static boolean isValidInput(int row, int col) {
//	    // Check if the input row and column are within the board's boundaries
//	    return row >= 0 && row < gameBoard.getGameCells().length && col >= 0 && col < gameBoard.getGameCells()[0].length;
//	}
//
//
///*This method represents a phase of making a move in the game.*/
//	private static void movePhaseMove() {
//	    int fromRow, fromCol, toRow, toCol;
//	    
//	    // Get input from the user, check for validity
//	    do {
//	        fromRow = getInput.nextInt();
//	        fromCol = getInput.nextInt();
//	        toRow = getInput.nextInt();
//	        toCol = getInput.nextInt();
//	    } while (!gameBoard.isMovePhaseMoveLegal(fromRow, fromCol, toRow, toCol));
//	    
//	    // The move is legal, so make the move.
//	    gameBoard.movePieceFromLocation(fromRow, fromCol, toRow, toCol);
//	}
//}