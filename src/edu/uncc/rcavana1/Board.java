package edu.uncc.rcavana1;

public class Board {
	private int _gridSize;
	private boolean[][] _grid;
	private int _heuristic;
	
	public Board(int gridSize) {
		_gridSize = gridSize;
		_grid = new boolean[_gridSize][_gridSize];
	}
	
	public void randomize() {
		for (boolean[] row : _grid) {
			int rand = (int)(Math.random() * _gridSize);
			
			row[rand] = true;
		}
	}
	
	public int getHeuristic() {
		return _heuristic;
	}
	
	@Override
	public String toString() {
		String str = "\t";
		
		for (boolean[] row : _grid) {			
			for (int i = 0; i < _gridSize; i++) {
				boolean col = row[i];
				
				if (col) {
					str += "1";
				} else {
					str += "0";
				}
				if (i == _gridSize - 1) {
					str += "\r\n\t";
				} else {
					str += ",";
				}
			}
		}
		return str;
	}
}
