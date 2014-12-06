package net.muststudio.musicbox.gui;

import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

class CellWaveGuiItem extends SquareGuiItem {
	public CellWaveGuiItem(RelativePoint left_up, RelativePoint right_bottom) {
		super(left_up, right_bottom);
		paintW.setColor(Color.WHITE);
		paintW.setStyle(Style.STROKE);
		paintW.setStrokeWidth(15);
	}

	private Paint paintW = new Paint();
	private float radius = 0;
	private final float step = 20;

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(guiItemSquareRect.exactCenterX(),
				guiItemSquareRect.exactCenterY(), radius += step, paintW);
	}

	@Override
	public boolean checkState() {
		paintW.setAlpha(Math.max(0, paintW.getAlpha() - 20));
		if (paintW.getAlpha() <= 0)
			removeThis();
		return true;
	}
}