package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.musicbox.MusicGrid;
import net.muststudio.musicbox.MusicGrid.Cell;
import net.muststudio.musicbox.SoundMaker;
import net.muststudio.musicbox.util.Waitter;
import net.muststudio.util.guiitemlib.ui.BlockedBackToRemoveGuiItemContainer;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.GuiItemContainer;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public final class MusicGridGuiItem extends BlockedBackToRemoveGuiItemContainer {
	private class MusicBoxContainer extends SquareGuiItem {
		private int waitter2 = 0;

		private class MusicBoxChanger extends GuiItem {
			@Override
			public void draw(Canvas canvas) {
			}

			Waitter waitter = new Waitter(10);
			Waitter waitterRithm = new Waitter(4);
			int y = 0;

			@Override
			public boolean checkState() {
				if (waitter.isOk())
					changeMusic();
				return true;
			}

			private void changeMusic() {
				if (waitter2 > 0)
					return;
				boolean[] line = new boolean[musicBox.getWidth()];
				for (int i = 0; i < line.length; ++i)
					line[i] = false;
				line[(int) Math.abs(MusicActivity.getActivity().phoneOrientationValues[0] * 10)
						% line.length] = true;
				if (!waitterRithm.isOk())
					musicBox.lineChange(line, y++);
				else
					y++;
			}
		}

		private boolean cellChangeStatus = false;

		private class CellContainer extends SquareGuiItem {

			private Cell cell;
			private Paint paintW = new Paint();
			private Paint paintB = new Paint();

			public CellContainer(RelativePoint left_up, RelativePoint right_bottom, Cell cell) {
				super(left_up, right_bottom);
				this.cell = cell;
				paintW.setColor(Color.GRAY);
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
				synchronized (this) {
					waitter2 = 100000;
				}
				switch (e.getAction()) {
				case MotionEvent.ACTION_DOWN:
					cell.switchStatus();
					cellChangeStatus = cell.getStatus();
					break;
				case MotionEvent.ACTION_MOVE:
				case MotionEvent.ACTION_UP:
					if (cell.getStatus() ^ cellChangeStatus)
						cell.switchStatus();
					break;
				default:
					return false;
				}
				return true;
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

		public MusicBoxContainer(RelativePoint left_up, RelativePoint right_bottom) {
			super(left_up, right_bottom);
			int height;
			final int size = 21;
			musicBox = new MusicGrid(size, height = (int) (guiItemSquareRectF.height()
					/ guiItemSquareRectF.width() * size));
			musicBox.setSoundPlayer(new SoundMaker());
			container = new GuiItemContainer();
			for (int i = 0; i < size; ++i)
				for (int j = 0; j < height; ++j)
					container.addToList(new CellContainer(RelativePoint.getRelativePoint(
							guiItemSquareRectF.left + i * guiItemSquareRectF.width() / size,
							guiItemSquareRectF.top + j * guiItemSquareRectF.height() / height),
							RelativePoint.getRelativePoint(guiItemSquareRectF.left + (i + 1)
									* guiItemSquareRectF.width() / size, guiItemSquareRectF.top
									+ (j + 1) * guiItemSquareRectF.height() / height), musicBox
									.getCell(i, j)));
			container.addToList(new MusicBoxChanger());
		}

		private MusicGrid musicBox;
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

	public MusicGridGuiItem() {
		super();
		addToList(new MusicBoxContainer(new RelativePoint(0, 0), new RelativePoint(1, 0, false)));
	}
}
