package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import net.muststudio.musicbox.MusicBox;
import net.muststudio.musicbox.MusicBox.Cell;
import net.muststudio.musicbox.SoundMaker;
import net.muststudio.util.guiitemlib.ui.BlockedBackToRemoveGuiItemContainer;
import net.muststudio.util.guiitemlib.ui.GuiItemContainer;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public final class MusicBoxGuiItem extends BlockedBackToRemoveGuiItemContainer {
	private class MusicBoxContainer extends SquareGuiItem {
		private class CellContainer extends SquareGuiItem {
			private Cell cell;
			private Paint paintW = new Paint();
			private Paint paintB = new Paint();

			public CellContainer(RelativePoint left_up, RelativePoint right_bottom, Cell cell) {
				super(left_up, right_bottom);
				this.cell = cell;
				paintW.setColor(Color.WHITE);
				paintB.setColor(Color.BLACK);
			}

			@Override
			public void draw(Canvas canvas) {
				if (cell.getStatus())
					canvas.drawRect(guiItemSquareRect, paintW);
				else
					canvas.drawRect(guiItemSquareRect, paintB);
			}

			@Override
			public boolean onTouchEvent(MotionEvent e) {
				if (!isInsideOf(RelativePoint.getRelativePoint(e.getX(), e.getY())))
					return false;
				switch (e.getAction()) {
				case MotionEvent.ACTION_UP:
					cell.switchStatus();
					break;
				default:
					return false;
				}
				return true;
			}
		}

		public MusicBoxContainer(RelativePoint left_up, RelativePoint right_bottom) {
			super(left_up, right_bottom);
			int height;
			musicBox = new MusicBox(10, height = (guiItemSquareRect.height()
					/ guiItemSquareRect.width() * 10));
			musicBox.setSoundPlayer(new SoundMaker());
			container = new GuiItemContainer();
			for (int i = 0; i < 10; ++i)
				for (int j = 0; j < height; ++j)
					container.addToList(new CellContainer(RelativePoint.getRelativePoint(
							guiItemSquareRectF.left + i * guiItemSquareRectF.width() / 10,
							guiItemSquareRectF.top + j * guiItemSquareRectF.height() / height),
							RelativePoint.getRelativePoint(guiItemSquareRectF.left + (i + 1)
									* guiItemSquareRectF.width() / 10, guiItemSquareRectF.top
									+ (j + 1) * guiItemSquareRectF.height() / height), musicBox
									.getCell(i, j)));
		}

		private MusicBox musicBox;
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

	public MusicBoxGuiItem() {
		super();
		addToList(new MusicBoxContainer(new RelativePoint(0, 0), new RelativePoint(1, 1)));
	}
}
