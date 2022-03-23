package jfk.algorithms.sudoku.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import jfk.algorithms.sudoku.model.Sudoku;

public class SudokuSolverWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	public static void main (String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				SudokuSolverWindow window = new SudokuSolverWindow();
				window.setTitle("Sudoku solver");
				window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
				window.pack();
				window.setVisible(true);
				window.setResizable(false);
				
				KeyListener keyAdapter=new KeyAdapter()
				 {
				  public void keyPressed(KeyEvent evt)
				  {
					  if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){System.exit(0);}
				  }
				 };
				window.addKeyListener(keyAdapter);
			}
		};

		EventQueue.invokeLater(runner);
	}

	
	public SudokuSolverWindow() {
		
		getRootPane().setLayout(new BorderLayout());
		SudokuSolverPanel panel = new SudokuSolverPanel();
		panel.setSudoku(new Sudoku());
		getRootPane().add(panel);
	}
	
}
