package net.muststudio.musicbox;

import java.util.ArrayList;

import net.muststudio.musicbox.R;
import net.muststudio.musicbox.util.Waitter;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundMaker implements SoundPlayer {

	private static SoundPool soundPool;
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

		soundPool = new SoundPool(30, AudioManager.STREAM_ALARM, 1);
		soundIds.add(soundPool.load(context, R.raw.c2, 1));
		soundIds.add(soundPool.load(context, R.raw.d2, 1));
		soundIds.add(soundPool.load(context, R.raw.e2, 1));
		soundIds.add(soundPool.load(context, R.raw.f2, 1));
		soundIds.add(soundPool.load(context, R.raw.g2, 1));
		soundIds.add(soundPool.load(context, R.raw.a3, 1));
		soundIds.add(soundPool.load(context, R.raw.b3, 1));
		soundIds.add(soundPool.load(context, R.raw.c3, 1));
		soundIds.add(soundPool.load(context, R.raw.d3, 1));
		soundIds.add(soundPool.load(context, R.raw.e3, 1));
		soundIds.add(soundPool.load(context, R.raw.f3, 1));
		soundIds.add(soundPool.load(context, R.raw.g3, 1));
		soundIds.add(soundPool.load(context, R.raw.a4, 1));
		soundIds.add(soundPool.load(context, R.raw.b4, 1));
		soundIds.add(soundPool.load(context, R.raw.c4, 1));
		soundIds.add(soundPool.load(context, R.raw.d4, 1));
		soundIds.add(soundPool.load(context, R.raw.e4, 1));
		soundIds.add(soundPool.load(context, R.raw.f4, 1));
		soundIds.add(soundPool.load(context, R.raw.g4, 1));
		soundIds.add(soundPool.load(context, R.raw.a5, 1));
		soundIds.add(soundPool.load(context, R.raw.b5, 1));
	}

	private static Waitter soundWait = new Waitter(10);

	public static void playSound(int Id) {
		if (soundWait.isOk())
			if (soundMode)
				soundPool.play(soundIds.get(Id % soundIds.size()), 1, 1, 1, 0, 1f);
	}

	private static void playSoundNo(int i) {
		soundPool.play(soundIds.get(i % soundIds.size()), 1, 1, 1, 0, 1f);

	}

	@Override
	public void playSoundList(int[] ids) {
		for (int i : ids)
			playSoundNo(i);
	}

	public void playSoundId(int Id) {
		playSound(Id);
	}
}
