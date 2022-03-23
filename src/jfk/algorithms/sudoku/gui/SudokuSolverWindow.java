package jfk.algorithms.sudoku.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
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

					private void openSudoku() {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
						fileChooser.setAcceptAllFileFilterUsed(false);
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSudoku files", "jsudoku"));
						int result = fileChooser.showOpenDialog(window);
						if (result == JFileChooser.APPROVE_OPTION) {
						    File selectedFile = fileChooser.getSelectedFile();
						    try {
						    int[][] values = fileToValues(selectedFile);
						    panel.getSudoku().setValues(values);
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

				// window.getRootPane().setLayout(new BorderLayout());
				JMenuBar menuBar = new JMenuBar();

				JMenu menuItemFiles = new JMenu("Files");
				menuItemFiles.setMnemonic(KeyEvent.VK_F);
				menuBar.add(menuItemFiles);

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

				JMenu menuItemSudoku = new JMenu("Sudoku");
				menuItemSudoku.setMnemonic(KeyEvent.VK_S);
				menuBar.add(menuItemSudoku);

				JMenuItem menuItemNewSudoku = new JMenuItem("New");
				menuItemNewSudoku.addActionListener(listener);
				menuItemNewSudoku.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
				menuItemSudoku.add(menuItemNewSudoku);

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

	public SudokuSolverWindow() {

	}

}
