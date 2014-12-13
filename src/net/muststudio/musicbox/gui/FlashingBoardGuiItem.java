package net.muststudio.musicbox.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import net.muststudio.musicbox.gui.FlashingBoardGuiItem.GridBoard.Grid;
import net.muststudio.util.guiitemlib.ui.BlockedBackToRemoveGuiItemContainer;
import net.muststudio.util.guiitemlib.ui.GuiItem;

public class FlashingBoardGuiItem extends BlockedBackToRemoveGuiItemContainer {
	public class Point3D {
		public float x, y, z;

		public Point3D(Point3D point) {
			x = point.x;
			y = point.y;
			z = point.z;
		}

		public Point3D(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	public class Camera {
		public float x;
		public float y;
		public float z;
		public float n = 100;

		public void moveOnly(float x, float y, float z) {
			this.x += x;
			this.y += y;
			this.z += z;
		}

		public PointF convert(Point3D pin) {
			float x = pin.x - this.x;
			float y = pin.y - this.y;
			float z = pin.z - this.z;
			PointF pnt = new PointF();
			pnt.x = x * n / z;
			pnt.y = y * n / z;
			return pnt;
		}

		public boolean outOfSight(Point3D left_up) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private Camera camera = new Camera();

	private class SingleGrid extends GuiItem {
		private Grid gridHeld;
		private Paint paint = new Paint();

		public SingleGrid(Grid grid) {
			this.gridHeld = grid;
		}

		private Path getPath() {
			Path path = new Path();
			PointF pnt = camera.convert(gridHeld.left_up);
			path.moveTo(pnt.x, pnt.y);
			pnt = camera.convert(gridHeld.left_down);
			path.lineTo(pnt.x, pnt.y);
			pnt = camera.convert(gridHeld.right_up);
			path.lineTo(pnt.x, pnt.y);
			pnt = camera.convert(gridHeld.right_down);
			path.lineTo(pnt.x, pnt.y);
			return path;
		}

		@Override
		public boolean checkState() {
			if (gridHeld.isFlashing())
				paint.setAlpha(255);
			else
				paint.setAlpha(Math.max(paint.getAlpha() - 20, 0));
			if (outOfSight(camera))
				removeThis();
			return super.checkState();
		}

		private boolean outOfSight(Camera camera) {
			if (camera.outOfSight(gridHeld.left_up) || camera.outOfSight(gridHeld.left_down)
					|| camera.outOfSight(gridHeld.right_up)
					|| camera.outOfSight(gridHeld.right_down))
				return true;
			return false;
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.drawPath(getPath(), paint);
		}
	}

	public class GridBoard {
		public class Grid {
			private Point3D left_up;
			private Point3D left_down;
			private Point3D right_up;
			private Point3D right_down;
			private boolean isFlashing;

			public void setPosition(Point3D startingPoint, Point3D directionPoint1,
					Point3D directionPoint2, Point3D directionPoint3) {
				left_up = new Point3D(startingPoint);
				left_down = new Point3D(directionPoint1);
				right_up = new Point3D(directionPoint2);
				right_down = new Point3D(directionPoint3);
			}

			public boolean isFlashing() {
				boolean temp = isFlashing;
				isFlashing = false;
				return temp;
			}

			public void setFlashing(boolean isFlashing) {
				this.isFlashing = isFlashing;
			}
		}

		private int width;
		private int height;
		private Grid[][] board;
		private Point3D turn_point1;
		private Point3D turn_point2;

		private void turn() {
			// TODO
		}

		public GridBoard(Point3D turnPoint1, Point3D turnPoint2) {
			this(100, 100, turnPoint1, turnPoint2);
		}

		public GridBoard(int width, int height, Point3D turnPoint1, Point3D turnPoint2) {
			this.width = width;
			this.height = height;
			board = new Grid[width][height];
			for (int i = 0; i < width; ++i)
				for (int j = 0; j < height; ++j)
					board[i][j] = new Grid();
			turn_point1 = new Point3D(turnPoint1);
			turn_point2 = new Point3D(turnPoint2);
		}

		public Grid getGrid(int i, int j) {
			return board[i % width][j % height];
		}
	}

	public FlashingBoardGuiItem() {
		super();
		// TODO
	}

	public void addBoard(GridBoard board) {
		// TODO
	}

}
