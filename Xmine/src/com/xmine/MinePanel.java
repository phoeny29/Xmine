package com.xmine;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MinePanel extends JFrame implements MouseListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int easyMineNum = 10;
	private static final int normalMineNum = 30;
	private static final int hardMineNum = 90;
	private static int mineNum;
	private static final int easySize = 10;
	private static final int normalSize = 15;
	private static final int hardSize = 19;
	private static int mineFieldSize;
	private static int gameLevel;
	private static int remainMineNum;

	private JMenu menuGame;
	private JMenuItem easyGame;
	private JMenuItem middleGame;
	private JMenuItem hardGame;
	private JMenuItem exitGame;
	private JMenu menuHelp;
	private JMenuItem aboutGame;
	private JPanel controlPane;
	private MineBlock[][] mineBlock;
	private ImageIcon blank;
	private GridBagConstraints constraints;
	private GridBagLayout gridbag;

	private About about;
	private MineField mineField;
	private Icon[] mineNumIcon;
	private boolean gameStarted = false;

	private ImageIcon flag;
	private ImageIcon mine;
	private ImageIcon uncertain;
	private ImageIcon wrongflag;
	private ImageIcon bang;
	private ImageIcon restart;

	public int numFlaged;
	private JButton restartButton;
	private JPanel subPane;
	private JLabel timeLabel;
	private Timer timer;
	private JLabel remainMineLabel;
	private JMenuBar menuBar;

	public MinePanel() throws InterruptedException {
		super("Xmine by Xin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameLevel = 1;
		remainMineNum = easyMineNum;
		loadGameImages();
		drawGamePanel();
		drawMineBlocks(1);

	}

	// load all image to create image buttons
	public void loadGameImages() {
		String[] imageName = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"blank", "flag", "uncertain", "mine", "wrongflag", "bang",
				"restart" };
		Image[] images = new BufferedImage[imageName.length];
		mineNumIcon = new ImageIcon[20];
		try {
			for (int i = 0; i < imageName.length; i++) {
				images[i] = ImageIO.read(this.getClass().getClassLoader()
						.getResource("images/" + imageName[i] + ".gif"));
				mineNumIcon[i] = new ImageIcon(images[i]);
			}
			blank = new ImageIcon(images[9]);
			flag = new ImageIcon(images[10]);
			uncertain = new ImageIcon(images[11]);
			mine = new ImageIcon(images[12]);
			wrongflag = new ImageIcon(images[13]);
			bang = new ImageIcon(images[14]);
			restart = new ImageIcon(images[15]);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// draw the game panel, except for mines
	public void drawGamePanel() {
		Insets space = new Insets(0, 0, 0, 0);
		menuBar = new JMenuBar();
		menuGame = new JMenu("Game");
		menuHelp = new JMenu("Help");
		menuBar.add(menuGame);
		menuBar.add(menuHelp);

		easyGame = new JMenuItem("Easy");
		easyGame.addActionListener(this);
		middleGame = new JMenuItem("Middle");
		middleGame.addActionListener(this);
		hardGame = new JMenuItem("Hard");
		hardGame.addActionListener(this);
		exitGame = new JMenuItem("Exit");
		exitGame.addActionListener(this);
		aboutGame = new JMenuItem("About...");
		aboutGame.addActionListener(this);

		menuGame.add(easyGame);
		menuGame.add(middleGame);
		menuGame.add(hardGame);
		menuGame.add(exitGame);
		menuHelp.add(aboutGame);

		this.setJMenuBar(menuBar);
		about = new About("About Xmine");

		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		gridbag = new GridBagLayout();
		controlPane = new JPanel();
		controlPane.setLayout(gridbag);
		subPane = new JPanel();

		restartButton = new JButton(restart);
		restartButton.setSize(30, 30);
		restartButton.setMargin(space);
		restartButton.addMouseListener(this);

		timeLabel = new JLabel();
		timeLabel.setText("Mine  ");
		subPane.add(timeLabel);
		subPane.add(restartButton);
		remainMineLabel = new JLabel();
		remainMineLabel.setText("  Sweeper");
		subPane.add(remainMineLabel);

		setConstraints(constraints, 0, 0, 15, 2, 2, 2);
		gridbag.setConstraints(subPane, constraints);
		controlPane.add(subPane);
		setContentPane(controlPane);
		setLocation(500, 100);
		setVisible(true);

	}

	void setConstraints(GridBagConstraints gbConstraintsonstraints, int x,
			int y, int w, int h, int weightx, int weighty) {
		gbConstraintsonstraints.gridx = x;
		gbConstraintsonstraints.gridy = y;
		gbConstraintsonstraints.gridwidth = w;
		gbConstraintsonstraints.gridheight = h;
		gbConstraintsonstraints.weightx = weightx;
		gbConstraintsonstraints.weighty = weighty;
	}

	// draw mine field, according to the game level
	public void drawMineBlocks(int level) {
		switch (level) {
		case 1:
			gameStarted = false;
			setSize(275, 370);
			gameLevel = 1;
			mineNum = easyMineNum;
			mineFieldSize = easySize;
			mineBlock = new MineBlock[easySize][easySize];
			for (int i = 0; i < easySize; i++) {
				for (int j = 0; j < easySize; j++) {

					mineBlock[i][j] = new MineBlock(i, j, blank);
					mineBlock[i][j].addMouseListener(this);
					setConstraints(constraints, j, i + 5, 1, 1, 1, 1);
					gridbag.setConstraints(mineBlock[i][j], constraints);
					controlPane.add(mineBlock[i][j]);
				}
			}
			setContentPane(controlPane);
			break;
		case 2:
			gameStarted = false;
			setSize(420, 520);
			gameLevel = 2;
			mineNum = normalMineNum;
			mineFieldSize = normalSize;
			mineBlock = new MineBlock[normalSize][normalSize];
			for (int i = 0; i < normalSize; i++) {
				for (int j = 0; j < normalSize; j++) {

					mineBlock[i][j] = new MineBlock(i, j, blank);
					mineBlock[i][j].addMouseListener(this);
					setConstraints(constraints, j, i + 3, 1, 1, 1, 1);
					gridbag.setConstraints(mineBlock[i][j], constraints);
					controlPane.add(mineBlock[i][j]);
				}
			}
			setContentPane(controlPane);
			break;
		case 3:
			gameStarted = false;
			setSize(510, 600);
			gameLevel = 3;
			mineNum = hardMineNum;
			mineFieldSize = hardSize;
			mineBlock = new MineBlock[hardSize][hardSize];
			for (int i = 0; i < hardSize; i++) {
				for (int j = 0; j < hardSize; j++) {

					mineBlock[i][j] = new MineBlock(i, j, blank);
					mineBlock[i][j].addMouseListener(this);
					setConstraints(constraints, j, i + 3, 1, 1, 1, 1);
					gridbag.setConstraints(mineBlock[i][j], constraints);
					controlPane.add(mineBlock[i][j]);
				}
			}
			setContentPane(controlPane);
			break;
		}
	}

	// When changing a game difficulty level, delete mine buttons and then draw
	// new ones
	public void deleteMineBlocks() {
		for (int i = 0; i < mineFieldSize; i++) {
			for (int j = 0; j < mineFieldSize; j++) {
				controlPane.remove(mineBlock[i][j]);
			}
		}
		setContentPane(controlPane);
	}

	// Button events handling
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == easyGame) {
			if (gameStarted)
				timer.stop();
			remainMineNum = easyMineNum;
			remainMineLabel.setText("Mines Remain: " + remainMineNum);
			deleteMineBlocks();
			drawMineBlocks(1);
			return;
		}
		if (e.getSource() == middleGame) {
			if (gameStarted)
				timer.stop();
			remainMineNum = normalMineNum;
			remainMineLabel.setText("Mines Remain: " + remainMineNum);
			deleteMineBlocks();
			drawMineBlocks(2);
			return;
		}
		if (e.getSource() == hardGame) {
			if (gameStarted)
				timer.stop();
			remainMineNum = hardMineNum;
			remainMineLabel.setText("Mines Remain: " + remainMineNum);
			deleteMineBlocks();
			drawMineBlocks(3);
			return;
		}
		if (e.getSource() == exitGame) {
			System.exit(0);
		}
		if (e.getSource() == aboutGame) {
			about.setVisible(true);
		}

	}

	// Mouse click event handling
	@Override
	public void mouseClicked(MouseEvent e) {
		// restart button, restart current level
		if (e.getSource() == restartButton) {
			if (gameStarted)
				timer.stop();
			deleteMineBlocks();
			drawMineBlocks(gameLevel);
			switch (gameLevel) {
			case 1:
				remainMineNum = easyMineNum;
				break;
			case 2:
				remainMineNum = normalMineNum;
				break;
			case 3:
				remainMineNum = hardMineNum;

			}
			remainMineLabel.setText("Mines Remain: " + remainMineNum);
			return;
		}

		int row, col;
		row = ((MineBlock) e.getSource()).getRow();
		col = ((MineBlock) e.getSource()).getCol();

		if (!gameStarted) {
			newGame(mineNum, row, col);
			timer = new Timer(timeLabel);
			timer.start();
			remainMineLabel.setText("Mines Remain: " + remainMineNum);
		}

		if (e.getModifiers() == (InputEvent.BUTTON2_MASK)) {
			sweepAround(row, col);
		}

		if (!mineBlock[row][col].getClickedFlag()) {

			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				if (mineBlock[row][col].getFlag() == 1) {
					return;
				} else {
					// System.out.println("You clicked:" + row + ",col:" + col);
					openBlock(row, col);
				}
			} else if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
				flagMine(row, col);
			}

		}
	}

	private void newGame(int num, int row, int col) {
		mineField = new MineField(num, row, col, mineFieldSize);
		gameStarted = true;
	}

	void flagMine(int row, int col) {
		System.out.println((mineBlock[row][col].getFlag() + 1) % 3);
		mineBlock[row][col].setFlag((mineBlock[row][col].getFlag() + 1) % 3);
		if (mineBlock[row][col].getFlag() == 2) {
			remainMineNum++;
			remainMineLabel.setText("Mines Remain: " + remainMineNum);
		} else if (mineBlock[row][col].getFlag() == 1) {
			remainMineNum--;
			remainMineLabel.setText("Mines Remain: " + remainMineNum);
		}
		showFlagImage(row, col);
		if (winCheck()) {
			timer.stop();
			remainMineLabel.setText("  YOU WIN ! ");
		}
	}

	void showFlagImage(int row, int col) {
		int flagIndicator = mineBlock[row][col].getFlag();
		switch (flagIndicator) {
		case 0:
			mineBlock[row][col].setIcon(blank);
			break;
		case 1:
			mineBlock[row][col].setIcon(flag);
			break;
		case 2:
			mineBlock[row][col].setIcon(uncertain);

		}
	}

	void openBlock(int row, int col) {
		int i, j;
		i = row < 0 ? 0 : row;
		i = i > mineFieldSize - 1 ? mineFieldSize - 1 : i;
		j = col < 0 ? 0 : col;
		j = j > mineFieldSize - 1 ? mineFieldSize - 1 : j;

		System.out.println("Check Mine row:" + i + ",col:" + j);
		if (mineField.mineArray[i][j] == 9) {
			onMine(i, j);
		} else if (mineField.mineArray[i][j] == 0
				&& mineBlock[i][j].getClickedFlag() == false) {
			mineBlock[i][j].setClickedFlag(true);
			showBlock(i, j);
			for (int m = i - 1; m <= i + 1; m++)
				for (int n = j - 1; n <= j + 1; n++)
					openBlock(m, n);

		} else {
			showBlock(i, j);
			mineBlock[i][j].setClickedFlag(true);
		}
		if (winCheck()) {
			timer.stop();
			remainMineLabel.setText("  YOU WIN ! ");
			return;
		}
	}

	// Use Mouse middle button to sweep all blocks around
	private void sweepAround(int row, int col) {
		int top, bottom, left, right;
		top = row - 1 > 0 ? row - 1 : 0;
		bottom = row + 1 < mineFieldSize ? row + 1 : mineFieldSize - 1;
		left = col - 1 > 0 ? col - 1 : 0;
		right = col + 1 < mineFieldSize ? col + 1 : mineFieldSize - 1;

		for (int i = top; i <= bottom; i++) {
			for (int j = left; j <= right; j++) {
				if (mineBlock[i][j].getFlag() != 1)
					openBlock(i, j);
			}
		}

	}

	private boolean winCheck() {
		for (int i = 0; i < mineFieldSize; i++) {
			for (int j = 0; j < mineFieldSize; j++) {
				if (mineField.mineArray[i][j] == 9
						&& mineBlock[i][j].getFlag() != 1) {
					return (false);
				}
				if (mineField.mineArray[i][j] != 9
						&& mineBlock[i][j].getFlag() == 1) {
					return (false);
				}
				if (mineField.mineArray[i][j] != 9
						&& mineBlock[i][j].getClickedFlag() == false) {
					return (false);
				}
			}
		}
		return (true);
	}

	private void onMine(int row, int col) {

		timer.stop();
		remainMineLabel.setText(" BOMMMMMMM!  ");
		for (int i = 0; i < mineFieldSize; i++) {
			for (int j = 0; j < mineFieldSize; j++) {
				mineBlock[i][j].setIcon(mineNumIcon[0]);
				int isMine;
				isMine = mineField.mineArray[i][j] != 9 ? 0 : 1;
				mineBlock[i][j].setClickedFlag(true);
				if (isMine == 1 && (i != row || j != col)) {
					if (mineBlock[i][j].getFlag() == 1) {
						mineBlock[i][j].setIcon(flag);
						mineBlock[i][j].setClickedFlag(true);
					} else {
						mineBlock[i][j].setIcon(mine);
						mineBlock[i][j].setClickedFlag(true);
					}
				} else if (isMine == 1 && (i == row && j == col)) {
					mineBlock[i][j].setIcon(bang);
					mineBlock[i][j].setClickedFlag(true);
				} else if (isMine == 0 && mineBlock[i][j].getFlag() == 1) {
					mineBlock[i][j].setIcon(wrongflag);
					mineBlock[i][j].setClickedFlag(true);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	void win() {
		timer.stop();
		remainMineLabel.setText("  YOU WIN ! ");

	}

	void showBlock(int row, int col) {
		int num;
		num = mineField.mineArray[row][col];
		if (num != 0) {
			mineBlock[row][col].setIcon(mineNumIcon[num]);
			mineBlock[row][col].setClickedFlag(true);
		} else {
			mineBlock[row][col].setEnabled(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}

// Timer object, refresh every 0.1 s
class Timer extends Thread {

	int count;
	double time;
	JLabel j;

	public Timer(JLabel j) {
		this.count = 0;
		this.time = 0.0;
		this.j = j;
	}

	public void run() {
		while (true) {
			try {
				sleep(100);
				count = count + 1;
				time = (double) count / 10.0;
				j.setText("Time Used: " + time);
			} catch (InterruptedException e) {
			}
		}
	}

}

// Frame when clicking "about" in Menu bar
class About extends JFrame implements MouseListener {
	private static final long serialVersionUID = 1L;
	private JPanel about;
	private JLabel msg;
	private JButton back;

	public About(String title) {
		super(title);
		setSize(400, 100);
		about = new JPanel();
		msg = new JLabel("A Mine Sweeper Game by Xin Du.Please Enjoy!");
		back = new JButton("back");
		back.addMouseListener(this);
		about.add(msg);
		about.add(back);
		setContentPane(about);
		setLocation(500, 200);
	}

	public void mouseClicked(MouseEvent e) {
		this.setVisible(false);
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

}
