package com.atomicobject.othello;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Timer;

public class AI {
	
	private int[][] scoreBoard = {
		{1500, -300,  150,  100,  100,  150, -300, 1500},
        {-300, -300,  50,   20,   20,   50, -300, -300},
        { 150,   50,   15,   15,   15,   15,   50,  150},
        { 100,   20,   15,   10,   10,   15,   20,  100},
        { 100,   20,   15,   10,   10,   15,   20,  100},
        { 150,   50,   15,   15,   15,   15,   50,  150},
        {-300, -300,   50,   20,   20,   50, -300, -300},
        {1500, -300,  150,  100,  100,  150, -300, 1500}
		// {1000, -100,  150,  100,  100,  150, -100, 1000},
		// {-100, -200,   20,   20,   20,   20, -200, -100},
		// { 150,   20,   15,   15,   15,   15,   20,  150},
		// { 100,   20,   15,   10,   10,   15,   20,  100},
		// { 100,   20,   15,   10,   10,   15,   20,  100},
		// { 150,   20,   15,   15,   15,   15,   20,  150},
		// {-100, -200,   20,   20,   20,   20, -200, -100},
		// {1000, -100,  150,  100,  100,  150, -100, 1000}
	};

	public AI(int[][] moves) {}

	public int[] computeMove(GameState state) {
		System.out.println("AI returning canned move for game state - " + state);

		int [] move = this.miniMaxDecision(state.getBoard(), state.getPlayer());

		return move;
	}

	/*
	* Starts the minimax algorithm to determine a best move to make
	*
	* @param 		The current state of the board
	* @param 		The current player
	*
	* @return 		An array containing the move
	*/
	private int [] miniMaxDecision(int [][] board, int player) {
		int opponent = (player == 1) ? 2 : 1;

		// Get the legal moves of the current board state
		ArrayList<int []> legalMoves = this.getLegalMoves(board, player);

		if (legalMoves.size() == 0) {
			return new int [0];
		}
		else {
			int bestMoveValue = Integer.MIN_VALUE;
			int [] bestMove = new int [0];

			// Loop through all the possible legal moves
			for (int [] currentMove : legalMoves) {

				int [][] tempBoard = new int [8][8];
				this.copy(tempBoard, board);

				this.makeMove(tempBoard, currentMove[0], currentMove[1], player);

				// Recursive call to miniMaxValue
				int value = this.miniMaxValue(tempBoard, player, opponent, 1);
				if (value > bestMoveValue) {
					bestMoveValue = value;
					bestMove = currentMove;
				}
			}

			return bestMove;
		}
	}

	/*
	* Copies the given array into the given destination
	*
	* @param 		The destination to store the data
	* @param 		The source of the data
	*/
	private void copy(int [][] destination, int [][] source) {
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j ++) {
				destination[i][j] = source[i][j];
			}
		}
	}

	/*
	* Runs the minimax algorithm to determine the move with the optimal value
	*
	* @param 		The current state of the board
	* @param 		The player value (1 or 2)
	* @param 		The player whose turn it currently is
	* @param 		The depth to search til
	*/
	private int miniMaxValue(int [][] board, int player, int currentTurn, int depth) {
		int opponent = (currentTurn == 1) ? 2 : 1;

		// Stop if we've reached the maximum depth
		if (depth == 6) {
			return this.getHeuristic(board, player);
		}

		ArrayList<int []> legalMoves = this.getLegalMoves(board, currentTurn);

		// If no moves exist
		if (legalMoves.size() == 0) {
			return this.miniMaxValue(board, player, opponent, depth + 1);
		}
		else {
			int bestMoveValue = Integer.MIN_VALUE;
			if (player != currentTurn) {
				bestMoveValue = Integer.MAX_VALUE;
			}

			// Run through the list of legal moves
			for (int [] currentMove : legalMoves) {
				int [][] tempBoard = new int [8][8];
				this.copy(tempBoard, board);

				this.makeMove(tempBoard, currentMove[0], currentMove[1], currentTurn);

				// Recursive call to miniMaxValue
				int value = this.miniMaxValue(tempBoard, player, opponent, depth + 1);

				// Calculates optimal value
				if (player == currentTurn) {
					if (value > bestMoveValue) {
						bestMoveValue = value;
					}
				}
				else {
					if (value < bestMoveValue) {
						bestMoveValue = value;
					}
				}
			}

			return bestMoveValue;
		}
	}

	/*
	* Calculates the overall score of the current player
	*
	* @param 		The current state of the board
	* @param 		The current player
	*
	* @return 		The overall score of the given player
	*/
	private int getHeuristic(int [][] board, int player) {
		int opponent = (player == 1) ? 2 : 1;
		int playerScore = this.getScore(board, player);
		int opponentScore = this.getScore(board, opponent);
		return playerScore - opponentScore;
	}

	/*
	* Calculates the score of the given player
	*
	* @param 		The current state of the board
	* @param 		The current player
	*
	* @return 		The score of the given player
	*/
	private int getScore(int [][] board, int player) {
		int score = 0;
		for (int x = 0; x < 8; x ++) {
			for (int y = 0; y < 8; y ++) {
				if (board[x][y] == player) {
					score += this.scoreBoard[x][y];
				}
			}
		}

		return score;
	}

	/*
	* Gets the list of legal moves from the board
	*
	* @param 		The current state of the board
	* @param 		Your player value (1 or 2)
	*
	* @return 		An arraylist of integer arrays
	*/
	private ArrayList<int []> getLegalMoves(int [][] board, int player) {
		ArrayList<int []> legalMoves = new ArrayList<int []>();

		for (int x = 0; x < 8; x ++) {
			for (int y = 0; y < 8; y ++) {
				if (this.validMove(board, x, y, player)) {
					legalMoves.add(new int [] {x, y});
				}
			}
		}

		return legalMoves;
	}

	/*
	* Checks if the given move is a valid move given the current state of the board
	*
	* @param 		The current state of the board
	* @param 		The x coordinate
	* @param 		The y coordinate
	* @param 		The player
	*
	* @return 		True if it is a valid move, false otherwise
	*/
	private boolean validMove(int [][] board, int x, int y, int player) {
		int opponent = (player == 1) ? 2 : 1;

		if (board[x][y] != 0) {
			return false;
		}

		// Check to the left
		if (this.canFlip(board, x - 1, y, -1, 0, player, opponent)) {
			return true;
		}

		// Check to the right
		if (this.canFlip(board, x + 1, y, 1, 0, player, opponent)) {
			return true;
		}

		// Check down
		if (this.canFlip(board, x, y - 1, 0, -1, player, opponent)) {
			return true;
		}

		// Check up
		if (this.canFlip(board, x, y + 1, 0, 1, player, opponent)) {
			return true;
		}

		// Check down-left	
		if (this.canFlip(board, x - 1, y - 1, -1, -1, player, opponent)) {
			return true;
		}

		// Check down-right
		if (this.canFlip(board, x + 1, y - 1, 1, -1, player, opponent)) {
			return true;
		}

		// Check up-left	
		if (this.canFlip(board, x - 1, y + 1, -1, 1, player, opponent)) {
			return true;
		}

		// Check up-right
		if (this.canFlip(board, x + 1, y + 1, 1, 1, player, opponent)) {
			return true;
		}

		return false;
	}

	/*
	* Makes the given move on the given board
	*
	* @param 		The current state of the board
	* @param 		The x coordinate
	* @param 		The y coordinate
	* @param 		The player (1 or 2)
	*/
	private void makeMove(int [][] board, int x, int y, int player) {
		int opponent = (player == 1) ? 2 : 1;

		board[x][y] = player;

		/*
		* The following code determines if we can flip any pieces with the current move
		*/

		// Checks to the left
		if (this.canFlip(board, x - 1, y, -1, 0, player, opponent)) {
			this.flip(board, x - 1, y, -1, 0, player, opponent);
		}

		// Checks to the right
		if (this.canFlip(board, x + 1, y, 1, 0, player, opponent)) {
			this.flip(board, x + 1, y, 1, 0, player, opponent);
		}

		// Checks downwards
		if (this.canFlip(board, x, y - 1, 0, -1, player, opponent)) {
			this.flip(board, x, y - 1, 0, -1, player, opponent);
		}

		// Checks upwards
		if (this.canFlip(board, x, y + 1, 0, 1, player, opponent)) {
			this.flip(board, x, y + 1, 0, 1, player, opponent);
		}

		// Checks downwards to the left	
		if (this.canFlip(board, x - 1, y - 1, -1, -1, player, opponent)) {
			this.flip(board, x - 1, y - 1, -1, -1, player, opponent);
		}

		// Checks downwards to the right
		if (this.canFlip(board, x + 1, y - 1, 1, -1, player, opponent)) {
			this.flip(board, x + 1, y - 1, 1, -1, player, opponent);
		}

		// Checks upwards to the left	
		if (this.canFlip(board, x - 1, y + 1, -1, 1, player, opponent)) {
			this.flip(board, x - 1, y + 1, -1, 1, player, opponent);
		}

		// Checks upwards to the right
		if (this.canFlip(board, x + 1, y + 1, 1, 1, player, opponent)) {
			this.flip(board, x + 1, y + 1, 1, 1, player, opponent);
		}
	}

	/*
	* Checks to see if pieces can be flipped on the given board
	*
	* @param 		The current state of the board
	* @param 		The x coordinate
	* @param 		The y coordinate
	* @param 		The change in x value
	* @param 		The change in y value
	* @param 		The player
	* @param 		The opponent
	*/
	private boolean canFlip(int [][] board, int x, int y, int xShift, int yShift, int player, int opponent) {
		// Runs through the board
		if (x < 8 && y < 8 && x >= 0 && y >= 0 && board[x][y] == opponent) {
			while (x >= 0 && x < 8 && y >= 0 && y < 8) {
				x += xShift;
				y += yShift;

				// Determines if the current cell can be flipped
				if (x >= 0 && x < 8 && y >= 0 && y < 8) {
					if (board[x][y] == 0) {
						return false;
					}
					else if (board[x][y] == player) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/*
	* Actually implements a flipping of the cells in the board
	*
	* @param 		The current state of the board
	* @param 		The x coordinate
	* @param 		The y coordinate
	* @param 		The change in x value
	* @param 		The change in y value
	* @param 		The player
	* @param 		The opponent
	*/
	private void flip(int [][] board, int x, int y, int xShift, int yShift, int player, int opponent) {
		while (board[x][y] == opponent) {
			board[x][y] = player;
			x += xShift;
			y += yShift;
		}
	}

	/*
	* Prints out the list of legal moves (Only for debugging)
	*
	* @param 		The list of legal moves
	*/
	private void printLegalMoves(ArrayList<int []> legalMoves) {
		for (int i = 0; i < legalMoves.size(); i ++) {
			System.out.println("[" + legalMoves.get(i)[0] + ", " + legalMoves.get(i)[1] + "]");
		}
	}

	/*
	* Prints out the current state of the board (Only for debugging)
	* 
	* @param 		The current state of the board
	*/
	private void printBoard(int [][] board) {
		for (int [] row : board) {
			System.out.print("[");
			for (int cell : row) {
				System.out.print(cell);
			}
			System.out.println("]");
		}
	}
}
