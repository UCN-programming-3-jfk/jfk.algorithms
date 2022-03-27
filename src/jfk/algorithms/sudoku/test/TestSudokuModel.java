package jfk.algorithms.sudoku.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jfk.algorithms.sudoku.controller.SudokuSolver;
import jfk.algorithms.sudoku.model.Sudoku;

class TestSudokuModel {

	@Test
	void testEmptySudokuIsValidButNotSolved() {
		Sudoku sudoku = new Sudoku();
		assertTrue(sudoku.isValid(), "Sudoku is not reported valid though it is empty");
		assertFalse(sudoku.isSolved(), "Sudoku is reported solved though it is empty");
	}

	@Test
	void testInvalidSudokuIsReportedInvalid() {
		Sudoku sudoku = new Sudoku();
		int[][] values = sudoku.getValues();
		values[0][0] = 1;
		values[0][1] = 2;
		values[0][2] = 3;
		values[1][0] = 4;
		values[1][1] = 5;
		values[1][2] = 6;
		values[2][0] = 7;
		values[2][1] = 8;
		values[2][2] = 9;
		values[0][3] = 1;

		System.out.println(sudoku);
		assertFalse(sudoku.isValid(), "Invalid sudoku is reported valid");
	}

	@Test
	void testInvalidRowIsReportedInvalid() {
		Sudoku sudoku = new Sudoku();
		int[][] values = sudoku.getValues();
		values[0][0] = 1;
		values[1][0] = 1;

		assertFalse(sudoku.isRowValid(0), "Invalid row is reported valid");
	}

	@Test
	void testInvalidColumnIsReportedInvalid() {
		Sudoku sudoku = new Sudoku();
		int[][] values = sudoku.getValues();
		values[0][0] = 1;
		values[0][1] = 1;

		assertFalse(sudoku.isColumnValid(0), "Invalid column is reported valid");
	}

	@Test
	void testSolvingEmptySudoku() {
		Sudoku sudoku = new Sudoku();
		SudokuSolver solver = new SudokuSolver(sudoku);
		assertTrue(solver.solve(), "Sudoku was not solved");
	}

	@Test
	void testSolvingSudokuWithValues() {
		Sudoku sudoku = new Sudoku();

		int[][] values = sudoku.getValues();
		values[0][0] = 1;
		values[1][1] = 2;
		values[2][2] = 3;
		values[3][3] = 4;
		values[4][4] = 5;
		values[5][5] = 6;
		values[6][6] = 7;
		values[7][7] = 8;
		values[8][8] = 9;

		SudokuSolver solver = new SudokuSolver(sudoku);
		assertTrue(solver.solve(), "Sudoku was not solved");
	}


}