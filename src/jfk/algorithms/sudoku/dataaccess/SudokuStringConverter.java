package jfk.algorithms.sudoku.dataaccess;
import jfk.algorithms.sudoku.model.Sudoku;

public class SudokuStringConverter {

	private SudokuStringConverter() {}

	public static Sudoku sudokuFromString(String sudokuString) {

		Sudoku sudoku = new Sudoku();

		int[][] values = new int[9][];
		String lines[] = sudokuString.split("\\r?\\n");
		for (int lineCounter = 0; lineCounter < 9; lineCounter++) {
			values[lineCounter] = new int[9];
		}
		for (int lineCounter = 0; lineCounter < 9; lineCounter++) {
			for (int charCounter = 0; charCounter < 9; charCounter++) {
				String valueString = lines[lineCounter].substring(charCounter, charCounter + 1);
				if (valueString.equals(".")) {
					valueString = "0";
				}
				values[charCounter][lineCounter] = Integer.parseInt(valueString);
			}
		}
		sudoku.setValues(values);
		return sudoku;
	}
}