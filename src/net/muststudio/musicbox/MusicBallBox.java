package net.muststudio.musicbox;

import java.util.ArrayList;

import net.muststudio.musicbox.util.Waitter;

public class MusicBallBox {
	private SoundPlayer soundPlayer = new DullSoundPlayer();

	public class Block {
		private boolean flashing;
		private int x;
		private int y;
		private int id;

		public Block(int id) {
			this.id = id;
		}

		public boolean isCollided(Ball ball) {
			if ((x - ball.x) * (x - ball.x) + (y - ball.y) * (y - ball.y) < ball.radius + 0.5)
				return true;
			return false;
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

	private class Grav {
		public float x;
		public float y;
	}

	private class Velo {
		public float x;
		public float y;

		public void add(Grav g) {
			x += g.x;
			y += g.y;
		}

		public void swapX() {
			x = -x;
		}

		public void swapY() {
			y = -y;
		}
	}

	public class Ball {
		private float x;
		private float y;
		private float radius;
		private Grav g;
		private Velo v;

		public Ball() {
			this(3, 3);

		}

		public Ball(int x, int y) {
			this.x = x;
			this.y = y;
			g = new Grav();
			v = new Velo();
		}

		public void move() {
			g.x = MusicActivity.getActivity().phoneAccelerometerValues[0];
			g.y = MusicActivity.getActivity().phoneAccelerometerValues[1];
			x += v.x + g.x / 2;
			y += v.y + g.y / 2;
			v.add(g);
		}

		public void swapX(float x) {
			v.swapX();
		}

		public void swapY(float y) {
			v.swapY();
		}
	}

	private class BallBoard {
		private Block[] x1;
		private Block[] x2;
		private Block[] y1;
		private Block[] y2;
		private Ball ball;
		private int width;
		private int height;

		public BallBoard(int width, int height) {
			this.width = width;
			this.height = height;
			x1 = new Block[width];
			x2 = new Block[width];
			y1 = new Block[height];
			y2 = new Block[height];
			ball = new Ball(width / 2, height / 2);
			int n = 0;
			for (int i = 0; i < width; ++i) {
				x1[i] = new Block(n++ % 21);
				x2[i] = new Block(n++ % 21);
			}
			for (int i = 0; i < height; ++i) {
				y1[i] = new Block(n++ % 21);
				y2[i] = new Block(n++ % 21);
			}
		}

		public void move() {
			ball.move();
			if (ball.x < 0)
				if (ball.v.x < 0)
					ball.swapX(0);
			if (ball.y < 0)
				if (ball.v.y < 0)
					ball.swapY(0);
			if (ball.x > width)
				if (ball.v.x > 0)
					ball.swapX(width);
			if (ball.y > height)
				if (ball.v.y > 0)
					ball.swapY(height);

		}

		public Block getCell(int set, int pos) {
			switch (set % 4) {
			case 0:
				return x1[pos];
			case 1:
				return x2[pos];
			case 2:
				return y1[pos];
			case 3:
				return y2[pos];
			default:
				break;
			}
			return null;
		}

		public Block getCell(int i) {
			return getCell(i % height, i / height);
		}

		public int getHeight() {
			return height;
		}

		public int getWidth() {
			return width;
		}
	}

	private BallBoard board;

	public MusicBallBox() {
		this(20, 50);
	}

	public MusicBallBox(int width, int height) {
		board = new BallBoard(width, height);
	}

	public Block getCell(int x, int y) {
		return board.getCell(x, y);
	}

	public void setSoundPlayer(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	private int i = 0;
	private Waitter waitter = new Waitter(15);

	public void playFunc() {
		board.move();
		if (!waitter.isOk())
			return;
		i = (i + 1) % board.getHeight();
		ArrayList<Integer> il = new ArrayList<Integer>();
	}

	public int getWidth() {
		return board.width;
	}

	public int getHeight() {
		return board.height;
	}
}
