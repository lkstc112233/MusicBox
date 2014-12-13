package net.muststudio.musicbox.gui;

import java.util.ArrayDeque;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.musicbox.R;
import net.muststudio.musicbox.gui.FlashingBoardGuiItem.GridBoard.Grid;
import net.muststudio.musicbox.util.Waitter;
import net.muststudio.util.guiitemlib.ui.BlockedBackToRemoveGuiItemContainer;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;

public class FlashingBoardGuiItem extends BlockedBackToRemoveGuiItemContainer {
	private class SingleGrid extends GuiItem {
		private Grid gridHeld;
		private Paint paint = new Paint();

		public SingleGrid(Grid grid) {
			this.gridHeld = grid;
			paint.setColor(Color.WHITE);
			paint.setAlpha(0);
		}

		@Override
		public boolean checkState() {
			if (gridHeld.isFlashing())
				paint.setAlpha(255);
			else
				paint.setAlpha(Math.max(paint.getAlpha() - 20, 0));
			if (gridHeld.outOfSight())
				removeThis();
			return super.checkState();
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.drawRect(new RectF(gridHeld.x, gridHeld.y, gridHeld.x + gridHeld.size,
					gridHeld.y + gridHeld.size), paint);
		}
	}

	public class GridBoard {
		public class Grid {
			private boolean isFlashing;
			private int x;
			private int y;
			private int size;

			public Grid(int i, int j, int size) {
				x = i;
				y = j;
				this.size = size;
				isFlashing = false;
			}

			public boolean outOfSight() {
				if (x + size > ScreenInfo.getScreenInfo().getScreenWidth()
						|| y + size > ScreenInfo.getScreenInfo().getScreenHeight())
					return true;
				return false;
			}

			public boolean isFlashing() {
				boolean temp = isFlashing;
				isFlashing = false;
				return temp;
			}

			public void setFlashing() {
				this.isFlashing = true;
			}

			public void move() {
				x += 10;
				y += 5;
			}
		}

		private int width;
		private int height;
		private Grid[][] board;

		public GridBoard(int posx, int posy) {
			this(30, 30, posx, posy);
		}

		public GridBoard(int width, int height, int posx, int posy) {
			this.width = width;
			this.height = height;
			board = new Grid[width][height];
			int size = (int) (Math.random() * 7 + 5);
			for (int i = 0; i < width; ++i)
				for (int j = 0; j < height; ++j)
					board[i][j] = new Grid(i * (size + 3) + posx, j * (size + 3) + posy, size);
		}

		public Grid getGrid(int i, int j) {
			return board[i % width][j % height];
		}

		public void flash() {
			for (Grid[] gs : board)
				for (Grid g : gs) {
					if (Math.random() < 0.003)
						g.setFlashing();
					g.move();
				}
		}
	}

	private ArrayDeque<GridBoard> boards;
	private Waitter waitter = new Waitter(300);
	private MediaPlayer mediaPlayer;

	public FlashingBoardGuiItem() {
		super();
		mediaPlayer = MediaPlayer.create(MusicActivity.getActivity(), R.raw.hua_er_zi);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();

		this.backgroundColor = Color.BLACK;
		waitter.isOk();
		boards = new ArrayDeque<FlashingBoardGuiItem.GridBoard>();
	}

	@Override
	public void removeThis() {
		mediaPlayer.stop();
		super.removeThis();
	}

	@Override
	public boolean checkState() {
		if (Math.random() > 0.002) {
			GridBoard board;
			boards.add(board = new GridBoard((int) (Math.random()
					* ScreenInfo.getScreenInfo().getScreenWidth() - ScreenInfo.getScreenInfo()
					.getScreenWidth() / 2), (int) (Math.random()
					* ScreenInfo.getScreenInfo().getScreenHeight() - ScreenInfo.getScreenInfo()
					.getScreenHeight() / 2)));
			for (int i = 0; i < 30; i++)
				for (int j = 0; j < 30; j++)
					addToList(new SingleGrid(board.getGrid(i, j)));
		}
		if (waitter.isOk())
			boards.removeFirst();
		for (GridBoard board : boards)
			board.flash();
		return super.checkState();
	}
}
