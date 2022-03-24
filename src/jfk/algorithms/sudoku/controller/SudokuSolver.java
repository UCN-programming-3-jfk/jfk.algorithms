package jfk.algorithms.sudoku.controller;

import jfk.algorithms.sudoku.model.Sudoku;

public class SudokuSolver {

	private Sudoku sudoku = null;
	
	public SudokuSolver(Sudoku sudokuToSolve) {
		setSudoku(sudokuToSolve); 
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public void setSudoku(Sudoku sudoku) {
		this.sudoku = sudoku;
	}
	
	public static boolean solve(Sudoku sudokuToSolve){
		return new SudokuSolver(sudokuToSolve).solve();
	}
	
	public boolean solve() {
		if(!getSudoku().isValid()) {
			throw new IllegalArgumentException("Sudoku is invalid");
		}
		return trySolve(0);
	}
	
	private boolean trySolve(int valueIndex) {
		if(valueIndex == 81) {
			System.out.println("Done!");
			System.out.println(getSudoku());
			return getSudoku().isSolved();
			}
		
		int x = valueIndex % 9;
		int y = valueIndex / 9;
		
		if(getSudoku().getValues()[x][y] != 0) {
			return trySolve(valueIndex+1);
		}
		for(int valueToTry = 1; valueToTry<= 9; valueToTry++) {
			System.out.println("Index:" + valueIndex + " - trying value: " + valueToTry + " at x:" + x + ", y:" + y + "");
			getSudoku().getValues()[x][y] = valueToTry;
			if(getSudoku().isValid()){
				if(trySolve(valueIndex +1)) {
					return true;
				}
			}
		}
		getSudoku().getValues()[x][y] = 0;
		
		return false;

	}
	
	static int getNumberOfSolutions(Sudoku sudoku) {

		SudokuSolver solver = new SudokuSolver(sudoku);
		return solver.getNumberOfSolutions(0);
	}
	
	private int getNumberOfSolutions(int valueIndex) {
		
		if(valueIndex == 81) {return 1;}
		
		int x = valueIndex % 9;
		int y = valueIndex / 9;
		
		if(getSudoku().getValues()[x][y] != 0) {
			return getNumberOfSolutions(valueIndex+1);
		}
		
		for(int valueToTry = 1; valueToTry<= 9; valueToTry++) {
			System.out.println("Index:" + valueIndex + " - trying value: " + valueToTry + " at x:" + x + ", y:" + y + "");
			getSudoku().getValues()[x][y] = valueToTry;
			if(getSudoku().isValid()){
				int solutions = getNumberOfSolutions(valueIndex + 1);
				if(solutions > 0) {
					return solutions;
				}
			}
		}
		getSudoku().getValues()[x][y] = 0;
		
		return 0;
	}
	
	
}