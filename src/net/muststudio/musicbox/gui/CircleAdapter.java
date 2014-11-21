package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public class CircleAdapter extends SquareGuiItem {
	private Paint paint;

	public CircleAdapter(RelativePoint left_up, RelativePoint right_bottom, int color) {
		super(left_up, right_bottom);
		paint = new Paint();
		paint.setColor(color);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(guiItemSquareRectF.centerX(), guiItemSquareRectF.centerY(),
				guiItemSquareRectF.width() / 2, paint);
	}

}
