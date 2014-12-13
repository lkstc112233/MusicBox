package net.muststudio.musicbox;

import android.content.Context;
import android.graphics.Color;
import net.muststudio.musicbox.R;
import net.muststudio.musicbox.gui.BackgroundedGuiItemContainer;
import net.muststudio.musicbox.gui.BoyGuiItemContainer;
import net.muststudio.musicbox.gui.CircleAdapter;
import net.muststudio.musicbox.gui.CircleWinger;
import net.muststudio.musicbox.gui.CircledIconButtonGuiItem;
import net.muststudio.musicbox.gui.ColoredGuiItemContainer;
import net.muststudio.musicbox.gui.DebugGuiItem;
import net.muststudio.musicbox.gui.EnjoyModeSwitcher;
import net.muststudio.musicbox.gui.MusicBallBoxGuiItem;
import net.muststudio.musicbox.gui.MusicGridGuiItem;
import net.muststudio.musicbox.gui.PikachuGuiItemContainer;
import net.muststudio.musicbox.gui.ShiningHeartGuiItem;
import net.muststudio.musicbox.gui.SquareAdapter;
import net.muststudio.util.guiitemlib.ui.GenericButton.Task;
import net.muststudio.util.guiitemlib.ui.GuiItemContainer;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.view.SurfaceViewFrame;

public final class MainView extends SurfaceViewFrame {
	private class MainMenu extends ColoredGuiItemContainer {
		public MainMenu() {
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

			addToList(new SquareAdapter(new RelativePoint(0, 0.031, false), new RelativePoint(1,
					0.054, false), Color.rgb(0xf1, 0xc4, 0x0f)));
			addToList(new SquareAdapter(new RelativePoint(0, 0.024, false), new RelativePoint(1,
					0.005, false), Color.rgb(0xf1, 0xc4, 0x0f)));

			addToList(new CircleAdapter(new RelativePoint(0.08, 0.18),
					new RelativePoint(0.92, 1.02), Color.rgb(0x9d, 0xa0, 0x49)));
			addToList(new CircleAdapter(new RelativePoint(0.12, 0.22),
					new RelativePoint(0.88, 0.98), Color.rgb(0xd4, 0xce, 0x42)));

			int backColor = Color.rgb(0x1d, 0x2a, 0x57);
			addToList(new CircledIconButtonGuiItem(new RelativePoint(0.15, 0.25),
					new RelativePoint(0.5, 0.6), backColor, R.drawable.music).setTask(new Task() {
				@Override
				public void task() {
					addTo(new MusicBallBoxGuiItem());
				}
			}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(0.5, 0.6), new RelativePoint(
					0.85, 0.95), backColor, R.drawable.music2).setTask(new Task() {
				@Override
				public void task() {
					addTo(new MusicGridGuiItem());
				}
			}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(0.43, 1.13),
					new RelativePoint(0.57, 1.27), backColor, R.drawable.play).setTask(new Task() {
				@Override
				public void task() {
					addTo(new DebugGuiItem());
				}
			}));

			addToList(new EnjoyModeSwitcher());
			addToList(new BoyGuiItemContainer());
		}
	}

	public MainView(Context context) {
		super(context);
	}

	@Override
	protected void logic() {
	}

	@Override
	protected void createSurface() {
	}

	@Override
	protected void initilize() {
		GuiItemContainer containerMain = new GuiItemContainer();

		this.guiItem = containerMain;
		containerMain.addToList(new MainMenu());
	}
}
