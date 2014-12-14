package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.view.MotionEvent;
import net.muststudio.musicbox.LowVolSoundPlayer;
import net.muststudio.musicbox.MusicBallBox;
import net.muststudio.musicbox.MusicBallBox.Ball;
import net.muststudio.musicbox.util.Waitter;
import net.muststudio.util.guiitemlib.ui.GuiItemContainer;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public final class InvisibleMusicBallBoxGuiItem extends GuiItemContainer {
	private class MusicBallBoxContainer extends SquareGuiItem {
		private class BallContainer extends SquareGuiItem {
			private class BallWaveGuiItem extends CellWaveGuiItem {
				public BallWaveGuiItem(RelativePoint left_up) {
					super(left_up, left_up);
					paintW.setAlpha(128);
				}
			}

			private Ball ball;
			private Waitter waitter = new Waitter(4);

			public BallContainer(RelativePoint left_up, RelativePoint right_bottom, Ball cell) {
				super(left_up, right_bottom);
				this.ball = cell;
			}

			@Override
			public boolean checkState() {
				if (waitter.isOk())
					addTo(new BallWaveGuiItem(RelativePoint.getRelativePoint(
							guiItemSquareRectF.left + ball.getX()
									* MusicBallBoxContainer.this.guiItemSquareRectF.width() / 21,
							guiItemSquareRectF.top + ball.getY()
									* MusicBallBoxContainer.this.guiItemSquareRectF.width() / 21)));
				return true;
			}

			@Override
			public void draw(Canvas canvas) {
			}
		}

		public MusicBallBoxContainer(RelativePoint left_up, RelativePoint right_bottom) {
			super(left_up, right_bottom);
			final int width = 21;
			musicBox = new MusicBallBox(width, (int) (guiItemSquareRectF.height()
					/ guiItemSquareRectF.width() * width));
			musicBox.setSoundPlayer(new LowVolSoundPlayer(0.3f));
			container = new GuiItemContainer();
			addToList(new BallContainer(mainPosition, subPosition, musicBox.getBall()));
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

	public InvisibleMusicBallBoxGuiItem() {
		addToList(new MusicBallBoxContainer(new RelativePoint(0, 0), new RelativePoint(1, 0, false)));
	}
}
