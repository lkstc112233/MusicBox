package net.muststudio.musicbox;

import net.muststudio.musicbox.util.Decounter;

public class MusicBallBox {
	private SoundPlayer soundPlayer = new DullSoundPlayer();

	public class Block {
		private boolean flashing;
		private int x;
		private int y;
		private int id;

		public Block(int x, int y, int id) {
			this.x = x;
			this.y = y;
			this.id = id;
		}

		public boolean isCollided(Ball ball) {
			if ((x - ball.x) * (x - ball.x) + (y - ball.y) * (y - ball.y) < ball.radius + 0.5)
				return true;
			return false;
		}

		public void playSound() {
			soundPlayer.playSoundId(id);
			flash();
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

		public void refresh() {
			final float factor = 0.3f;
			final float factor2 = 0.03f;
			x = -MusicActivity.getActivity().phoneAccelerometerValues[0] * factor;
			y = MusicActivity.getActivity().phoneAccelerometerValues[1] * factor;
			x += MusicActivity.getActivity().sensorAccelerometerValues.x * factor2;
			y += MusicActivity.getActivity().sensorAccelerometerValues.y * factor2;
		}
	}

	private class Velo {
		public float x;
		public float y;

		public void add(Grav g) {
			x += g.x;
			y += g.y;
		}

		public void sub() {
			x *= 0.95;
			y *= 0.95;
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

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}

		public Ball(int x, int y) {
			this.x = x;
			this.y = y;
			g = new Grav();
			v = new Velo();
		}

		public void move() {
			g.refresh();
			x += v.x + g.x / 2;
			y += v.y + g.y / 2;
			v.add(g);
			v.sub();
		}

		public void swapX(float x) {
			v.swapX();
			this.x = 2 * x - this.x;
		}

		public void swapY(float y) {
			v.swapY();
			this.y = 2 * y - this.y;
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
				x1[i] = new Block(i, 0, n++ % 21);
				x2[i] = new Block(i, height - 1, n++ % 21);
			}
			for (int i = 0; i < height; ++i) {
				y1[i] = new Block(0, i, n++ % 21);
				y2[i] = new Block(width - 1, i, n++ % 21);
			}
		}

		public Ball getBall() {
			return ball;
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
		//
		// public int getHeight() {
		// return height;
		// }
		//
		// public int getWidth() {
		// return width;
		// }
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

	private Decounter waitter = new Decounter(15);

	public void playFunc() {
		board.move();
		waitter.count();
		for (int i = 0; i < getWidth(); ++i) {
			if (board.getCell(0, i).isCollided(board.getBall()))
				if (waitter.isOk()) {
					board.getCell(0, i).playSound();
					return;
				}
			if (board.getCell(1, i).isCollided(board.getBall()))
				if (waitter.isOk()) {
					board.getCell(1, i).playSound();
					return;
				}
		}
		for (int i = 0; i < getHeight(); ++i) {
			if (board.getCell(2, i).isCollided(board.getBall()))
				if (waitter.isOk()) {
					board.getCell(2, i).playSound();
					return;
				}
			if (board.getCell(3, i).isCollided(board.getBall()))
				if (waitter.isOk()) {
					board.getCell(3, i).playSound();
					return;
				}
		}
	}

	public int getWidth() {
		return board.width;
	}

	public int getHeight() {
		return board.height;
	}

	public Ball getBall() {
		return board.getBall();
	}
}
