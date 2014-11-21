package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public class HalfCircleAdapter extends SquareGuiItem {
	private Paint paint;

	public HalfCircleAdapter(RelativePoint left_up, RelativePoint right_bottom, int color) {
		super(left_up, right_bottom);
		paint = new Paint();
		paint.setColor(color);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawArc(guiItemSquareRectF, 180, 180, true, paint);
	}

}
