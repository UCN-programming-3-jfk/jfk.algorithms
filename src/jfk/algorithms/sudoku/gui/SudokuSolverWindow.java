package jfk.algorithms.sudoku.gui;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import jfk.algorithms.sudoku.model.Sudoku;
import jfk.algorithms.sudoku.model.SudokuSolver;

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
						    if(!fileToSave.getAbsolutePath().toLowerCase().endsWith(".jsudoku")) {
						    	fileToSave = new File(fileToSave.toString() + ".jsudoku");
						    }
						    
						    try {
							    int[][] values = panel.getSudoku().getValues();
							    FileWriter myWriter = new FileWriter(fileToSave.getAbsolutePath());
							      for(int rowCounter = 0; rowCounter <9; rowCounter++) {
							    	  for(int columnCounter = 0; columnCounter <9; columnCounter++) {
							    		  String valueToWrite = values[columnCounter][rowCounter] + "";
							    		  if(valueToWrite.equals("0")) {valueToWrite=".";}
							    		  myWriter.write(valueToWrite);
							    	  }
							    	  myWriter.write("\n");
							      }
							      myWriter.close();
							    }
							    catch(Exception ex) {
							    	JOptionPane.showMessageDialog(window, "Error saving to file '" + fileToSave.getAbsolutePath() + "'. Error was: " + ex.getMessage());
							    }
						    
						    fileToSave.getAbsolutePath();
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
						    int[][] values = fileToValues(selectedFile);
						    panel.getSudoku().setValues(values);
						    panel.getSudoku().lockCellsWithValues();
						    panel.repaint();
						    }
						    catch(Exception ex) {
						    	JOptionPane.showMessageDialog(window, "Error opening file '" + selectedFile.getAbsolutePath() + "'. Error was: " + ex.getMessage());
						    }
						}
						
					}

					private int[][] fileToValues(File selectedFile) throws IOException {
						int[][] values = new int[9][];
						String content = Files.readString(Path.of(selectedFile.getAbsolutePath()));
						String lines[] = content.split("\\r?\\n");
						for(int lineCounter = 0; lineCounter < 9; lineCounter++) {
							values[lineCounter] = new int[9];
						}
						for(int lineCounter = 0; lineCounter < 9; lineCounter++) {
							for(int charCounter = 0; charCounter<9; charCounter++) {
								String valueString = lines[lineCounter].substring(charCounter, charCounter+1);
								if(valueString.equals(".")) {valueString = "0";}
								values[charCounter][lineCounter] = Integer.parseInt(valueString);
							}
						}
						return values;
					}
				};

				JMenuBar menuBar = new JMenuBar();

				JMenu menuItemFiles = new JMenu("Files");
				menuItemFiles.setMnemonic(KeyEvent.VK_F);
				menuBar.add(menuItemFiles);

				JMenuItem menuItemNewSudoku = new JMenuItem("New");
				menuItemNewSudoku.addActionListener(listener);
				menuItemNewSudoku.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
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
				menuItemlockCellsWithValues.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
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