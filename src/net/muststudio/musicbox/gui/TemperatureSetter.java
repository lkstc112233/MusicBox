package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public class TemperatureSetter extends SquareGuiItem {
	protected static double alarmLow = 0.75;
	protected static double alarmHigh = 1.25;
	protected static double tempBase = 36.5;
	protected static Paint paint = new Paint();

	protected float radiusBig;
	protected float radiusSmall;
	private boolean movingBig;
	private boolean moving;

	public static double getAlarmHigh() {
		return tempBase + alarmHigh;
	}

	public static double getAlarmLow() {
		return tempBase + alarmLow;
	}

	public static void setTempBase(double tempBase) {
		TemperatureSetter.tempBase = tempBase;
	}

	public static double getTempBase() {
		return tempBase;
	}

	public TemperatureSetter(RelativePoint left_up, RelativePoint right_bottom) {
		super(left_up, right_bottom);
		paint.setColor(Color.rgb(0x6b, 0x2e, 0x36));
		radiusBig = guiItemSquareRectF.width() / 2;
		radiusSmall = radiusBig / 11;
		moving = movingBig = false;
	}

	@Override
	public void draw(Canvas canvas) {
		final double angle_low = alarmLow * Math.PI;
		final double angle_high = alarmHigh * Math.PI;

		final float cx = guiItemSquareRectF.centerX();
		final float cy = guiItemSquareRectF.centerY();

		canvas.drawCircle((float) (cx - radiusBig * Math.sin(angle_low)), (float) (cy + radiusBig
				* Math.cos(angle_low)), radiusSmall, paint);
		canvas.drawCircle((float) (cx - radiusBig * Math.sin(angle_high)), (float) (cy + radiusBig
				* Math.cos(angle_high)), radiusSmall, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		final float cx = guiItemSquareRectF.centerX();
		final float cy = guiItemSquareRectF.centerY();
		switch (e.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			final double angle_low = alarmLow * Math.PI;
			final double angle_high = alarmHigh * Math.PI;
			moving = true;
			if (new RectF((float) (cx - radiusBig * Math.sin(angle_low)) - radiusSmall,
					(float) (cy + radiusBig * Math.cos(angle_low)) - radiusSmall,
					(float) (cx - radiusBig * Math.sin(angle_low)) + radiusSmall,
					(float) (cy + radiusBig * Math.cos(angle_low)) + radiusSmall).contains(
					e.getX(), e.getY()))
				movingBig = false;
			else if (new RectF((float) (cx - radiusBig * Math.sin(angle_high)) - radiusSmall,
					(float) (cy + radiusBig * Math.cos(angle_high)) - radiusSmall,
					(float) (cx - radiusBig * Math.sin(angle_high)) + radiusSmall,
					(float) (cy + radiusBig * Math.cos(angle_high)) + radiusSmall).contains(
					e.getX(), e.getY()))
				movingBig = true;
			else
				moving = false;
			return moving;
		case MotionEvent.ACTION_MOVE:
			if (!moving)
				return false;
			double angle = Math.acos((e.getY() - cy)
					/ Math.pow((e.getX() - cx) * (e.getX() - cx) + (e.getY() - cy)
							* (e.getY() - cy), 0.5));
			if (e.getX() > cx)
				angle = 2 * Math.PI - angle;
			if (movingBig)
				alarmHigh = angle / Math.PI;
			else
				alarmLow = angle / Math.PI;
			if (alarmHigh < alarmLow) {
				double temp = alarmLow;
				alarmLow = alarmHigh;
				alarmHigh = temp;
			}
			return true;
		case MotionEvent.ACTION_UP:
			return moving = false;
		default:
			break;
		}
		return false;
	}
}
