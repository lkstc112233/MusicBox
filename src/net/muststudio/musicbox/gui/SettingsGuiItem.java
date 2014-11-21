package net.muststudio.musicbox.gui;

import java.util.Calendar;

import net.muststudio.musicbox.R;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.musicbox.Settings;
import net.muststudio.util.guiitemlib.ui.GenericButton;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.TextPainter;
import net.muststudio.util.guiitemlib.ui.GenericButton.Task;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.widget.TimePicker;

public class SettingsGuiItem extends ColoredGuiItemContainer {
	Calendar calendar = Calendar.getInstance();
	TextEditControl n;
	TextEditControl a;

	public SettingsGuiItem() {
		super(Color.rgb(0x35, 0x49, 0x90));// f1c40f
		addToList(new BackgroundedGuiItemContainer.BackToExitAdapter());
		addToList(new CircleWinger(new RelativePoint(0.08, 0.08)));
		addToList(new CircleWinger(new RelativePoint(0.06, 0.02)));
		addToList(new CircleWinger(new RelativePoint(0.92, 0.08)));
		addToList(new CircleWinger(new RelativePoint(0.95, 0.02)));
		addToList(new CircleWinger(new RelativePoint(0.97, 0.03)));
		addToList(new CircleWinger(new RelativePoint(0.92, 0.07, false)));
		addToList(new CircleWinger(new RelativePoint(0.05, 0.02, false)));
		addToList(new CircleWinger(new RelativePoint(0.07, 0.03, false)));
		addToList(new CircleWinger(new RelativePoint(0.92, 0.57, true, false)));
		addToList(new CircleWinger(new RelativePoint(0.96, 0.62, true, false)));
		addToList(new CircleWinger(new RelativePoint(0.1, 0.72, true, false)));

		addToList(new TextPainter(new RelativePoint(0.075, 0.28), new RelativePoint(0.925, 0.18),
				false).setHorizontalCenter(true).setString("SETTINGS").setVerticalCenter(true)
				.setTextCount(20));

		addToList(new RoundRectAdapter(new RelativePoint(0.075, 0.29), new RelativePoint(0.925,
				0.38375), Color.rgb(0x16, 0xa0, 0x86)));
		addToList(new TextPainter(new RelativePoint(0.075, 0.29), new RelativePoint(0.5, 0.38375),
				false).setHorizontalCenter(false).setString("PHONE").setVerticalCenter(true)
				.setTextCount(10));
		addToList(n = new TextEditControl(new RelativePoint(0.3, 0.2875), new RelativePoint(0.925,
				0.3835), Settings.getSettings().getDialNum()));
		addToList(new RoundRectAdapter(new RelativePoint(0.075, 0.4775), new RelativePoint(0.925,
				0.57125), Color.rgb(0x16, 0xa0, 0x86)));
		addToList(new TextPainter(new RelativePoint(0.075, 0.4775),
				new RelativePoint(0.5, 0.57125), false).setHorizontalCenter(false)
				.setString("MESSAGE").setVerticalCenter(true).setTextCount(10));
		addToList(a = new TextEditControl(new RelativePoint(0.3, 0.475), new RelativePoint(0.925,
				0.571), Settings.getSettings().getSmsNum()));

		addToList(new GenericButton(new RelativePoint(0.075, 0.69), new RelativePoint(0.925,
				0.78375), "ALARM", Color.rgb(0x7b, 0x45, 0xa7)).setTask(new Task() {
			@Override
			public void task() {
				calendar.setTimeInMillis(System.currentTimeMillis());
				int mHour = calendar.get(Calendar.HOUR_OF_DAY);
				int mMinute = calendar.get(Calendar.MINUTE);
				new TimePickerDialog(MusicActivity.getActivity(),
						new TimePickerDialog.OnTimeSetListener() {
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								calendar.setTimeInMillis(System.currentTimeMillis());
								calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
								calendar.set(Calendar.MINUTE, minute);
								calendar.set(Calendar.SECOND, 0);
								calendar.set(Calendar.MILLISECOND, 0);
								Settings.getSettings().setHourOfDay(hourOfDay);
								Settings.getSettings().setMinute(minute);
								Settings.getSettings().write();
							}
						}, mHour, mMinute, true).show();
			}
		}));

		addToList(new CircledIconButtonGuiItem(new RelativePoint(0.43, 1.3), new RelativePoint(
				0.57, 1.45), Color.rgb(0x1d, 0x2a, 0x57), R.drawable.setting));
	}

	@Override
	public boolean onBackKey() {
		Settings.getSettings().setDialNum(n.string.str);
		Settings.getSettings().setSmsNum(a.string.str);
		Settings.getSettings().write();
		removeThis();
		return true;
	}
}
