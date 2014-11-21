package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public class TemperatureWave extends SquareGuiItem {
	public static final double TMax = 39;
	public static final double TMin = 35;
	public static final Paint paint = new Paint();

	public TemperatureWave(RelativePoint left_up, RelativePoint right_bottom) {
		super(left_up, right_bottom);
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(3);
	}

	@Override
	public void draw(Canvas canvas) {
		try {
			Object[] values;
			synchronized (MusicActivity.getActivity().realtimeTemperatures) {
				values = MusicActivity.getActivity().realtimeTemperatures.toArray().clone();
			}
			if (0 == values.length)
				return;
			// + 3
			float lasty = getX(0);
			float lastx = getY((Double) values[0]);
			canvas.drawCircle(lastx, lasty, 4, paint);
			for (int i = 0; i < values.length; ++i) {
				canvas.drawLine(lastx, lasty, getX(i), getY((Double) values[i]), paint);
				lasty = getX(i);
				lastx = getY((Double) values[i]);
				canvas.drawCircle(lastx, lasty, 5, paint);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private float getX(int i) {
		return guiItemSquareRectF.left + guiItemSquareRectF.height() / 100 * i;
	}

	private float getY(double temp) {
		return (float) (guiItemSquareRectF.bottom - guiItemSquareRectF.width() / (TMax - TMin)
				* ((temp + 3) - TMin));
	}
}
