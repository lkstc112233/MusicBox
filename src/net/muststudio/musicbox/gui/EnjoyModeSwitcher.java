package net.muststudio.musicbox.gui;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import net.muststudio.musicbox.SoundMaker;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;

public class EnjoyModeSwitcher extends GuiItem {
	private boolean status = false;
	private boolean hot = false;
	private boolean left_right = false;
	private int count = 0;

	private static final ArrayList<PointF> points = new ArrayList<PointF>();
	private static final Paint paint = new Paint();

	public EnjoyModeSwitcher() {
		paint.setColor(Color.argb(0x6f, 255, 255, 0));
	}

	private static final RectF bottomRect = new RectF(0, ScreenInfo.getScreenInfo()
			.getScreenHeight() * 3 / 4, ScreenInfo.getScreenInfo().getScreenWidth(), ScreenInfo
			.getScreenInfo().getScreenHeight());
	private static final RectF bottomLeftRect = new RectF(0, ScreenInfo.getScreenInfo()
			.getScreenHeight() * 3 / 4, ScreenInfo.getScreenInfo().getScreenWidth() / 4, ScreenInfo
			.getScreenInfo().getScreenHeight());
	private static final RectF bottomRightRect = new RectF(ScreenInfo.getScreenInfo()
			.getScreenWidth() * 3 / 4, ScreenInfo.getScreenInfo().getScreenHeight() * 3 / 4,
			ScreenInfo.getScreenInfo().getScreenWidth(), ScreenInfo.getScreenInfo()
					.getScreenHeight());
	private static final RectF topRect = new RectF(0, 0, ScreenInfo.getScreenInfo()
			.getScreenWidth(), ScreenInfo.getScreenInfo().getScreenHeight() / 4);

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			if (status)
				synchronized (points) {
					points.add(new PointF(e.getX(), e.getY()));
				}
			break;
		case MotionEvent.ACTION_UP:
			synchronized (points) {
				points.clear();
			}
		}

		if (status) {
			if (hot) {
				if (e.getAction() == MotionEvent.ACTION_UP)
					if (topRect.contains(e.getX(), e.getY())) {
						status = false;
						hot = false;
						SoundMaker.closeSoundMode();
					} else
						hot = false;
			} else if (e.getAction() == MotionEvent.ACTION_DOWN)
				if (bottomRect.contains(e.getX(), e.getY()))
					hot = true;
			return true;
		} else {
			if (hot) {
				RectF toExam;
				if (left_right)
					toExam = bottomLeftRect;
				else
					toExam = bottomRightRect;
				if (e.getAction() == MotionEvent.ACTION_MOVE)
					if (toExam.contains(e.getX(), e.getY())) {
						left_right = !left_right;
						count += 1;
					}
				if (e.getAction() == MotionEvent.ACTION_UP)
					if (count == 3) {
						hot = false;
						status = true;
						SoundMaker.openSoundMode();
					} else {
						hot = false;
						count = 0;
					}
				return true;
			} else {
				if (e.getAction() == MotionEvent.ACTION_DOWN) {
					if (bottomLeftRect.contains(e.getX(), e.getY())) {
						left_right = false;
						hot = true;
						count = 0;
						return true;
					}
					if (bottomRightRect.contains(e.getX(), e.getY())) {
						left_right = true;
						hot = true;
						count = 0;
						return true;
					}
				}
				return false;
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		Object[] array;
		synchronized (points) {
			array = points.toArray().clone();
		}
		for (Object p : array)
			canvas.drawCircle(((PointF) p).x, ((PointF) p).y, 5, paint);
	}

}
