package edu.uncc.rcavana1;

public class Solver {
	private boolean _hasSolved;
	private int _stateNum;
	
	public Solver() {
		_hasSolved = false;
		_stateNum = 0;
	}
	
	public boolean getHasSolved() {
		return _hasSolved;
	}
	
	public int getStateNum() {
		return _stateNum;
	}
	
	public void nextState(Board board) {
		_stateNum++;
	}
}
