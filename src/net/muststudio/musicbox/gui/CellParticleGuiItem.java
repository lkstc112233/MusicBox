package net.muststudio.musicbox.gui;

import net.muststudio.util.guiitemlib.ui.GuiItem;
import net.muststudio.util.guiitemlib.ui.GuiItemContainer;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class CellParticleGuiItem extends GuiItemContainer {
	private static float t = 0;

	private class SingleParticleGuiItem extends GuiItem {
		private RelativePoint point;
		private Paint paint = new Paint();
		private float dx;
		private float dy;
		private float vx;
		private float vy;
		private float radius;

		private float ax() {
			return (float) Math.cos(t);
		}

		private float ay() {
			return (float) Math.sin(t);
		}

		public void addT() {
			t += Math.PI / 12;
		}

		private SingleParticleGuiItem(RelativePoint point) {
			this.point = point;
			paint.setColor(Color.WHITE);
			dx = dy = 0;
			vx = (float) (Math.random() * 10 - 5);
			vy = (float) (Math.random() * 10 - 5);
			radius = (float) (Math.random() * 5 + 3);
			addT();
		}

		private void move() {
			dx += vx + ax() / 2;
			dy += vy + ay() / 2;
			vx += ax();
			vy += ay();
		}

		@Override
		public boolean checkState() {
			move();
			paint.setAlpha(Math.max(0, paint.getAlpha() - 5));
			if (paint.getAlpha() <= 0)
				removeThis();
			return true;
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.drawCircle(point.getScreenX() + dx, point.getScreenY() + dy, radius, paint);
		}
	}

	public CellParticleGuiItem(RelativePoint pnt) {
		double size = Math.random() * 20 + 20;
		for (int i = 0; i < size; ++i)
			addToList(new SingleParticleGuiItem(pnt));

	}

	@Override
	public boolean checkState() {
		boolean bool = super.checkState();
		if (guiItems.size() == 0)
			removeThis();
		return bool;
	}

}