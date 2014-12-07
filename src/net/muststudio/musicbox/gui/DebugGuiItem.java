package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.util.guiitemlib.ui.GuiItem;

public class DebugGuiItem extends GuiItem {

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(-1);
		int dp = 30;
		paint.setTextSize(dp);
		int y = 0;
		canvas.drawText("SensorValues:", 0, y += dp, paint);
		canvas.drawText("AccelerometerValues:", 0, y += dp, paint);
		canvas.drawText(
				"x:" + Double.toString(MusicActivity.getActivity().sensorAccelerometerValues.x), 0,
				y += dp, paint);
		canvas.drawText(
				"y:" + Double.toString(MusicActivity.getActivity().sensorAccelerometerValues.y), 0,
				y += dp, paint);
		canvas.drawText(
				"z:" + Double.toString(MusicActivity.getActivity().sensorAccelerometerValues.z), 0,
				y += dp, paint);
		canvas.drawText("MagnetometerValues:", 0, y += dp, paint);
		canvas.drawText(
				"x:" + Double.toString(MusicActivity.getActivity().sensorMagnetometerValues.x), 0,
				y += dp, paint);
		canvas.drawText(
				"y:" + Double.toString(MusicActivity.getActivity().sensorMagnetometerValues.y), 0,
				y += dp, paint);
		canvas.drawText(
				"z:" + Double.toString(MusicActivity.getActivity().sensorMagnetometerValues.z), 0,
				y += dp, paint);
		canvas.drawText("GyroscopeValues:", 0, y += dp, paint);
		canvas.drawText(
				"x:" + Double.toString(MusicActivity.getActivity().sensorGyroscopeValues.x), 0,
				y += dp, paint);
		canvas.drawText(
				"y:" + Double.toString(MusicActivity.getActivity().sensorGyroscopeValues.y), 0,
				y += dp, paint);
		canvas.drawText(
				"z:" + Double.toString(MusicActivity.getActivity().sensorGyroscopeValues.z), 0,
				y += dp, paint);
		canvas.drawText("OrientationValues:", 0, y += dp, paint);
		canvas.drawText(
				"x:" + Float.toString(MusicActivity.getActivity().sensorOrientationValuesf[0]), 0,
				y += dp, paint);
		canvas.drawText(
				"y:" + Float.toString(MusicActivity.getActivity().sensorOrientationValuesf[1]), 0,
				y += dp, paint);
		canvas.drawText(
				"z:" + Float.toString(MusicActivity.getActivity().sensorOrientationValuesf[2]), 0,
				y += dp, paint);

		y = 0;

		canvas.drawText("PhoneValues:", 400, y += dp, paint);
		canvas.drawText("AccelerometerValues:", 400, y += dp, paint);
		canvas.drawText(
				"x:" + Float.toString(MusicActivity.getActivity().phoneAccelerometerValues[0]),
				400, y += dp, paint);
		canvas.drawText(
				"y:" + Float.toString(MusicActivity.getActivity().phoneAccelerometerValues[1]),
				400, y += dp, paint);
		canvas.drawText(
				"z:" + Float.toString(MusicActivity.getActivity().phoneAccelerometerValues[2]),
				400, y += dp, paint);
		canvas.drawText("MagnetometerValues:", 400, y += dp, paint);
		canvas.drawText(
				"x:" + Float.toString(MusicActivity.getActivity().phoneMagneticFieldValues[0]),
				400, y += dp, paint);
		canvas.drawText(
				"y:" + Float.toString(MusicActivity.getActivity().phoneMagneticFieldValues[1]),
				400, y += dp, paint);
		canvas.drawText(
				"z:" + Float.toString(MusicActivity.getActivity().phoneMagneticFieldValues[2]),
				400, y += dp, paint);
		canvas.drawText("OrientationValues:", 400, y += dp, paint);
		canvas.drawText(
				"x:" + Float.toString(MusicActivity.getActivity().phoneOrientationValues[0]), 400,
				y += dp, paint);
		canvas.drawText(
				"y:" + Float.toString(MusicActivity.getActivity().phoneOrientationValues[1]), 400,
				y += dp, paint);
		canvas.drawText(
				"z:" + Float.toString(MusicActivity.getActivity().phoneOrientationValues[2]), 400,
				y += dp, paint);
		canvas.drawText("PhoneV:", 400, y += dp, paint);
		canvas.drawText("x:" + Float.toString(MusicActivity.getActivity().phoneV[0]), 400, y += dp,
				paint);
		canvas.drawText("y:" + Float.toString(MusicActivity.getActivity().phoneV[1]), 400, y += dp,
				paint);
		canvas.drawText("phoneP:", 400, y += dp, paint);
		canvas.drawText("x:" + Float.toString(MusicActivity.getActivity().phoneP[0]), 400, y += dp,
				paint);
		canvas.drawText("y:" + Float.toString(MusicActivity.getActivity().phoneP[1]), 400, y += dp,
				paint);
	}

	@Override
	public boolean onBackKey() {
		removeThis();
		return true;
	}

}
