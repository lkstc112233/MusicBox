package net.muststudio.musicbox.gui;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.musicbox.util.Waitter;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;

public class ECGViewer extends SquareGuiItem {
	protected static Paint greenPaint = new Paint();

	protected static ArrayList<Float> formNormalData() {
		ArrayList<Float> list = new ArrayList<Float>();
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0.25f);// P
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(-0.15f);// Q
		list.add(.95f);// R
		list.add(-0.15f);// S
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0.3f);// T
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		return list;
	}

	protected static ArrayList<Float> formErrorData() {
		ArrayList<Float> list = new ArrayList<Float>();
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		list.add(0f);
		return list;
	}

	protected static final ArrayList<Float> ecgNormalStatic = formNormalData();
	protected static final ArrayList<Float> ecgErrorStatic = formErrorData();

	protected static ArrayList<Float> getEcgData() {
		if (MusicActivity.getActivity().sensorAmbientTemperature > 30)
			return ecgNormalStatic;
		else
			return ecgErrorStatic;

	}

	protected static int pointer = 0;
	protected final static Float[] value_list = new Float[ecgNormalStatic.size()];
	protected static Paint backgroundLinePaint = new Paint();
	protected static Paint backgroundPaint = new Paint();
	protected static Paint textPaint = new Paint();

	public ECGViewer(RelativePoint left_up, RelativePoint right_bottom) {
		super(left_up, right_bottom);
		greenPaint.setStrokeWidth(4);
		backgroundLinePaint.setStrokeWidth(ScreenInfo.getScreenInfo().getScreenWidth() / 100);
		backgroundLinePaint.setColor(Color.rgb(0xcc, 0x70, 0x3d));
		backgroundPaint.setColor(Color.rgb(0xa7, 0x3b, 0x14));
		textPaint.setTextSize(ScreenInfo.getScreenInfo().getScreenWidth() / 12);
		textPaint.setColor(Color.WHITE);
	}

	private Waitter waitter = new Waitter(2);

	@Override
	public void draw(Canvas canvas) {
		for (int i = 0; i < 21; ++i) {
			canvas.drawLine(guiItemSquareRectF.left, i * guiItemSquareRectF.height() / 20
					+ guiItemSquareRectF.top, guiItemSquareRectF.right,
					i * guiItemSquareRectF.height() / 20 + guiItemSquareRectF.top,
					backgroundLinePaint);
			canvas.drawLine(i * guiItemSquareRectF.width() / 20 + guiItemSquareRectF.left,
					guiItemSquareRectF.top, i * guiItemSquareRectF.width() / 20
							+ guiItemSquareRectF.left, guiItemSquareRectF.bottom,
					backgroundLinePaint);
		}

		if (waitter.isOk()) {
			value_list[pointer] = getEcgData().get(pointer);
			value_list[(++pointer) % value_list.length] = null;
			pointer %= value_list.length;
		}

		int paintColor = 255;
		int i = pointer;
		do {
			greenPaint.setColor(Color.argb(paintColor, 0, 255, 0));
			do {
				int t = i - 1;
				if (t < 0)
					break;
				if (value_list[i] == null)
					break;
				if (value_list[t] == null)
					break;
				canvas.drawLine(guiItemSquareRectF.left + guiItemSquareRectF.width()
						/ value_list.length * i, guiItemSquareRectF.centerY() - value_list[i]
						* guiItemSquareRectF.height() / 2, guiItemSquareRectF.left
						+ guiItemSquareRectF.width() / value_list.length * t,
						guiItemSquareRectF.centerY() - value_list[t] * guiItemSquareRectF.height()
								/ 2, greenPaint);
			} while (false);
			paintColor -= 400 / value_list.length;
			if (paintColor < 0)
				paintColor = 0;
			i -= 1;
			if (i < 0)
				i = value_list.length - 1;
		} while (i != pointer);
		canvas.drawRect(guiItemSquareRectF.left,
				guiItemSquareRectF.bottom + guiItemSquareRectF.height() / 20,
				guiItemSquareRectF.right, guiItemSquareRectF.bottom + guiItemSquareRectF.height()
						/ 4, backgroundPaint);
		String textToDraw = getEcgData() == ecgNormalStatic ? "心率正常" : "无数据，请检查传感器";
		canvas.drawText(textToDraw,
				guiItemSquareRectF.centerX() - textPaint.measureText(textToDraw) / 2,
				guiItemSquareRectF.bottom + guiItemSquareRectF.height() / 20 * 4, textPaint);
	}
}
