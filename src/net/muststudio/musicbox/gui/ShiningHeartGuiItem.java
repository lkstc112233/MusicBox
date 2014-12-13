package net.muststudio.musicbox.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.musicbox.R;
import net.muststudio.util.guiitemlib.ui.BitmapPainter;
import net.muststudio.util.guiitemlib.ui.BlockedBackToRemoveGuiItemContainer;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;
import net.muststudio.util.guiitemlib.util.BitmapHolder;

public class ShiningHeartGuiItem extends BlockedBackToRemoveGuiItemContainer {
	private class HeartGuiItem extends GuiItem {
		private float vx;
		private float y;
		private float vy;
		private Matrix scale;
		private Bitmap res;

		public HeartGuiItem() {
			float x = (float) (Math.random() * ScreenInfo.getScreenInfo().getScreenWidth());
			y = ScreenInfo.getScreenInfo().getScreenHeight();
			vx = (float) (Math.random() * 6 - 3);
			vy = -(float) (Math.random() * 30 + 20);
			float scale = (float) (Math.random() > 0.5 ? Math.random() * 3 + 1
					: 1 / (Math.random() * 3 + 1));
			this.scale = new Matrix();
			this.scale.setScale(scale, scale);
			this.scale.postTranslate(x, y);
			switch ((int) (Math.random() * 3)) {
			case 0:
				res = BitmapHolder.getInstance().getBitmap(R.drawable.heart1);
				break;
			case 1:
				res = BitmapHolder.getInstance().getBitmap(R.drawable.heart2);
				break;
			default:
				res = BitmapHolder.getInstance().getBitmap(R.drawable.heart3);
				break;
			}
		}

		@Override
		public void draw(Canvas canvas) {
			scale.postTranslate(vx, vy);
			canvas.drawBitmap(res, scale, null);
		}

		@Override
		public boolean checkState() {
			if ((y -= vy) < -128 * 3)
				removeThis();
			return true;
		}
	}

	private MediaPlayer mediaPlayer;

	public ShiningHeartGuiItem() {
		addToList(new BitmapPainter(new RelativePoint(0, 0), new RelativePoint(1, 0, false, true))
				.setBitmap(BitmapHolder.getInstance().getBitmap(R.drawable.smch)));
		mediaPlayer = MediaPlayer.create(MusicActivity.getActivity(), R.raw.ji_te_ba);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
	}

	@Override
	public boolean checkState() {
		if (Math.random() > 0.97)
			addToList(new HeartGuiItem());
		return super.checkState();
	}

	@Override
	public void removeThis() {
		mediaPlayer.stop();
		super.removeThis();
	}
}
