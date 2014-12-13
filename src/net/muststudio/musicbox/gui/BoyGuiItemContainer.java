package net.muststudio.musicbox.gui;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Matrix;
import net.muststudio.musicbox.R;
import net.muststudio.util.guiitemlib.ui.BitmapPainter;
import net.muststudio.util.guiitemlib.ui.BlockedBackToRemoveGuiItemContainer;
import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.ScreenInfo;
import net.muststudio.util.guiitemlib.util.BitmapHolder;

public class BoyGuiItemContainer extends BlockedBackToRemoveGuiItemContainer {
	private class BoyGuiItem extends GuiItem {
		private Matrix scale;
		private ArrayList<Integer> res;
		private int index;

		public BoyGuiItem() {
			float scw = ScreenInfo.getScreenInfo().getScreenWidth();
			float sch = ScreenInfo.getScreenInfo().getScreenHeight();
			float x = (float) (Math.random() * scw - scw / 4);
			float y = (float) (Math.random() * sch - sch / 2);
			float scale = (float) (1 / (Math.random() * 1.2 + 1));
			this.scale = new Matrix();
			this.scale.setScale(scale, scale);
			this.scale.postTranslate(x, y);
			res = new ArrayList<Integer>();
			res.add(R.drawable.gymnastics00);
			res.add(R.drawable.gymnastics01);
			res.add(R.drawable.gymnastics02);
			res.add(R.drawable.gymnastics03);
			res.add(R.drawable.gymnastics04);
			res.add(R.drawable.gymnastics05);
			res.add(R.drawable.gymnastics06);
			res.add(R.drawable.gymnastics07);
			res.add(R.drawable.gymnastics08);
			res.add(R.drawable.gymnastics09);
			res.add(R.drawable.gymnastics10);
			res.add(R.drawable.gymnastics11);
			res.add(R.drawable.gymnastics12);
			res.add(R.drawable.gymnastics13);
			res.add(R.drawable.gymnastics14);
			res.add(R.drawable.gymnastics15);
			res.add(R.drawable.gymnastics16);
			res.add(R.drawable.gymnastics17);
			res.add(R.drawable.gymnastics18);
			res.add(R.drawable.gymnastics19);
			res.add(R.drawable.gymnastics20);
			res.add(R.drawable.gymnastics21);
			res.add(R.drawable.gymnastics22);
			res.add(R.drawable.gymnastics23);
			res.add(R.drawable.gymnastics24);
			res.add(R.drawable.gymnastics25);
			res.add(R.drawable.gymnastics26);
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

	public BoyGuiItemContainer() {
		addToList(new BitmapPainter(new RelativePoint(0, 0), new RelativePoint(1, 0, false, true))
				.setBitmap(BitmapHolder.getInstance().getBitmap(R.drawable.photo)));
		do
			addToList(new BoyGuiItem());
		while (Math.random() > 0.5);
	}
}
