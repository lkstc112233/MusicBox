package net.muststudio.musicbox;

import java.util.ArrayList;

import net.muststudio.musicbox.util.Waitter;

public final class MusicGrid {
	private SoundPlayer soundPlayer = new DullSoundPlayer();

	public class Cell {
		private boolean status;
		private boolean flashing;

		public boolean getStatus() {
			return status;
		}

		public void switchStatus() {
			status = !status;
		}

		public void flash() {
			flashing = true;
		}

		public boolean isFlashing() {
			boolean toReturn = flashing;
			flashing = false;
			return toReturn;
		}
	}

	private class CellBoard {
		private ArrayList<ArrayList<Cell>> m_board;
		private int width;
		private int height;

		public CellBoard(int width, int height) {
			this.width = width;
			this.height = height;
			m_board = new ArrayList<ArrayList<Cell>>();
			for (int i = 0; i < height; ++i) {
				ArrayList<Cell> temp = new ArrayList<Cell>();
				for (int j = 0; j < width; ++j)
					temp.add(new Cell());
				m_board.add(temp);
			}
		}

		public Cell getCell(int x, int y) {
			return m_board.get(y % height).get(x % width);
		}

		public Cell getCell(int i) {
			return getCell(i % height, i / height);
		}

		public ArrayList<Cell> getCellList(int y) {
			return m_board.get(y);
		}

		public int getHeight() {
			return height;
		}

		public int getSize() {
			return width * height;
		}
	}

	private CellBoard board;

	public MusicGrid() {
		this(20, 50);
	}

	public MusicGrid(int width, int height) {
		board = new CellBoard(width, height);
	}

	public Cell getCell(int x, int y) {
		return board.getCell(x, y);
	}

	public void setSoundPlayer(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	public void switchMusic(int musicNumberIn) {
		ArrayList<Boolean> boolList = new ArrayList<Boolean>();
		int musicStep = musicNumberIn & 0xf;
		musicNumberIn = musicNumberIn >> 4;
		String musicString = Integer.toBinaryString(musicNumberIn);
		for (int i = 27; i >= 0; --i) {
			if (i >= musicString.length())
				boolList.add(false);
			else if (musicString.charAt(i) == '1')
				boolList.add(true);
			else
				boolList.add(false);
		}
		int swiper = 0;
		for (int i = 0; i < board.getSize(); ++i) {
			swiper += musicStep;
			swiper %= 28;
			if (boolList.get(swiper))
				board.getCell(i).switchStatus();
		}

	}

	private int i = 0;
	private Waitter waitter = new Waitter(15);

	public void playFunc() {
		if (!waitter.isOk())
			return;
		i = (i + 1) % board.getHeight();
		ArrayList<Cell> l = board.getCellList(i);
		ArrayList<Integer> il = new ArrayList<Integer>();
		int cou = 0;
		for (Cell c : l)
			if (c.status) {
				c.flash();
				il.add(cou++);
			} else
				++cou;
		int[] ia = new int[il.size()];
		for (int j = 0; j < ia.length; ++j)
			ia[j] = il.get(j);
		soundPlayer.playSoundList(ia);
	}

	public int getWidth() {
		return board.width;
	}

	public void lineChange(boolean[] line, int j) {
		for (int i = 0; i < line.length; ++i)
			if (line[i])
				board.getCell(i, j).switchStatus();
	}
}
