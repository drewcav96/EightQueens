package edu.uncc.rcavana1;

import java.util.List;

public class Agent {
	private int _iterations;
	private Board _nextBestState;
	private int _nextBestHeuristic;
	
	public Agent() {
		_iterations = 1;
		_nextBestState = null;
		_nextBestHeuristic = -1;
	}
	
	public int getIterations() {
		return _iterations;
	}
	
	public Board getNextBestState() {
		return _nextBestState;
	}
	
	public int getNextBestHeuristic() {
		return _nextBestHeuristic;
	}
	
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
