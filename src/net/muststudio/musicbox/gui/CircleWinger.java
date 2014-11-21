package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;

public class CircleWinger extends GuiItem {
	private float swinRadius = ScreenInfo.getScreenInfo().getScreenWidth() / 5;
	private float circleRadius = ScreenInfo.getScreenInfo().getScreenWidth() / 10;
	private float step = 0.01f;
	private RelativePoint center;
	private Paint paint;

	private float angle;

	public CircleWinger(RelativePoint center) {
		this.center = center;
		paint = new Paint();
		paint.setColor(Color.argb(0xa7, 0x4c, 0x58, 0x85));
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(ScreenInfo.getScreenInfo().getScreenWidth() / 30);
		angle = (float) (Math.random() * Math.PI * 2);
		swinRadius *= Math.random() * 0.5 + 1;
		circleRadius *= Math.random() * 0.3 + 1;
		step *= Math.random() * 0.2 + 0.9;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(
				(float) (center.getScreenX() + swinRadius
						* Math.cos(angle += step
								* (1 + MusicActivity.getActivity().sensorAmbientTemperature / 40))),
				(float) (center.getScreenY() + swinRadius
						* Math.sin(angle += step
								* (1 + MusicActivity.getActivity().sensorAmbientTemperature / 40))),
				circleRadius, paint);
	}

}
