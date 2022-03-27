package jfk.algorithms.sudoku.dataaccess;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import jfk.algorithms.sudoku.model.Sudoku;

public class FilebasedSudokuStorage {

	private FilebasedSudokuStorage() {}

	public static Sudoku getSudokuFromFile(String absolutePath) throws Exception {
		return SudokuStringConverter.sudokuFromString(Files.readString(Path.of(absolutePath)));
	}

	public static void saveSudokuToFile(Sudoku sudoku, String absolutePath) throws Exception {
		  Files.writeString(Path.of(absolutePath), sudoku.toString(), StandardOpenOption.CREATE);
	}
}
