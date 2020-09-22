package edu.uncc.rcavana1;

import java.util.List;

/**
 * Agent class controlling the artificial intelligence engine operating on a Board.
 * @author Drew Cavanaugh (rcavana1@uncc.edu)
 * @since 9/22/2020
 */
public class Agent {
	private int _iterations;
	private Board _nextBestState;
	private int _nextBestHeuristic;
	
	/**
	 * Constructs an Agent.
	 */
	public Agent() {
		_iterations = 1;
		_nextBestState = null;
		_nextBestHeuristic = -1;
	}
	
	/**
	 * Returns the number of iterations this Agent has processed.
	 * Iterations are incremented each time determineLowerNeighbors() is called.
	 * @return Number of iterations.
	 */
	public int getIterations() {
		return _iterations;
	}
	
	/**
	 * Returns the next best board state as determined by determineLowerNeighbors().
	 * @return The next best state.
	 */
	public Board getNextBestState() {
		return _nextBestState;
	}
	
	/**
	 * Returns the heuristic value of the next best board state as determined by determineLowerNeighbors().
	 * @return The heuristic value of the next best state.
	 */
	public int getNextBestHeuristic() {
		return _nextBestHeuristic;
	}
	
	/**
	 * Calculates all neighbors for the specified board.
	 * Evaluates number of neighbor states that have a lower heuristic value than the specified board.
	 * Remembers the next best board state and its heuristic value to be accessed.
	 * @param board The current board state.
	 * @return Number of neighbors with lower heuristic values.
	 */
	public int determineLowerNeighbors(Board board) {
		int lowerStates = 0;
		int currentHeuristic = board.determineHeuristic();
		List<Board> neighborStates = board.determineNeighbors();
		
		_nextBestState = board;
		_nextBestHeuristic = currentHeuristic;
		for (Board thisState : neighborStates) {
			int thisHeuristic = thisState.determineHeuristic();
			
			if (thisHeuristic < currentHeuristic) {
				// this neighbor's heuristic is lower than the currently evaluated state
				lowerStates++;
				if (thisHeuristic < _nextBestHeuristic) {
					// this neighbor's heuristic is the lowest of any currently evaluated neighbors
					_nextBestState = thisState;
					_nextBestHeuristic = thisHeuristic;
				}
			}
		}
		_iterations++;
		return lowerStates;
	}
}
