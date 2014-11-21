package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;

public class TextAdapter extends GuiItem {
	public class StringHolder {
		public String str;
	}

	protected RelativePoint paintPosition;
	protected Paint paint;
	protected StringHolder toPaintText;

	public TextAdapter(RelativePoint left_up, String text, double size) {
		paintPosition = left_up;
		toPaintText = new StringHolder();
		toPaintText.str = text;
		paint = new Paint();
		paint.setTextSize((float) (ScreenInfo.getScreenInfo().getScreenWidth() * size * 0.95));
	}

	public StringHolder getHolder() {
		return toPaintText;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText(toPaintText.str, paintPosition.getScreenX(), paintPosition.getScreenY(),
				paint);
	}
}
