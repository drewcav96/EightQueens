package edu.uncc.rcavana1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	private int _gridSize;
	private boolean[][] _grid;
	
	public Board(int gridSize) {
		_gridSize = gridSize;
		randomizeBoard();
	}
	
	private Board(int gridSize, boolean[][] grid) {
		_gridSize = gridSize;
		_grid = new boolean[_gridSize][];
		// perform deep clone
		_grid = Arrays.copyOf(grid, _gridSize);
		for (int i = 0; i < _gridSize; i++) {
			_grid[i] = Arrays.copyOf(grid[i], _gridSize);
		}
	}
	
	public void randomizeBoard() {
		// just make a new grid
		_grid = new boolean[_gridSize][_gridSize];
		// randomly place 1 piece per row
		for (int j = 0; j < _gridSize; j++) {
			int rand = (int)(Math.random() * _gridSize);
			
			_grid[rand][j] = true;
		}
	}
	
	public boolean isGoalState() {
		return determineHeuristic() == 0;
	}
	
	public int determineHeuristic() {
		int heuristic = 0;
		
		for (int i = 0; i < _gridSize; i++) {
			for (int j = 0; j < _gridSize; j++) {
				if (_grid[i][j]) {
					heuristic += rowConflicts(i, j);
					heuristic += colConflicts(i, j);
					heuristic += diagConflicts(i, j);
				}
			}
		}
		return heuristic;
	}
	
	public boolean tryMovePiece(int startRow, int startCol, int finalRow, int finalCol) {
		// only perform the move if there isn't already a piece in the final destination
		if (_grid[startRow][startCol] && !_grid[finalRow][finalCol]) {
			_grid[startRow][startCol] = false;
			_grid[finalRow][finalCol] = true;
			return true;
		}
		return false;
	}
	
	public List<Board> determineNeighbors() {
		List<Board> neighbors = new ArrayList<Board>();
		
		for (int i = 0; i < _gridSize; i++) {
			for (int j = 0; j < _gridSize; j++) {
				if (_grid[i][j]) {
					for (int k = 0; k < _gridSize; k++) {
						if (k == i) {
							continue;
						}
						Board neighbor = new Board(_gridSize, _grid);
						
						if (neighbor.tryMovePiece(i, j, k, j)) {
							// if piece move was successful, it becomes an alternate state
							neighbors.add(neighbor);
						}
					}
				}
			}
		}
		return neighbors;
	}
	
	private int rowConflicts(int row, int col) {
		int count = 0;
		
		for (int i = 0; i < _gridSize; i++) {
			if (i == col) {
				// do not count itself as a conflict
				continue;
			}
			if (_grid[row][i]) {
				count++;
			}
		}
		return count;
	}
	
	private int colConflicts(int row, int col) {
		int count = 0;
		
		for (int j = 0; j < _gridSize; j++) {
			if (j == row) {
				// do not count itself as a conflict
				continue;
			}
			if (_grid[j][col]) {
				count++;
			}
		}
		return count;
	}
	
	private int diagConflicts(int row, int col) {
		int count = 0;
		
		// traverse northwest from origin
		for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
			if (_grid[i][j]) {
				count++;
			}
		}
		// traverse northeast from origin
		for (int i = row + 1, j = col - 1; i < _gridSize && j >= 0; i++, j--) {
			if (_grid[i][j]) {
				count++;
			}
		}
		// traverse southeast from origin
		for (int i = row + 1, j = col + 1; i < _gridSize && j < _gridSize; i++, j++) {
			if (_grid[i][j]) {
				count++;
			}
		}
		// traverse southwest from origin
		for (int i = row - 1, j = col + 1; i >= 0 && j < _gridSize; i--, j++) {
			if (_grid[i][j]) {
				count++;
			}
		}
		return count;
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
