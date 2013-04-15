package com.xmine;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MineField {
	public int[][] mineArray;
	public int size;

	MineField(int mineNum, int row, int col, int size) {
		this.size = size;
		mineArray = new int[size][size];
		setMine(mineNum, row, col, size);
		setMineNum();
	}

	private void setMine(int mineNum, int Clickedcol, int Clickedrow, int size) {
		int col = 0, row = 0, i = 0;
		while (i < mineNum) {
			col = (int) (Math.random() * 10000) % (size - 1);
			row = (int) (Math.random() * 10000) % (size - 1);
			// The first click mine block can't be mine
			if (row == Clickedrow && col == Clickedcol)
				continue;
			if (mineArray[col][row] == 0) {
				mineArray[col][row] = 9;
				i++;
			}
		}
	}

	private void setMineNum() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				mineArray[i][j] = mineArray[i][j] == 9 ? 9 : checkMineNum(i, j);
			}
		}
	}

	private int checkMineNum(int col, int row) {
		int top, bottom, left, right, count = 0;
		top = col - 1 > 0 ? col - 1 : 0;
		bottom = col + 1 < size ? col + 1 : size - 1;
		left = row - 1 > 0 ? row - 1 : 0;
		right = row + 1 < size ? row + 1 : size - 1;
		for (int i = top; i <= bottom; i++) {
			for (int j = left; j <= right; j++) {
				if (mineArray[i][j] == 9)
					count++;
			}
		}
		return (count);
	}

}

class MineBlock extends JButton {

	private static final long serialVersionUID = 1L;
	private int col;
	private int row;
	private int flag = 0;
	private boolean clickedFlag = false;

	MineBlock(int row, int col, ImageIcon icon) {
		super(icon);
		this.row = row;
		this.col = col;
	}

	public boolean getClickedFlag() {
		return (clickedFlag);
	}

	public void setClickedFlag(boolean toSet) {
		clickedFlag = toSet;
	}

	public int getCol() {
		return (col);
	}

	public int getRow() {
		return (row);
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getFlag() {
		return (flag);
	}
}
