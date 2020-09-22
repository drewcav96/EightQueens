package edu.uncc.rcavana1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Board class containing a specified number of rows, columns, and pieces (one per column).
 * @author Drew Cavanaugh (rcavana1@uncc.edu)
 * @since 9/22/2020
 */
public class Board {
	private int _gridSize;
	private boolean[][] _grid;
	
	/**
	 * Constructs a Board with set size.
	 * @param gridSize The number of rows, columns, and pieces.
	 */
	public Board(int gridSize) {
		_gridSize = gridSize;
		randomizeBoard();
	}
	
	/**
	 * Constructs a Board from existing Board parameters.
	 * @param gridSize The number of rows, columns, and pieces.
	 * @param grid The board grid to clone.
	 */
	private Board(int gridSize, boolean[][] grid) {
		_gridSize = gridSize;
		_grid = new boolean[_gridSize][];
		// perform deep clone
		_grid = Arrays.copyOf(grid, _gridSize);
		for (int i = 0; i < _gridSize; i++) {
			_grid[i] = Arrays.copyOf(grid[i], _gridSize);
		}
	}
	
	/**
	 * Randomly generates a new grid with the set grid size and populates one piece per column.
	 */
	public void randomizeBoard() {
		// just make a new grid
		_grid = new boolean[_gridSize][_gridSize];
		// randomly place 1 piece per row
		for (int j = 0; j < _gridSize; j++) {
			int rand = (int)(Math.random() * _gridSize);
			
			_grid[rand][j] = true;
		}
	}
	
	/**
	 * Determines if the board is currently in a goal state.
	 * @return Whether this board is in a goal state.
	 */
	public boolean isGoalState() {
		return determineHeuristic() == 0;
	}
	
	/**
	 * Determines the current heuristic value of this board.
	 * Lower the number, the closer it is to the goal state.
	 * @return The heuristic value of the board.
	 */
	public int determineHeuristic() {
		int heuristic = 0;
		
		for (int i = 0; i < _gridSize; i++) {
			for (int j = 0; j < _gridSize; j++) {
				// evaluate conflicts on existing pieces only
				if (_grid[i][j]) {
					heuristic += rowConflicts(i, j);
					heuristic += colConflicts(i, j);
					heuristic += diagConflicts(i, j);
				}
			}
		}
		return heuristic;
	}
	
	/**
	 * Attempts to move a piece from the specified starting row and column to a final row and column.
	 * If a piece occupies the final row and column, the original piece will not be moved.
	 * @param startRow The row containing the piece to move.
	 * @param startCol The column containing the piece to move.
	 * @param finalRow The row to move the piece to.
	 * @param finalCol The column to move the piece to.
	 * @return If the piece was able to be moved, true; otherwise false.
	 */
	public boolean tryMovePiece(int startRow, int startCol, int finalRow, int finalCol) {
		// only perform the move if there isn't already a piece in the final destination
		if (_grid[startRow][startCol] && !_grid[finalRow][finalCol]) {
			_grid[startRow][startCol] = false;
			_grid[finalRow][finalCol] = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Determines all neighbor states for this board and returns them in an ArrayList.
	 * A neighbor state is created by cloning this board and moving a piece from each column to another row.
	 * @return List of all neighbor states for this board.
	 */
	public List<Board> determineNeighbors() {
		List<Board> neighbors = new ArrayList<Board>();
		
		for (int i = 0; i < _gridSize; i++) {
			for (int j = 0; j < _gridSize; j++) {
				// create neighbor states for existing pieces only
				if (_grid[i][j]) {
					for (int k = 0; k < _gridSize; k++) {
						if (k == i) {
							// do not create a neighbor state for itself
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
	
	/**
	 * Evaluates the number of row conflicts for a given space.
	 * @param row The row of the space to evaluate.
	 * @param col The column of the space to evaluate.
	 * @return The count of conflicts in the row for the given space.
	 */
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
	
	/**
	 * Evaluates the number of column conflicts for a given space.
	 * @param row The row of the space to evaluate.
	 * @param col The column of the space to evaluate.
	 * @return The count of conflicts in the column for the given space.
	 */
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
	
	/**
	 * Evaluates the number of diagonal conflicts for a given space.
	 * @param row The row of the space to evaluate.
	 * @param col The column of the space to evaluate.
	 * @return The count of conflicts in the diagonal for the given space.
	 */
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
