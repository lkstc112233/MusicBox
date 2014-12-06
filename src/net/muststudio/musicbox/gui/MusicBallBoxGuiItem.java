package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import net.muststudio.musicbox.MusicBallBox;
import net.muststudio.musicbox.MusicBallBox.Ball;
import net.muststudio.musicbox.MusicBallBox.Block;
import net.muststudio.musicbox.SoundMaker;
import net.muststudio.util.guiitemlib.ui.BlockedBackToRemoveGuiItemContainer;
import net.muststudio.util.guiitemlib.ui.GuiItemContainer;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public final class MusicBallBoxGuiItem extends BlockedBackToRemoveGuiItemContainer {
	private class MusicBallBoxContainer extends SquareGuiItem {
		private int waitter2 = 0;

		private class BallContainer extends SquareGuiItem {
			private Ball ball;
			private Paint paintW = new Paint();

			public BallContainer(RelativePoint left_up, RelativePoint right_bottom, Ball cell) {
				super(left_up, right_bottom);
				this.ball = cell;
				paintW.setColor(Color.LTGRAY);
			}

			@Override
			public void draw(Canvas canvas) {
				// TODO
				canvas.drawCircle(guiItemSquareRectF.centerX(), guiItemSquareRectF.centerY(),
						MusicBallBoxContainer.this.guiItemSquareRectF.width() / 21, paintW);
			}

			@Override
			public boolean checkState() {
				synchronized (this) {
					if (--waitter2 < 0)
						waitter2 = 0;
				}
				if (cell.isFlashing()) {
					addTo(new CellFlasherGuiItem(mainPosition, subPosition));
					addTo(new CellWaveGuiItem(mainPosition, subPosition));
				}
				return true;
			}
		}

		private class BlockContainer extends SquareGuiItem {
			private Block cell;
			private Paint paintW = new Paint();

			public BlockContainer(RelativePoint left_up, RelativePoint right_bottom, Block cell) {
				super(left_up, right_bottom);
				this.cell = cell;
				paintW.setColor(Color.GRAY);
			}

			@Override
			public void draw(Canvas canvas) {
				canvas.drawRect(guiItemSquareRect, paintW);
			}

			@Override
			public boolean checkState() {
				synchronized (this) {
					if (--waitter2 < 0)
						waitter2 = 0;
				}
				if (cell.isFlashing()) {
					addTo(new CellFlasherGuiItem(mainPosition, subPosition));
					addTo(new CellWaveGuiItem(mainPosition, subPosition));
				}
				return true;
			}
		}

		public MusicBallBoxContainer(RelativePoint left_up, RelativePoint right_bottom) {
			super(left_up, right_bottom);
			int height;
			final int width = 21;
			musicBox = new MusicBallBox(width, height = (int) (guiItemSquareRectF.height()
					/ guiItemSquareRectF.width() * width));
			musicBox.setSoundPlayer(new SoundMaker());
			container = new GuiItemContainer();
			for (int i = 0; i < width; ++i)
				container.addToList(new BlockContainer(RelativePoint.getRelativePoint(
						guiItemSquareRectF.left + i * guiItemSquareRectF.width() / width,
						guiItemSquareRectF.top), RelativePoint.getRelativePoint(
						guiItemSquareRectF.left + (i + 1) * guiItemSquareRectF.width() / width,
						guiItemSquareRectF.top + guiItemSquareRectF.height() / height), musicBox
						.getCell(0, i)));
			for (int i = 0; i < width; ++i)
				container.addToList(new BlockContainer(RelativePoint.getRelativePoint(
						guiItemSquareRectF.left + i * guiItemSquareRectF.width() / width,
						guiItemSquareRectF.top + (height - 1) * guiItemSquareRectF.height()
								/ height), RelativePoint.getRelativePoint(guiItemSquareRectF.left
						+ (i + 1) * guiItemSquareRectF.width() / width, guiItemSquareRectF.top
						+ guiItemSquareRectF.height()), musicBox.getCell(1, i)));
			for (int i = 0; i < height; ++i)
				container.addToList(new BlockContainer(RelativePoint.getRelativePoint(
						guiItemSquareRectF.left,
						guiItemSquareRectF.top + i * guiItemSquareRectF.height() / height),
						RelativePoint.getRelativePoint(
								guiItemSquareRectF.left + guiItemSquareRectF.width() / width,
								guiItemSquareRectF.top + (i + 1) * guiItemSquareRectF.height()
										/ height), musicBox.getCell(2, i)));
			for (int i = 0; i < height; ++i)
				container.addToList(new BlockContainer(RelativePoint.getRelativePoint(
						guiItemSquareRectF.left + (width - 1) * guiItemSquareRectF.width() / width,
						guiItemSquareRectF.top + i * guiItemSquareRectF.height() / height),
						RelativePoint.getRelativePoint(
								guiItemSquareRectF.left + guiItemSquareRectF.width(),
								guiItemSquareRectF.top + (i + 1) * guiItemSquareRectF.height()
										/ height), musicBox.getCell(3, i)));
		}

		private MusicBallBox musicBox;
		private GuiItemContainer container;

		@Override
		public void draw(Canvas canvas) {
			container.draw(canvas);
		}

		@Override
		public boolean onTouchEvent(MotionEvent e) {
			return container.onTouchEvent(e);
		}

		@Override
		public boolean isInsideOf(RelativePoint rp) {
			return container.isInsideOf(rp);
		}

		@Override
		public boolean onBackKey() {
			return container.onBackKey();
		}

		@Override
		public boolean checkState() {
			musicBox.playFunc();
			return container.checkState();
		}

		@Override
		public boolean onTextEvent(CharSequence text) {
			return container.onTextEvent(text);
		}

		@Override
		public boolean onDeleteEvent(int sum) {
			return container.onDeleteEvent(sum);
		}

	}

	public MusicBallBoxGuiItem() {
		super();
		addToList(new MusicBallBoxContainer(new RelativePoint(0, 0), new RelativePoint(1, 0, false)));
	}
}
