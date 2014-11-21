package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.view.MotionEvent;
import net.muststudio.util.guiitemlib.ui.RelativePoint;
import net.muststudio.util.guiitemlib.ui.SquareGuiItem;
import net.muststudio.util.guiitemlib.ui.TextEditDialogGuiItem;

public class TextEditControl extends SquareGuiItem {
	private TextAdapter text;
	public TextAdapter.StringHolder string;

	public TextEditControl(RelativePoint left_up, RelativePoint right_bottom) {
		this(left_up, right_bottom, "");
	}

	public TextEditControl(RelativePoint left_up, RelativePoint right_bottom, String string) {
		super(left_up, right_bottom);
		text = new TextAdapter(new RelativePoint(mainPosition.getRelativeX(),
				subPosition.getRelativeY()), string, Math.abs(subPosition.getRelativeY()
				- mainPosition.getRelativeY()));
		this.string = text.getHolder();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_UP)
			if (this.isInsideOf(RelativePoint.getRelativePoint(e.getX(), e.getY()))) {
				addTo(new TextEditDialogGuiItem(new TextEditDialogGuiItem.ConfirmOperation() {
					@Override
					public void confirm(String returned) {
						string.str = returned;
					}
				}, string.str));
				return true;
			}
		return false;
	}

	@Override
	public void draw(Canvas canvas) {
		text.draw(canvas);
	}
}
