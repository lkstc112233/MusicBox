package net.muststudio.musicbox.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.util.guiitemlib.ui.GenericButton;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.GuiItemContainer;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;

public class BackgroundedGuiItemContainer extends GuiItemContainer {
	public static class ClickAdapter extends GenericButton {
		public ClickAdapter(RelativePoint left_up, RelativePoint right_bottom) {
			super(left_up, right_bottom);
		}

		@Override
		public void draw(Canvas canvas) {
		}
	}

	public static class BackToExitAdapter extends GuiItem {
		@Override
		public void draw(Canvas canvas) {
		}

		@Override
		public boolean onBackKey() {
			MusicActivity.getActivity().finish();
			return true;
		}
	}

	protected Bitmap background;
	protected Rect screenRect;

	public BackgroundedGuiItemContainer(Bitmap bitmap) {
		background = bitmap;
		screenRect = new Rect();
		screenRect.left = screenRect.top = 0;
		screenRect.right = ScreenInfo.getScreenInfo().getScreenWidth();
		screenRect.bottom = ScreenInfo.getScreenInfo().getScreenHeight();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(background, null, screenRect, null);
		super.draw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		super.onTouchEvent(e);
		return true;
	}

	@Override
	public boolean isInsideOf(RelativePoint rp) {
		super.isInsideOf(rp);
		return true;
	}

	@Override
	public boolean onBackKey() {
		if (!super.onBackKey())
			removeThis();
		return true;
	}

	@Override
	public boolean checkState() {
		super.checkState();
		return false;
	}

	@Override
	public boolean onTextEvent(CharSequence text) {
		super.onTextEvent(text);
		return true;
	}

	@Override
	public boolean onDeleteEvent(int sum) {
		super.onDeleteEvent(sum);
		return true;
	}
}
