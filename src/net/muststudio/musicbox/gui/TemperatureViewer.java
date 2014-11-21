package net.muststudio.musicbox.gui;

import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import net.muststudio.musicbox.BluetoothTemperatureSource;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.musicbox.SoundMaker;
import net.muststudio.musicbox.util.Waitter;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public class TemperatureViewer extends SquareGuiItem {
	public interface TemperatureSource {
		double getTemperature();
	}

	protected class RandomTemperatureSource implements TemperatureSource {
		private Waitter waitter = new Waitter(20);

		private double lastValue = Math.random() * 0.025 + 37.4875;

		@Override
		public double getTemperature() {
			if (waitter.isOk())
				lastValue = Math.random() * 0.025 + 37.4875;
			return lastValue;
		}
	}

	protected static Paint linePaint = new Paint();
	protected static Paint textPaint = new Paint();

	protected float radiusBig;
	protected float radiusSmall;
	protected TemperatureSource source;
	protected long start = System.currentTimeMillis();
	protected double tempSum = 0;
	protected int tempCount = 0;

	private void isAlarmed(double t) {

		if (System.currentTimeMillis() - start < 10000) {
			tempSum += t;
			tempCount += 1;
			return;
		}
		if (tempSum / (tempCount == 0 ? 1 : tempCount) > 32
				&& tempSum / (tempCount == 0 ? 1 : tempCount) < 34)
			TemperatureSetter.setTempBase(32);
		else
			TemperatureSetter.setTempBase(36.5);

		if (t > TemperatureSetter.getAlarmHigh() || t < TemperatureSetter.getAlarmLow()) {
			linePaint.setColor(Color.RED);
			textPaint.setColor(Color.RED);
			SoundMaker.startAlarm();
		} else {
			linePaint.setColor(Color.WHITE);
			textPaint.setColor(Color.rgb(0x1a, 0x2b, 0x57));
			SoundMaker.stopAlarm();
		}
	}

	public TemperatureViewer(RelativePoint left_up, RelativePoint right_bottom) {
		super(left_up, right_bottom);
		linePaint.setColor(-1);
		linePaint.setStrokeWidth(7);
		textPaint.setColor(Color.rgb(0x1a, 0x2b, 0x57));
		textPaint.setTextSize(guiItemSquareRectF.height() / 9);
		radiusBig = guiItemSquareRectF.width() / 2;
		radiusSmall = radiusBig / 11;
		if (MusicActivity.getActivity().bleEnabled)
			source = new BluetoothTemperatureSource();
		else
			source = new RandomTemperatureSource();
	}

	@Override
	public void draw(Canvas canvas) {
		final double temperature = source.getTemperature();
		isAlarmed(temperature);
		final double angle_t = (temperature - TemperatureSetter.getTempBase()) * Math.PI;
		final float cx = guiItemSquareRectF.centerX();
		final float cy = guiItemSquareRectF.centerY();

		canvas.drawLine((float) (cx - (radiusBig - radiusSmall) * Math.sin(angle_t)),
				(float) (cy + (radiusBig - radiusSmall) * Math.cos(angle_t)),
				(float) (cx - (radiusBig + radiusSmall) * Math.sin(angle_t)),
				(float) (cy + (radiusBig + radiusSmall) * Math.cos(angle_t)), linePaint);

		DecimalFormat df = new DecimalFormat("00.000");
		canvas.drawText(df.format(temperature),
				guiItemSquareRectF.left + guiItemSquareRectF.width() / 5,
				guiItemSquareRectF.centerY() - guiItemSquareRectF.width() / 12, textPaint);
	}
}
