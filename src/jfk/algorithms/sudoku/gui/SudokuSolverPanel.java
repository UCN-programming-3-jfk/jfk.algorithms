package jfk.algorithms.sudoku.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jfk.algorithms.sudoku.model.*;

public class SudokuSolverPanel extends JPanel {

	private Point selectedCell = null;
	private static final long serialVersionUID = 1L;

	private Sudoku sudoku = null;

	@Override
	public void paint(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		float f = 55.0f;
		g.setFont(g.getFont().deriveFont(f));
		int tileSize = getWidth() / 9;
		
		markErrorsIfAny(g, tileSize);

		
		for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
			for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
				g.setColor(Color.black);
				
				if (getSudoku().isLocked(columnCounter, rowCounter)) {
					g.setColor(Color.black);
					g.fillRect(columnCounter * tileSize, rowCounter * tileSize, tileSize, tileSize);
					g.setColor(Color.white);
				}
				
				g.drawRect(columnCounter * tileSize, rowCounter * tileSize, tileSize, tileSize);
				if (getSudoku().getValues()[columnCounter][rowCounter] != 0) {
					g.drawString(getSudoku().getValues()[columnCounter][rowCounter] + "",
							columnCounter * tileSize + tileSize / 3, rowCounter * tileSize + tileSize - tileSize/4);
				}
			}
		}
		g.setColor(Color.black);
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setStroke(new BasicStroke(7));
		for (int rowCounter = 0; rowCounter < 3; rowCounter++) {
			for (int columnCounter = 0; columnCounter < 3; columnCounter++) {
				g.drawRect(columnCounter * tileSize * 3, rowCounter * tileSize * 3, tileSize * 3, tileSize * 3);
			}
		}
		if (getSelectedCell() != null) {
			Point selectedCell = getSelectedCell();
			g.setColor(Color.red);
			g.drawRect(selectedCell.x * tileSize, selectedCell.y * tileSize, tileSize, tileSize);
		}
		graphics2d.setStroke(new BasicStroke(1));
	}

	private void markErrorsIfAny(Graphics g, int tileSize) {
		g.setColor(Color.yellow);
		for (int rowCounter = 0; rowCounter < 9; rowCounter++) {			
			if(!getSudoku().isRowValid(rowCounter)) {
				g.fillRect(0, rowCounter * tileSize, tileSize*9, tileSize);
			}
		}

		for (int columnCounter = 0; columnCounter < 9; columnCounter++) {			
			if(!getSudoku().isColumnValid(columnCounter)) {
				g.fillRect(columnCounter * tileSize, 0, tileSize, tileSize*9);
			}
		}
		
		for (int quadrantYCounter = 0; quadrantYCounter < 3; quadrantYCounter++) {
			for (int quadrantXCounter = 0; quadrantXCounter < 3; quadrantXCounter++) {
				if(!getSudoku().isQuadrantValid(quadrantXCounter, quadrantYCounter)) {
					g.fillRect(quadrantXCounter * tileSize*3, quadrantYCounter * tileSize * 3, tileSize * 3, tileSize*3);
				}
			}
		}
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public void setSudoku(Sudoku sudoku) {
		this.sudoku = sudoku;
	}

	public SudokuSolverPanel() {
		setMinimumSize(new Dimension(720, 720));
		setPreferredSize(getMinimumSize());

		MouseAdapter adapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				Point cellClicked = getCellFromClick(e.getPoint());

				if (cellClicked.x >= 0 && cellClicked.x < 9 && cellClicked.y >= 0 && cellClicked.y < 9) {

					if (e.isShiftDown() && getSudoku().getValues()[cellClicked.x][cellClicked.y] > 0) {
						setSelectedCell(cellClicked);
						sudoku.toggleCellLock(cellClicked.x, cellClicked.y);
						repaint();
						return;
					}
					if (getSudoku().isLocked(cellClicked.x, cellClicked.y)) {
						return;
					}
					int newValue = getSudoku().getValues()[cellClicked.x][cellClicked.y];

					switch (e.getButton()) {

					case MouseEvent.BUTTON1:

						if (cellClicked.equals(getSelectedCell())) {
							newValue++;
							if (newValue > 9) {
								newValue = 1;
							}
							getSudoku().getValues()[cellClicked.x][cellClicked.y] = newValue;
							break;
						}
						setSelectedCell(cellClicked);
						break;

					case MouseEvent.BUTTON2:
						SudokuSolver.solve(getSudoku());
						repaint();
						break;
					case MouseEvent.BUTTON3:
						if (cellClicked.equals(getSelectedCell())) {
							newValue--;
							if (newValue < 1) {
								newValue = 9;
							}
							getSudoku().getValues()[cellClicked.x][cellClicked.y] = newValue;
							break;
						}
						setSelectedCell(cellClicked);
					}

					repaint();
				}

			}

			private Point getCellFromClick(Point point) {
				return new Point(point.x / (getWidth() / 9), point.y / (getHeight() / 9));
			}
		};
		addMouseListener(adapter);

		KeyAdapter keyAdapter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (getSelectedCell() != null) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_DELETE:
						setSelectedCellsValue(0);
						break;
					case KeyEvent.VK_1:
						setSelectedCellsValue(1);
						break;
					case KeyEvent.VK_2:
						setSelectedCellsValue(2);
						break;
					case KeyEvent.VK_3:
						setSelectedCellsValue(3);
						break;
					case KeyEvent.VK_4:
						setSelectedCellsValue(4);
						break;
					case KeyEvent.VK_5:
						setSelectedCellsValue(5);
						break;
					case KeyEvent.VK_6:
						setSelectedCellsValue(6);
						break;
					case KeyEvent.VK_7:
						setSelectedCellsValue(7);
						break;
					case KeyEvent.VK_8:
						setSelectedCellsValue(8);
						break;
					case KeyEvent.VK_9:
						setSelectedCellsValue(9);
						break;
					case KeyEvent.VK_LEFT:
						if (getSelectedCell().x > 0) {
							setSelectedCell(new Point(getSelectedCell().x - 1, getSelectedCell().y));
						}
						break;

					case KeyEvent.VK_RIGHT:
						if (getSelectedCell().x < 8) {
							setSelectedCell(new Point(getSelectedCell().x + 1, getSelectedCell().y));
						}
						break;
					case KeyEvent.VK_UP:
						if (getSelectedCell().y > 0) {
							setSelectedCell(new Point(getSelectedCell().x, getSelectedCell().y - 1));
						}
						break;

					case KeyEvent.VK_DOWN:
						if (getSelectedCell().y < 8) {
							setSelectedCell(new Point(getSelectedCell().x, getSelectedCell().y + 1));

							break;
						}
						
					};
				};
				repaint();
			}
		};

		addKeyListener(keyAdapter);
		setFocusable(true);
		requestFocusInWindow();
	}

	private void setSelectedCellsValue(int value) {
		getSudoku().setValue(getSelectedCell().x, getSelectedCell().y, value);
	}

	public Point getSelectedCell() {
		return selectedCell;
	}

	public void setSelectedCell(Point selectedCell) {
		if (selectedCell.x >= 0 && selectedCell.x < 9 && selectedCell.y >= 0 && selectedCell.y < 9) {
			this.selectedCell = selectedCell;
		}
	}
}