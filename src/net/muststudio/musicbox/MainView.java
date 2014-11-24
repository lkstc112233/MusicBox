package net.muststudio.musicbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import net.muststudio.musicbox.R;
import net.muststudio.musicbox.gui.BackgroundedGuiItemContainer;
import net.muststudio.musicbox.gui.CircleAdapter;
import net.muststudio.musicbox.gui.CircleWinger;
import net.muststudio.musicbox.gui.CircledIconButtonGuiItem;
import net.muststudio.musicbox.gui.ColoredGuiItemContainer;
import net.muststudio.musicbox.gui.DebugGuiItem;
import net.muststudio.musicbox.gui.ECGViewer;
import net.muststudio.musicbox.gui.EnjoyModeSwitcher;
import net.muststudio.musicbox.gui.HalfCircleAdapter;
import net.muststudio.musicbox.gui.MusicBoxGuiItem;
import net.muststudio.musicbox.gui.SettingsGuiItem;
import net.muststudio.musicbox.gui.RadioScanner;
import net.muststudio.musicbox.gui.RoundRectAdapter;
import net.muststudio.musicbox.gui.SquareAdapter;
import net.muststudio.musicbox.gui.TemperatureBackground;
import net.muststudio.musicbox.gui.TemperatureWave;
import net.muststudio.musicbox.gui.TextEditControl;
import net.muststudio.util.guiitemlib.ui.GenericButton.Task;
import net.muststudio.util.guiitemlib.ui.GuiItemContainer;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.TextPainter;
import net.muststudio.util.guiitemlib.util.BitmapHolder;
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
			addToList(new CircleAdapter(new RelativePoint(0.165, 0.31), new RelativePoint(0.835,
					0.98), Color.rgb(0x34, 0x49, 0x5e)));
			addToList(new SquareAdapter(new RelativePoint(0.495, 0.33), new RelativePoint(0.505,
					0.5), Color.rgb(0xec, 0xf0, 0xf1)));
			addToList(new HalfCircleAdapter(new RelativePoint(0.2, 0.4),
					new RelativePoint(0.8, 1.0), Color.rgb(0x95, 0xa5, 0xa5)));
			addToList(new HalfCircleAdapter(new RelativePoint(0.24, 0.44), new RelativePoint(0.76,
					0.96), Color.rgb(0x34, 0x49, 0x5e)));
			addToList(new CircleAdapter(new RelativePoint(
					0.5 - 0.28 * Math.cos(Math.PI / 16) - 0.02,
					0.7 - 0.28 * Math.sin(Math.PI / 16) - 0.02), new RelativePoint(
					0.5 - 0.28 * Math.cos(Math.PI / 16) + 0.02,
					0.7 - 0.28 * Math.sin(Math.PI / 16) + 0.02), Color.rgb(0xec, 0xf0, 0xf1)));

			int backColor = Color.rgb(0x1d, 0x2a, 0x57);
			addToList(new CircledIconButtonGuiItem(new RelativePoint(0.04, 0.59),
					new RelativePoint(0.16, 0.71), backColor, R.drawable.trueecg)
					.setTask(new Task() {
						@Override
						public void task() {
							GuiItemContainer container = new BackgroundedGuiItemContainer(
									BitmapHolder.getInstance().getBitmap(R.drawable.ecg));

							container.addToList(new ECGViewer(new RelativePoint(0.1, 0.32),
									new RelativePoint(0.9, 1.12)));

							addTo(container);
						}
					}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(0.96, 0.59),
					new RelativePoint(0.84, 0.71), backColor, R.drawable.iecg).setTask(new Task() {
				@Override
				public void task() {
					GuiItemContainer container = new BackgroundedGuiItemContainer(BitmapHolder
							.getInstance().getBitmap(R.drawable.breath_wave));
					container.addToList(new TemperatureWave(new RelativePoint(0.1, 0.32),
							new RelativePoint(0.92, 0.74)));
					addTo(container);
				}
			}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(
					0.5 - 0.2 * Math.sqrt(2) - 0.075, 0.64 - 0.2 * Math.sqrt(2) - 0.075),
					new RelativePoint(0.5 - 0.2 * Math.sqrt(2) + 0.075,
							0.64 - 0.2 * Math.sqrt(2) + 0.075), backColor, R.drawable.temperature)
					.setTask(new Task() {
						@Override
						public void task() {
							GuiItemContainer container = new BackgroundedGuiItemContainer(
									BitmapHolder.getInstance().getBitmap(R.drawable.temperature_bg));
							container.addToList(new TemperatureBackground(new RelativePoint(0.1,
									0.32), new RelativePoint(0.9, 1.12)));

							addTo(container);
						}
					}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(
					0.5 + 0.2 * Math.sqrt(2) - 0.075, 0.64 - 0.2 * Math.sqrt(2) - 0.075),
					new RelativePoint(0.5 + 0.2 * Math.sqrt(2) + 0.075,
							0.64 - 0.2 * Math.sqrt(2) + 0.075), backColor, R.drawable.call)
					.setTask(new Task() {
						@Override
						public void task() {
							Uri uri = Uri.parse("tel:" + Settings.getSettings().getDialNum());
							Intent intent = new Intent(Intent.ACTION_DIAL, uri);
							MusicActivity.getActivity().startActivity(intent);
						}
					}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(0.4, 0.1), new RelativePoint(
					0.6, 0.3), backColor, R.drawable.user).setTask(new Task() {
				@Override
				// TextEditControl
				public void task() {
					class MyCont extends ColoredGuiItemContainer {
						TextEditControl n;
						TextEditControl a;

						public MyCont() {
							super(Color.rgb(0x35, 0x49, 0x90));
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

							addToList(new TextPainter(new RelativePoint(0.075, 0.28),
									new RelativePoint(0.925, 0.18), false)
									.setHorizontalCenter(true).setString("PERSONAL INFORMATION")
									.setVerticalCenter(true).setTextCount(20));

							addToList(new CircledIconButtonGuiItem(new RelativePoint(0.075, 0.29),
									new RelativePoint(0.45, 0.665), 0, R.drawable.babyheadwithbg));
							addToList(new RoundRectAdapter(new RelativePoint(0.5, 0.29),
									new RelativePoint(0.925, 0.38375), Color.rgb(0x16, 0xa0, 0x86)));
							addToList(new TextPainter(new RelativePoint(0.5, 0.29),
									new RelativePoint(0.925, 0.38375), false)
									.setHorizontalCenter(false).setString("NAME")
									.setVerticalCenter(true).setTextCount(10));
							addToList(n = new TextEditControl(new RelativePoint(0.7, 0.2875),
									new RelativePoint(0.925, 0.3835), Settings.getSettings()
											.getName()));
							addToList(new RoundRectAdapter(new RelativePoint(0.5, 0.4775),
									new RelativePoint(0.925, 0.57125), Color.rgb(0x16, 0xa0, 0x86)));
							addToList(new TextPainter(new RelativePoint(0.5, 0.4775),
									new RelativePoint(0.925, 0.57125), false)
									.setHorizontalCenter(false).setString("AGE")
									.setVerticalCenter(true).setTextCount(10));
							addToList(a = new TextEditControl(new RelativePoint(0.7, 0.475),
									new RelativePoint(0.925, 0.571), Settings.getSettings()
											.getAge()));

							addToList(new RoundRectAdapter(new RelativePoint(0.075, 0.69),
									new RelativePoint(0.925, 0.78375), Color.rgb(0x7b, 0x45, 0xa7)));
							addToList(new TextPainter(new RelativePoint(0.075, 0.69),
									new RelativePoint(0.5, 0.78375), false)
									.setHorizontalCenter(false).setString("HEALTH")
									.setVerticalCenter(true).setTextCount(11));
							addToList(new TextPainter(new RelativePoint(0.7, 0.69),
									new RelativePoint(0.9, 0.78375), false)
									.setHorizontalCenter(false).setString("正常")
									.setVerticalCenter(true).setTextCount(3));
							addToList(new RoundRectAdapter(new RelativePoint(0.075, 0.84),
									new RelativePoint(0.925, 0.93375), Color.rgb(0x92, 0x6e, 0xb8)));
							addToList(new TextPainter(new RelativePoint(0.075, 0.84),
									new RelativePoint(0.5, 0.93375), false)
									.setHorizontalCenter(false).setString("HEART RATE")
									.setVerticalCenter(true).setTextCount(11));
							addToList(new TextPainter(new RelativePoint(0.7, 0.84),
									new RelativePoint(0.9, 0.93375), false)
									.setHorizontalCenter(false).setString("正常")
									.setVerticalCenter(true).setTextCount(3));
							addToList(new RoundRectAdapter(new RelativePoint(0.075, 0.99),
									new RelativePoint(0.925, 1.08375), Color.rgb(0x7b, 0x45, 0xa7)));
							addToList(new TextPainter(new RelativePoint(0.075, 0.99),
									new RelativePoint(0.5, 1.08375), false)
									.setHorizontalCenter(false).setString("TEMPERATURE")
									.setVerticalCenter(true).setTextCount(11));
							addToList(new TextPainter(new RelativePoint(0.7, 0.99),
									new RelativePoint(0.9, 1.08375), false)
									.setHorizontalCenter(false).setString("正常")
									.setVerticalCenter(true).setTextCount(3));
							addToList(new RoundRectAdapter(new RelativePoint(0.075, 1.14),
									new RelativePoint(0.925, 1.23375), Color.rgb(0x92, 0x6e, 0xb8)));
							addToList(new TextPainter(new RelativePoint(0.075, 1.14),
									new RelativePoint(0.5, 1.23375), false)
									.setHorizontalCenter(false).setString("USING TIME")
									.setVerticalCenter(true).setTextCount(11));
							long a = System.currentTimeMillis()
									- Settings.getSettings().getFirstUseTime();
							String sdate = "";
							if (a / (365 * 24 * 3600) > 0)
								sdate += a / (365 * 24 * 3600) + "年";
							a %= 365 * 24 * 3600;
							if (a / (30 * 24 * 3600) > 0)
								sdate += a / (30 * 24 * 3600) + "月";
							a %= 30 * 24 * 3600;
							if (a / (24 * 3600) > 0)
								sdate += a / (24 * 3600) + "天";
							addToList(new TextPainter(new RelativePoint(0.5, 1.14),
									new RelativePoint(0.9, 1.23375), false)
									.setHorizontalCenter(false).setString(sdate)
									.setVerticalCenter(true).setTextCount(10));

							addToList(new CircledIconButtonGuiItem(new RelativePoint(0.43, 1.3),
									new RelativePoint(0.57, 1.45), Color.rgb(0x1d, 0x2a, 0x57),
									R.drawable.user));

						}

						@Override
						public boolean onBackKey() {
							Settings.getSettings().setAge(a.string.str);
							Settings.getSettings().setName(n.string.str);
							Settings.getSettings().write();
							return super.onBackKey();
						}
					}
					addTo(new MyCont());
				}
			}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(
					0.5 - 0.2 * Math.sqrt(3) - 0.06, 0.625 + 0.2 - 0.06), new RelativePoint(
					0.5 - 0.2 * Math.sqrt(3) + 0.06, 0.625 + 0.2 + 0.06), backColor,
					R.drawable.setting).setTask(new Task() {
				@Override
				public void task() {
					addTo(new SettingsGuiItem());
				}
			}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(
					0.5 + 0.2 * Math.sqrt(3) - 0.06, 0.625 + 0.2 - 0.06), new RelativePoint(
					0.5 + 0.2 * Math.sqrt(3) + 0.06, 0.625 + 0.2 + 0.06), backColor,
					R.drawable.shield).setTask(new Task() {
				@Override
				public void task() {
					GuiItemContainer container = new BackgroundedGuiItemContainer(BitmapHolder
							.getInstance().getBitmap(R.drawable.distance));

					container.addToList(new RadioScanner(new RelativePoint(0.1, 0.32),
							new RelativePoint(0.9, 1.12)));

					addTo(container);
				}
			}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(0.5 - 0.2 - 0.06,
					0.65 + 0.2 * Math.sqrt(3) - 0.06), new RelativePoint(0.5 - 0.2 + 0.06,
					0.65 + 0.2 * Math.sqrt(3) + 0.06), backColor, R.drawable.alarm_clock)
					.setTask(new Task() {
						@Override
						public void task() {
							addTo(new MusicBoxGuiItem());
						}
					}));
			addToList(new CircledIconButtonGuiItem(new RelativePoint(0.5 + 0.2 - 0.06,
					0.65 + 0.2 * Math.sqrt(3) - 0.06), new RelativePoint(0.5 + 0.2 + 0.06,
					0.65 + 0.2 * Math.sqrt(3) + 0.06), backColor, R.drawable.message)
					.setTask(new Task() {
						@Override
						public void task() {
							Uri smsToUri = Uri.parse("smsto:" + Settings.getSettings().getSmsNum());
							Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,
									smsToUri);
							mIntent.putExtra("sms_body",
									MusicActivity.getActivity().getString(R.string.message_content));
							MusicActivity.getActivity().startActivity(mIntent);
							// addTo(new
							// BackgroundedGuiItemContainer(BitmapHolder.getInstance()
							// .getBitmap(R.drawable.feedback)));
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
