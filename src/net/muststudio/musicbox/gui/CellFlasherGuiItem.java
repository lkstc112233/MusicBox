package net.muststudio.musicbox.gui;

import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class CellFlasherGuiItem extends SquareGuiItem {
	public CellFlasherGuiItem(RelativePoint left_up, RelativePoint right_bottom) {
		super(left_up, right_bottom);
		paintF.setColor(Color.WHITE);
	}

	private Paint paintF = new Paint();

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(guiItemSquareRect, paintF);
	}

	@Override
	public boolean checkState() {
		paintF.setAlpha(Math.max(0, paintF.getAlpha() - 30));
		if (paintF.getAlpha() <= 0)
			removeThis();
		return true;
	}
}