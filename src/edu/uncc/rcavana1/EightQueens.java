package edu.uncc.rcavana1;

import java.util.Scanner;

public class EightQueens {
	public final static int DEFAULT_GRID_SIZE = 8;
	
	private static Solver _solver;
	private static Board _board;
	private static Scanner _scan;

	public static void main(String[] args) {
		int size = DEFAULT_GRID_SIZE;
		
		if (args.length == 1) {
			try {
				size = Integer.parseInt(args[0]);
			} catch (NumberFormatException ex) {
				System.out.println(String.format("Runtime error! Did not understand input: %s\r\n", args[0]));
				exit(true);
			}
		} else if (args.length > 1) {
			System.out.println("Runtime error! Too many arguments specified.");
			exit(true);
		}
		_scan = new Scanner(System.in);
		_solver = new Solver();
		_board = new Board(size);
		_board.randomize();
		while (!_solver.getHasSolved()) {
			_solver.nextState(_board);
			System.out.println(String.format("Solver State: %d", _solver.getStateNum()));
			System.out.println(String.format("Board heuristic: %d\r\n", _board.getHeuristic()));
			System.out.println("Board:");
			System.out.println(_board.toString());
			System.out.println("Press enter to go to next state...");
			try {
				_scan.nextLine();
			} catch (Exception ex) {
				System.out.println(String.format("Runtime error! %s", ex.getMessage()));
			}
		}
	}

	private static void exit(boolean isError) {
		if (isError) {
			System.out.println("Usage: java EightQueens [boardSize]");
			System.out.println("\tWhere [boardSize] is optional size of the board. Default is 8.\r\n");
			System.exit(-1);
		} else {
			System.exit(0);
		}
	}
}
