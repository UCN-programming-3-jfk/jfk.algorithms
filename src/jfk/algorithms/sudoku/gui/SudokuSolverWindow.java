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

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import jfk.algorithms.sudoku.model.Sudoku;
import jfk.algorithms.sudoku.model.SudokuSolver;

public class SudokuSolverWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				SudokuSolverWindow window = new SudokuSolverWindow();
				window.setTitle("Sudoku solver");
				//window.setSize(900, 900);

//				 window.getRootPane().add(menuBar, BorderLayout.NORTH);

				window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				SudokuSolverPanel panel = new SudokuSolverPanel();
				panel.setSudoku(new Sudoku());
				window.add(panel);
				

				JMenuBar menuBar = createMenuBar(panel);
				window.setJMenuBar(menuBar);
				window.setVisible(true);
				window.setResizable(false);
				window.pack();
				KeyListener keyAdapter = new KeyAdapter() {
					public void keyPressed(KeyEvent evt) {
						if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
							System.exit(0);
						}
					}
				};
				window.addKeyListener(keyAdapter);

			}

			private JMenuBar createMenuBar(SudokuSolverPanel panel) {
				ActionListener listener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						switch (e.getActionCommand()) {
						case "Open":
							
							break;
							
							case "Save":
							
							break;
						case "Close":
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

				JMenuItem menuItemClose = new JMenuItem("Close");
				menuItemClose.addActionListener(listener);
				menuItemClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
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
