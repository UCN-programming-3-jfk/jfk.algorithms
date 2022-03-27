package jfk.algorithms.sudoku.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jfk.algorithms.sudoku.controller.SudokuSolver;
import jfk.algorithms.sudoku.dataaccess.SudokuStringConverter;
import jfk.algorithms.sudoku.model.Sudoku;

class SudokuGeneratorTests {

	@Test
	void testSingleSolutionToSudokuWithOneSolution() {
		Sudoku sudoku = SudokuStringConverter.sudokuFromString(
		"123456789\n" +
		"456789123\n" +
		"789123456\n" +
		"214365897\n" +
		"365897214\n" +
		"897214365\n" +
		"531642978\n" +
		"642978531\n" +
		"978531642");

		assertEquals(1,SudokuSolver.getNumberOfSolutions(sudoku), "Wrong number of solutions calculated. Should have been 1");
	}

	@Test
	void testSingleSolutionToSudokuWithTwoSolutions() {
		Sudoku sudoku = SudokuStringConverter.sudokuFromString(
		"345126789\n" +
		"678349125\n" +
		"..9578346\n" +
		"..3457698\n" +
		"456891237\n" +
		"897263451\n" +
		"531682974\n" +
		"764935812\n" +
		"982714563");

		assertEquals(2,SudokuSolver.getNumberOfSolutions(sudoku), "Wrong number of solutions calculated. Should have been 1");
	}


	@Test
	void testSingleSolutionToSudokuWithFourSolutions() {
		Sudoku sudoku = SudokuStringConverter.sudokuFromString(
		"345126789\n" +
		"678349125\n" +
		"9..578..6\n" +
		"5..697..8\n" +
		"436281597\n" +
		"789435261\n" +
		"153862974\n" +
		"267914853\n" +
		"894753612");

		assertEquals(4,SudokuSolver.getNumberOfSolutions(sudoku), "Wrong number of solutions calculated. Should have been 1");
	}


}