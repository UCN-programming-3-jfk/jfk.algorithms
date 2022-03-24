package jfk.algorithms.sudoku.model;


import java.awt.Point;
import java.util.*;

public class Sudoku {

	private int[][] values;
	private List<Point> lockedCells = new ArrayList<Point>();

	public Sudoku() {
		createEmptyCellGrid();
	}

	private void createEmptyCellGrid() {
		values = new int[9][];
		for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
			values[columnCounter] = new int[9];
		}
	}

	public int[][] getValues() {
		return values;
	}

	public void setValues(int[][] values) {
		this.values = values;
	}

	public boolean isValid() {
		for (int quadrantxCounter = 0; quadrantxCounter < 3; quadrantxCounter++) {
			for (int quadrantyCounter = 0; quadrantyCounter < 3; quadrantyCounter++) {
				if (!isQuadrantValid(quadrantxCounter, quadrantyCounter)) {
					return false;
				}
			}
		}
		for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
			if (!isRowValid(rowCounter)) {
				return false;
			}
		}

		for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
			if (!isColumnValid(columnCounter)) {
				return false;
			}
		}

		return true;
	}

	public boolean isSolved() {
		for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
			for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
				int value = getValues()[columnCounter][rowCounter];
				if (value < 1 || value > 9) {
					return false;
				}
			}
		}
		return isValid();
	}

	public boolean isQuadrantValid(int quadrantX, int quadrantY) {

		Set<Integer> set = new HashSet<Integer>();

		for (int quadrantxCounter = 0; quadrantxCounter < 3; quadrantxCounter++) {
			for (int quadrantyCounter = 0; quadrantyCounter < 3; quadrantyCounter++) {
				int x = quadrantX * 3 + quadrantxCounter;
				int y = quadrantY * 3 + quadrantyCounter;
				Integer valueFound = Integer.valueOf((getValues()[x][y]));
				if (valueFound != 0 && !set.add(valueFound)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isRowValid(int row) {

		Set<Integer> set = new HashSet<Integer>();

		for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
			Integer valueFound = Integer.valueOf((getValues()[columnCounter][row]));
			if (valueFound != 0 && !set.add(valueFound)) {
				return false;
			}
		}

		return true;
	}

	public boolean isColumnValid(int column) {

		Set<Integer> set = new HashSet<Integer>();

		for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
			Integer valueFound = Integer.valueOf((getValues()[column][rowCounter]));
			if (valueFound != 0 && !set.add(valueFound)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
			for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
				String value = getValues()[columnCounter][rowCounter] + "";
				if (value.equals("0")) {
					value = ".";
				}
				builder.append(value);
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public String toFormattedString() {
		StringBuilder builder = new StringBuilder();
		for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
			for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
				String value = getValues()[columnCounter][rowCounter] + "";
				if (value.equals("0")) {
					value = ".";
				}
				builder.append(value);
				if (columnCounter % 3 == 2 && columnCounter < 8) {
					builder.append("|");
				}
			}
			builder.append("\n");
			if (rowCounter % 3 == 2 && rowCounter < 8) {
				builder.append("---|---|---\n");
			}
		}
		return builder.toString();
	}

	public void lockCell(int x, int y) {
		if (!lockedCells.contains(new Point(x, y))) {
			lockedCells.add(new Point(x, y));
		}
	}

	public void unlockCell(int x, int y) {
		if (lockedCells.contains(new Point(x, y))) {
			lockedCells.remove(new Point(x, y));
		}
	}

	public void toggleCellLock(int x, int y) {
		if (lockedCells.contains(new Point(x, y))) {
			lockedCells.remove(new Point(x, y));
		} else {
			lockCell(x, y);
		}
	}

	public boolean isLocked(int x, int y) {
		return lockedCells.contains(new Point(x, y));
	}

	public void setValue(int x, int y, int value) {
		getValues()[x][y] = value;
	}

	public void clearAll() {
		createEmptyCellGrid();
		lockedCells = new ArrayList<Point>();
	}

	public void clearAllExceptLockedCells() {
		for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
			for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
				if (!isLocked(columnCounter, rowCounter)) {
					setValue(columnCounter, rowCounter, 0);
				}
			}

		}
	}

	public void lockCellsWithValues() {
		for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
			for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
				if (getValues()[columnCounter][rowCounter] != 0 && !isLocked(columnCounter, rowCounter)) {
					lockCell(columnCounter, rowCounter);
				}
			}
		}
	}

}