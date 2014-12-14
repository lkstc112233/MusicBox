package net.muststudio.musicbox;

public class LowVolSoundPlayer implements SoundPlayer {
	public LowVolSoundPlayer() {
		this(0.3f);
	}

	public LowVolSoundPlayer(float vol) {
		this.vol = vol;
	}

	private float vol;

	@Override
	public void playSoundList(int[] ids) {
		for (int i : ids)
			SoundMaker.playSoundNo(i, vol);
	}

	@Override
	public void playSoundId(int Id) {
		SoundMaker.playSoundNo(Id, vol);
	}
}