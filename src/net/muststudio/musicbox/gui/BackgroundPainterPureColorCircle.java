package net.muststudio.musicbox.gui;

import net.muststudio.util.guiitemlib.ui.BackgroundPainterBase;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class BackgroundPainterPureColorCircle extends BackgroundPainterBase {
	protected Paint fillPainter;
	protected RectF location;

	public BackgroundPainterPureColorCircle(RelativePoint mainPosition, RelativePoint subPosition,
			int color) {
		super(mainPosition, subPosition);
		location = new RectF(super.location);
		fillPainter = new Paint();
		fillPainter.setColor(color);
		fillPainter.setStyle(Style.FILL);
	}

	public BackgroundPainterPureColorCircle setColor(int color) {
		fillPainter.setColor(color);
		return this;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(location.centerX(), location.centerY(), location.width() / 2, fillPainter);
	}
}
