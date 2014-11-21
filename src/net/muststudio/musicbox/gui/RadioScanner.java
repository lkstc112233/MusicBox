package net.muststudio.musicbox.gui;

import java.util.ArrayDeque;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Pair;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public class RadioScanner extends SquareGuiItem {
	private class MyInteger {
		public MyInteger(int i) {
			this.i = i;
		}

		public int i;
	}

	private static final float ANGLE_STEP = (float) (Math.PI / 36);
	private static final int fadeRate = 255 / 20;

	private static final int MAX_RADIO_SIZE = 20;

//	private float storedOZ;
//	private float storedRadius;

	private float xO;
	private float yO;

	protected final Paint radioRingPaint = new Paint();
	protected final Paint radioBarPaint = new Paint();
	protected final Paint radioBackgroundPaint = new Paint();

	protected final ArrayDeque<Float> radioAngles = new ArrayDeque<Float>();
	protected final ArrayDeque<Pair<Pair<Float, Float>, MyInteger>> angle_distance_alpha_pairs = new ArrayDeque<Pair<Pair<Float, Float>, MyInteger>>();

	private float radius;

	private float getAngle() {
		float d = getDistance();
		float x = xO - MusicActivity.getActivity().phoneP[0];
		float y = xO - MusicActivity.getActivity().phoneP[0];
		float a = (float) Math.acos(x / d);
		if (y < 0)
			a *= -1;
		// a = (float) (2 * Math.PI - a);
		return (float) (a+Math.PI);
	}

	private float getDistance() {
		return (float) Math.pow(
				Math.pow(xO - MusicActivity.getActivity().phoneP[0], 2)
						+ Math.pow(yO - MusicActivity.getActivity().phoneP[1], 2), 0.5) * 100;
	}

	public RadioScanner(RelativePoint left_up, RelativePoint right_bottom) {
		super(left_up, right_bottom);
		// storedOZ = JimmyActivity.getActivity().phoneOrientationValues[2];
		// storedRadius = 70;
		xO = MusicActivity.getActivity().phoneP[0];
		yO = MusicActivity.getActivity().phoneP[1];
		radioRingPaint.setColor(Color.GREEN);
		radioRingPaint.setStrokeWidth(7);
		radioRingPaint.setStyle(Style.STROKE);
		radioBarPaint.setColor(Color.GREEN);
		radioBarPaint.setStrokeWidth(7);
		radioBackgroundPaint.setColor(Color.BLACK);
		radioAngles.add(0f);
		radius = guiItemSquareRectF.width() / 2;
	}

	@Override
	public void draw(Canvas canvas) {
		float angle = -MusicActivity.getActivity().phoneOrientationValues[0] - getAngle();
		while (angle > Math.PI * 2)
			angle -= Math.PI * 2;
		while (angle < 0)
			angle += Math.PI * 2;
		float temp = radioAngles.getFirst() + ANGLE_STEP;
		if (temp > Math.PI * 2)
			temp -= Math.PI * 2;
		radioAngles.addFirst(temp);
		if (radioAngles.size() > MAX_RADIO_SIZE)
			radioAngles.removeLast();

		if (Math.abs(temp - angle) < ANGLE_STEP)
			angle_distance_alpha_pairs.add(new Pair<Pair<Float, Float>, MyInteger>(
					new Pair<Float, Float>(angle, getDistance()), new MyInteger(255)));

		// Paint.
		canvas.drawCircle(guiItemSquareRectF.centerX(), guiItemSquareRectF.centerY(), radius,
				radioBackgroundPaint);
		canvas.drawCircle(guiItemSquareRectF.centerX(), guiItemSquareRectF.centerY(), radius / 3,
				radioRingPaint);
		canvas.drawCircle(guiItemSquareRectF.centerX(), guiItemSquareRectF.centerY(),
				radius / 3 * 2, radioRingPaint);
		canvas.drawCircle(guiItemSquareRectF.centerX(), guiItemSquareRectF.centerY(), radius,
				radioRingPaint);
		canvas.drawLine(guiItemSquareRectF.centerX(), guiItemSquareRectF.top,
				guiItemSquareRectF.centerX(), guiItemSquareRectF.bottom, radioRingPaint);
		canvas.drawLine(guiItemSquareRectF.left, guiItemSquareRectF.centerY(),
				guiItemSquareRectF.right, guiItemSquareRectF.centerY(), radioRingPaint);

		int alpha = 255;
		for (Float f : radioAngles) {
			radioBarPaint.setColor(Color.argb(alpha, 0, 255, 0));
			// canvas.drawLine(guiItemSquareRectF.centerX(),
			// guiItemSquareRectF.centerY(),
			// (float) (guiItemSquareRectF.centerX() + radius * Math.cos(f)),
			// (float) (guiItemSquareRectF.centerY() + radius * Math.sin(f)),
			// radioBarPaint);
			canvas.drawArc(guiItemSquareRectF, (float) Math.toDegrees(f),
					(float) Math.toDegrees(ANGLE_STEP), true, radioBarPaint);
			alpha -= fadeRate;
		}

		for (Pair<Pair<Float, Float>, MyInteger> p : angle_distance_alpha_pairs) {
			radioBarPaint.setColor(Color.argb(p.second.i, 0, 255, 0));
			canvas.drawCircle(
					(float) (guiItemSquareRectF.centerX() + p.first.second
							* Math.cos(p.first.first)),
					(float) (guiItemSquareRectF.centerY() + p.first.second
							* Math.sin(p.first.first)), 7, radioBarPaint);
			p.second.i -= fadeRate;
		}
		while (angle_distance_alpha_pairs.size() != 0)
			if (angle_distance_alpha_pairs.getFirst().second.i <= 0)
				angle_distance_alpha_pairs.removeFirst();
			else
				break;
	}
}
