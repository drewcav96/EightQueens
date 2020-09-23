package edu.uncc.rcavana1;

import java.util.Scanner;

/**
 * The Main class for the EightQueens program.
 * @author Drew Cavanaugh (rcavana1@uncc.edu)
 * @since 9/22/2020
 */
public final class EightQueens {
	public final static int DEFAULT_GRID_SIZE = 8;
	
	private static Agent _agent;
	private static Board _board;
	private static Scanner _scan;

	/**
	 * The entry point for the program.
	 * @param args Command line arguments. Accepts 0 or 1 int argument specifying board size.
	 */
	public static void main(String[] args) {
		int size = DEFAULT_GRID_SIZE;
		
		if (args.length == 1) {
			try {
				size = Integer.parseInt(args[0]);
			} catch (NumberFormatException ex) {
				System.out.println(String.format("Runtime error! Did not understand argument: %s\r\n", args[0]));
				exit(true);
			}
		} else if (args.length > 1) {
			System.out.println("Runtime error! Too many arguments specified.\r\n");
			exit(true);
		}
		System.out.println("Eight Queens AI program");
		System.out.println("by Drew Cavanaugh (rcavana1@uncc.edu)");
		System.out.println(String.format("Grid size: %d", size));
		_scan = new Scanner(System.in);
		_agent = new Agent();
		_board = new Board(size);
		// repeat while the board has not reached goal state
		while (!_board.isGoalState()) {
			System.out.println(String.format("\r\n========== State: %d ==========\r\n", _agent.getIterations()));
			printBoard();
			int lowerNeighbors = _agent.determineLowerNeighbors(_board);
			
			System.out.println(String.format("Discovered %s lower heuristic neighbors.\r\n", lowerNeighbors));
			if (lowerNeighbors == 0) {
				System.out.println("No next best board state, restarting board...");
				_board.randomizeBoard();
			} else {
				System.out.println(String.format("Next best board state has heuristic value: %d.", _agent.getNextBestHeuristic()));
			}
			System.out.println("Press enter to continue...");
			waitForEnterKey();
			_board = _agent.getNextBestState();
		}
		System.out.println(String.format("\r\n========== Final State: %d ==========\r\n", _agent.getIterations()));
		System.out.println(String.format("Goal state reached in %d iterations.", _agent.getIterations()));
		System.out.println(String.format("Number of restarts: %d\r\n", _agent.getRestarts()));
		printBoard();
		System.out.println("Press enter to exit...");
		waitForEnterKey();
		exit(false);
	}
	
	/**
	 * Prints the current board state to console.
	 */
	private static void printBoard() {
		System.out.println(String.format("State heuristic: %d", _board.determineHeuristic()));
		System.out.println("State:");
		System.out.println(_board.toString());
	}
	
	/**
	 * Waits for the enter key to be pressed.
	 */
	private static void waitForEnterKey() {
		try {
			_scan.nextLine();
		} catch (Exception ex) {
			System.out.println(String.format("Runtime error! %s", ex.getMessage()));
			exit(true);
		}
	}

	/**
	 * Exits the program.
	 * If an error is specified, displays the usage prompt.
	 * @param isError Whether this program is exiting in an error state.
	 */
	private static void exit(boolean isError) {
		if (isError) {
			System.out.println("Usage: java EightQueens [boardSize]");
			System.out.println("\tWhere [boardSize] is the optional size of the board. Default is 8.\r\n");
			System.exit(-1);
		} else {
			System.exit(0);
		}
	}
}
