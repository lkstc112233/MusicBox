package net.muststudio.musicbox.gui;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import net.muststudio.musicbox.MusicActivity;
import net.muststudio.musicbox.R;
import net.muststudio.util.guiitemlib.ui.BitmapPainter;
import net.muststudio.util.guiitemlib.ui.BlockedBackToRemoveGuiItemContainer;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;
import net.muststudio.util.guiitemlib.util.BitmapHolder;

public class PikachuGuiItemContainer extends BlockedBackToRemoveGuiItemContainer {
	private class PikachuGuiItem extends GuiItem {
		private Matrix scale;
		private ArrayList<Integer> res;
		private int index;

		public PikachuGuiItem() {
			float scw = ScreenInfo.getScreenInfo().getScreenWidth();
			float sch = ScreenInfo.getScreenInfo().getScreenHeight();
			float x = (float) (Math.random() * scw - scw / 2);
			float y = (float) (Math.random() * sch - sch / 2);
			float scale = (float) (1 / (Math.random() * 1.2 + 1));
			this.scale = new Matrix();
			this.scale.setScale(scale, scale);
			this.scale.postTranslate(x, y);
			res = new ArrayList<Integer>();
			res.add(R.drawable.pikachu00);
			res.add(R.drawable.pikachu01);
			res.add(R.drawable.pikachu02);
			res.add(R.drawable.pikachu03);
			res.add(R.drawable.pikachu04);
			res.add(R.drawable.pikachu05);
			res.add(R.drawable.pikachu06);
			res.add(R.drawable.pikachu07);
			res.add(R.drawable.pikachu08);
			res.add(R.drawable.pikachu09);
			res.add(R.drawable.pikachu10);
			res.add(R.drawable.pikachu11);
			res.add(R.drawable.pikachu12);
			res.add(R.drawable.pikachu13);
			res.add(R.drawable.pikachu14);
			res.add(R.drawable.pikachu15);
			res.add(R.drawable.pikachu16);
			res.add(R.drawable.pikachu17);
			res.add(R.drawable.pikachu18);
			res.add(R.drawable.pikachu19);
			res.add(R.drawable.pikachu20);
			res.add(R.drawable.pikachu21);
			res.add(R.drawable.pikachu22);
			res.add(R.drawable.pikachu23);
			res.add(R.drawable.pikachu24);
			res.add(R.drawable.pikachu25);
			res.add(R.drawable.pikachu26);
			res.add(R.drawable.pikachu27);
			res.add(R.drawable.pikachu28);
			res.add(R.drawable.pikachu29);
			res.add(R.drawable.pikachu30);
			res.add(R.drawable.pikachu31);
			res.add(R.drawable.pikachu32);
			res.add(R.drawable.pikachu33);
			res.add(R.drawable.pikachu34);
			res.add(R.drawable.pikachu35);
			res.add(R.drawable.pikachu36);
			res.add(R.drawable.pikachu37);
			index = (int) (Math.random() * res.size());
		}

		@Override
		public void draw(Canvas canvas) {
			scale.postTranslate((float) (Math.random() * 50 - 25),
					(float) (Math.random() * 50 - 25));
			canvas.drawBitmap(BitmapHolder.getInstance().getBitmap(res.get(index)), scale, null);
			index += 1;
			index %= res.size();
		}
	}

	private MediaPlayer mediaPlayer;

	public PikachuGuiItemContainer() {
		addToList(new BitmapPainter(new RelativePoint(0, 0), new RelativePoint(1, 0, false, true))
				.setBitmap(BitmapHolder.getInstance().getBitmap(R.drawable.smc)));
		do
			addToList(new PikachuGuiItem());
		while (Math.random() > 0.5);
		mediaPlayer = MediaPlayer.create(MusicActivity.getActivity(), R.raw.kuai_san);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
	}

	@Override
	public void removeThis() {
		mediaPlayer.stop();
		super.removeThis();
	}
}
