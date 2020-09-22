package edu.uncc.rcavana1;

import java.util.Scanner;

public class EightQueens {
	public final static int DEFAULT_GRID_SIZE = 8;
	
	private static Agent _agent;
	private static Board _board;
	private static Scanner _scan;

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
		_scan = new Scanner(System.in);
		_agent = new Agent();
		_board = new Board(size);
		// repeat while the board has not reached goal state
		while (!_board.isGoalState()) {
			System.out.println(String.format("Agent iteration: %d", _agent.getIterations()));
			printBoard();
			int lowerNeighbors = _agent.determineLowerNeighbors(_board);
			
			System.out.println(String.format("Discovered %s lower heuristic neighbors.\r\n", lowerNeighbors));
			if (lowerNeighbors == 0) {
				System.out.println("No next best board state, restarting board...");
				_board.randomizeBoard();
			} else {
				System.out.println(String.format("Next best board state has heuristic value: %d.", _agent.getNextBestHeuristic()));
			}
			System.out.println("Press enter to go to next state...");
			waitForEnterKey();
			_board = _agent.getNextBestState();
		}
		System.out.println(String.format("Goal state reached in %d iterations.", _agent.getIterations()));
		printBoard();
		System.out.println("Press enter to exit...");
		waitForEnterKey();
		exit(false);
	}
	
	private static void printBoard() {
		System.out.println(String.format("State heuristic: %d\r\n", _board.determineHeuristic()));
		System.out.println("State:");
		System.out.println(_board.toString());
	}
	
	private static void waitForEnterKey() {
		try {
			_scan.nextLine();
		} catch (Exception ex) {
			System.out.println(String.format("Runtime error! %s", ex.getMessage()));
			exit(true);
		}
	}

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
