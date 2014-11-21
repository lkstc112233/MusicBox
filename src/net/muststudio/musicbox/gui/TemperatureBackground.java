package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public class TemperatureBackground extends SquareGuiItem {
	private GuiItem setter;
	private GuiItem viewer;
	private static Paint paint1 = new Paint();
	private static Paint paint2 = new Paint();
	protected float radiusBig;
	protected float radiusSmall;

	public TemperatureBackground(RelativePoint left_up, RelativePoint right_bottom) {
		super(left_up, right_bottom);
		addTo(setter = new TemperatureSetter(left_up, right_bottom));
		addTo(viewer = new TemperatureViewer(left_up, right_bottom));
		paint1.setColor(Color.rgb(0xcb, 0x51, 0x22));
		paint2.setColor(Color.rgb(0xf6, 0x88, 0x27));
		radiusBig = guiItemSquareRectF.width() / 2;
		radiusSmall = radiusBig / 11;
		radiusBig = radiusBig + radiusSmall;
		radiusSmall = radiusBig - 2 * radiusSmall;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(guiItemSquareRectF.centerX(), guiItemSquareRectF.centerY(), radiusBig,
				paint1);
		canvas.drawCircle(guiItemSquareRectF.centerX(), guiItemSquareRectF.centerY(), radiusSmall,
				paint2);

		setter.draw(canvas);
		viewer.draw(canvas);
	}

}
