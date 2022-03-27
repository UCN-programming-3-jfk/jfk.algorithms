package jfk.algorithms.sudoku.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jfk.algorithms.sudoku.model.Sudoku;

public class SudokuGenerator {

	public enum Difficulty{Easy, Medium, Hard}
	public Sudoku generateSudoku(Difficulty difficulty) {

		int numbersToAddToSingleSolution = 0;
		switch(difficulty) {

		case Easy : numbersToAddToSingleSolution = 25;break;
		case Medium : numbersToAddToSingleSolution = 15;break;
		case Hard : numbersToAddToSingleSolution = 5;break;
		}

		Sudoku sudoku = new Sudoku();
		SudokuSolver.solve(sudoku);

		List<Point> positions = new ArrayList<Point>();

		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				positions.add(new Point(x,y));
			}
		}
		Collections.shuffle(positions);
		List<Point> tested = new ArrayList<Point>();





		return sudoku;
	}


}
