package net.muststudio.musicbox;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
	private static Settings obj;

	public static Settings getSettings(Context context) {
		if (null == obj)
			obj = new Settings(context);
		return obj;
	}

	public static Settings getSettings() {
		return obj;
	}

	private SharedPreferences storage;

	private String dialNum;
	private String smsNum;
	// private String dialNum;
	private String name;
	private String age;

	private long firstUseTime;

	private int hourOfDay;
	private int minute;

	public int getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getDialNum() {
		return dialNum;
	}

	public void setDialNum(String dialNum) {
		this.dialNum = dialNum;
	}

	public String getSmsNum() {
		return smsNum;
	}

	public void setSmsNum(String smsNum) {
		this.smsNum = smsNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	private Settings(Context context) {
		storage = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
		int version = storage.getInt("Version", 0x01);
		if (version > 0x01)
			System.exit(0);
		read();
	}

	public void read() {
		dialNum = storage.getString("dialNum", "18202799879");
		smsNum = storage.getString("smsNum", "18202799879");
		name = storage.getString("name", "");
		age = storage.getString("age", "");
		firstUseTime = storage.getLong("firstUseTime", System.currentTimeMillis());
		hourOfDay = storage.getInt("hourOfDay", 18);
		minute = storage.getInt("minute", 0);
	}

	public void write() {
		SharedPreferences.Editor edt = storage.edit();
		edt.putInt("Version", 0x01);
		edt.putString("dialNum", dialNum);
		edt.putString("smsNum", smsNum);
		edt.putString("name", name);
		edt.putString("age", age);
		edt.putLong("firstUseTime", firstUseTime);
		edt.putInt("hourOfDay", hourOfDay);
		edt.putInt("minute", minute).commit();
	}

	public long getFirstUseTime() {
		return firstUseTime;
	}

}
