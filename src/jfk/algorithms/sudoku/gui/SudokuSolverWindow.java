package jfk.algorithms.sudoku.gui;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import jfk.algorithms.sudoku.controller.SudokuSolver;
import jfk.algorithms.sudoku.dataaccess.FilebasedSudokuStorage;
import jfk.algorithms.sudoku.model.Sudoku;

public class SudokuSolverWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static SudokuSolverWindow window;

	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				window = new SudokuSolverWindow();
				window.setTitle("JSudoku solver");
				window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				SudokuSolverPanel panel = new SudokuSolverPanel();
				panel.setSudoku(new Sudoku());
				window.add(panel);

				window.setJMenuBar(createMenuBar(panel));
				window.setVisible(true);
				window.setResizable(false);
				window.pack();
			}

			private JMenuBar createMenuBar(SudokuSolverPanel panel) {
				ActionListener listener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						switch (e.getActionCommand()) {
						case "Open":
							openSudoku();
							break;

						case "Save":
							saveSudoku();
							break;
						case "Exit":
							System.exit(0);
							break;
						case "New":
							panel.getSudoku().clearAll();
							panel.repaint();
							break;
						case "Clear unlocked":
							panel.getSudoku().clearAllExceptLockedCells();
							panel.repaint();
							break;
						case "Lock cells":
							panel.getSudoku().lockCellsWithValues();
							panel.repaint();
							break;

						case "Solve":
							SudokuSolver.solve(panel.getSudoku());
							panel.repaint();
							break;

						}
					}

					private void saveSudoku() {

						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Specify where to save sudoku");
						fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
						fileChooser.setAcceptAllFileFilterUsed(false);
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSudoku files", "jsudoku"));
						int userSelection = fileChooser.showSaveDialog(window);

						if (userSelection == JFileChooser.APPROVE_OPTION) {
							File fileToSave = fileChooser.getSelectedFile();
							if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".jsudoku")) {
								fileToSave = new File(fileToSave.toString() + ".jsudoku");
							}
							try {
								FilebasedSudokuStorage.saveSudokuToFile(panel.getSudoku(), fileToSave.getAbsolutePath());

							} catch (Exception ex) {
								JOptionPane.showMessageDialog(window,
										"Error saving to file '" + fileToSave.getAbsolutePath() + "'. Error was: " + ex.getMessage());
							}
						}
					}

					private void openSudoku() {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
						fileChooser.setAcceptAllFileFilterUsed(false);
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSudoku files", "jsudoku"));
						int result = fileChooser.showOpenDialog(window);
						if (result == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fileChooser.getSelectedFile();
							try {
								Sudoku sudoku = FilebasedSudokuStorage.getSudokuFromFile(selectedFile.getAbsolutePath());
								sudoku.lockCellsWithValues();
								panel.setSudoku(sudoku);
								panel.repaint();
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(window, "Error opening file '"
										+ selectedFile.getAbsolutePath() + "'. Error was: " + ex.getMessage());
							}
						}
					}
				};

				JMenuBar menuBar = new JMenuBar();

				JMenu menuItemFiles = new JMenu("Files");
				menuItemFiles.setMnemonic(KeyEvent.VK_F);
				menuBar.add(menuItemFiles);

				JMenuItem menuItemNewSudoku = new JMenuItem("New");
				menuItemNewSudoku.addActionListener(listener);
				menuItemNewSudoku.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
				menuItemFiles.add(menuItemNewSudoku);

				JMenuItem menuItemOpen = new JMenuItem("Open");
				menuItemOpen.addActionListener(listener);
				menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
				menuItemFiles.add(menuItemOpen);

				JMenuItem menuItemSave = new JMenuItem("Save");
				menuItemSave.addActionListener(listener);
				menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
				menuItemFiles.add(menuItemSave);

				JMenuItem menuItemClose = new JMenuItem("Exit");
				menuItemClose.addActionListener(listener);
				menuItemClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
				menuItemFiles.add(menuItemClose);

				JMenu menuItemSudoku = new JMenu("Edit");
				menuItemSudoku.setMnemonic(KeyEvent.VK_E);
				menuBar.add(menuItemSudoku);

				JMenuItem menuItemClearUnLockedItems = new JMenuItem("Clear unlocked");
				menuItemClearUnLockedItems.addActionListener(listener);
				menuItemClearUnLockedItems.setAccelerator(KeyStroke.getKeyStroke("F5"));
				menuItemSudoku.add(menuItemClearUnLockedItems);

				JMenuItem menuItemlockCellsWithValues = new JMenuItem("Lock cells");
				menuItemlockCellsWithValues.addActionListener(listener);
				menuItemlockCellsWithValues
						.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
				menuItemSudoku.add(menuItemlockCellsWithValues);

				JMenuItem menuItemSolve = new JMenuItem("Solve");
				menuItemSolve.addActionListener(listener);
				menuItemSolve.setAccelerator(KeyStroke.getKeyStroke("F11"));
				menuItemSudoku.add(menuItemSolve);

				return menuBar;
			}
		};

		EventQueue.invokeLater(runner);
	}
}