package net.muststudio.musicbox.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import net.muststudio.util.guiitemlib.ui.GenericButton;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.util.BitmapHolder;

public class CircledIconButtonGuiItem extends GenericButton {
	private Bitmap resource;

	public CircledIconButtonGuiItem(RelativePoint left_up, RelativePoint right_bottom, int color,
			int iconRes) {
		super(left_up, right_bottom, new BackgroundPainterPureColorCircle(left_up, right_bottom,
				color));
		resource = BitmapHolder.getInstance().getBitmap(iconRes);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawBitmap(resource, null, guiItemSquareRectF, null);
	}

}
