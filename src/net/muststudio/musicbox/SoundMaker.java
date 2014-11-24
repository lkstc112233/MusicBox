package net.muststudio.musicbox;

import java.util.ArrayList;

import net.muststudio.musicbox.MusicBox.SoundPlayer;
import net.muststudio.musicbox.R;
import net.muststudio.musicbox.util.Waitter;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

public class SoundMaker implements SoundPlayer {
	private static Vibrator vibrator;

	private static SoundPool soundPool;
	private static int alarmId;
	private static int stopId;
	private static boolean isPlaying;
	private static boolean soundMode = false;
	private static ArrayList<Integer> soundIds = new ArrayList<Integer>();

	public static void openSoundMode() {
		soundMode = true;
	}

	public static void closeSoundMode() {
		soundMode = false;
	}

	public static void init(Context context) {
		if (soundPool != null)
			return;
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		soundPool = new SoundPool(30, AudioManager.STREAM_ALARM, 1);
		alarmId = soundPool.load(context, R.raw.air, 1);
		soundIds.add(soundPool.load(context, R.raw.a, 1));
		soundIds.add(soundPool.load(context, R.raw.b, 1));
		soundIds.add(soundPool.load(context, R.raw.c, 1));
		soundIds.add(soundPool.load(context, R.raw.d, 1));
		soundIds.add(soundPool.load(context, R.raw.e, 1));
		soundIds.add(soundPool.load(context, R.raw.f, 1));
		soundIds.add(soundPool.load(context, R.raw.g, 1));
		soundIds.add(soundPool.load(context, R.raw.h, 1));
		soundIds.add(soundPool.load(context, R.raw.i, 1));
		soundIds.add(soundPool.load(context, R.raw.j, 1));
		soundIds.add(soundPool.load(context, R.raw.k, 1));
		soundIds.add(soundPool.load(context, R.raw.cha, 1));
		soundIds.add(soundPool.load(context, R.raw.dang, 1));
		soundIds.add(soundPool.load(context, R.raw.dong, 1));
		soundIds.add(soundPool.load(context, R.raw.yeah, 1));
		soundIds.add(soundPool.load(context, R.raw.yeah, 1));
		soundIds.add(soundPool.load(context, R.raw.yeah, 1));
		soundIds.add(soundPool.load(context, R.raw.yeah, 1));
		soundIds.add(soundPool.load(context, R.raw.bi, 1));
	}

	public static void startAlarm() {
		vibrator.vibrate(new long[] { 0, 1000 }, 0);

		if (!isPlaying) {
			stopId = soundPool.play(alarmId, 1, 1, 1, -1, 1f);
			isPlaying = true;
		}
	}

	public static void stopAlarm() {
		vibrator.cancel();
		soundPool.stop(stopId);
		isPlaying = false;
	}

	private static Waitter soundWait = new Waitter(10);

	public static void playSound(int Id) {
		if (soundWait.isOk())
			if (soundMode)
				soundPool.play(soundIds.get(Id % soundIds.size()), 1, 1, 1, 0, 1f);
	}

	private static void playSoundNo(int i) {
		soundPool.play(soundIds.get(i%soundIds.size()), 1, 1, 1, 0, 1f);

	}

	@Override
	public void playSoundList(int[] ids) {
		for (int i : ids)
			playSoundNo(i);
	}
}
