package shapes;

import java.awt.Rectangle;

import shapes.GAnchor.EAnchors;

public class GRectangle extends GShape {

	private int ox, oy;
	private int px, py;

	public GRectangle() {

	}

	public GShape clone() {
		return new GRectangle();
	}

	public void movePoint(int x2, int y2) {
		Rectangle rectangle = (Rectangle) shape;
		if (x2 <= ox && y2 <= oy) {
			rectangle.setFrame(x2, y2, ox - x2, oy - y2);
		} else if (x2 <= ox && y2 >= oy) {
			rectangle.setFrame(x2, oy, ox - x2, y2 - oy);
		} else if (x2 >= ox && y2 <= oy) {
			rectangle.setFrame(ox, y2, x2 - ox, oy - y2);
		} else {
			rectangle.setFrame(ox, oy, x2 - ox, y2 - oy);
		}
	}

	public void setShape(int x1, int y1, int x2, int y2) {
		shape = new Rectangle(x1, y1, x2 - x1, y2 - y1);
		ox = x1;
		oy = y1;
	}

	public void setPoint(int x, int y) {
		px = x;
		py = y;
	}

	public void moveShape(int x, int y) {
		Rectangle rectangle = (Rectangle) shape;
		rectangle.setLocation(rectangle.x + (x - px), rectangle.y + (y - py));
		px = x;
		py = y;
	}

	public void resizeShape(EAnchors selectedAnchor, int x, int y) {
		Rectangle rectangle = (Rectangle) shape;

		int dx = x - px;
		int dy = y - py;

		switch (selectedAnchor) {
		case N:
			rectangle.setFrame(rectangle.getX(), rectangle.getY() + dy, rectangle.getWidth(),
					rectangle.getHeight() - dy);
			break;
		case NW:
			rectangle.setFrame(rectangle.getX() + dx, rectangle.getY() + dy, rectangle.getWidth() - dx,
					rectangle.getHeight() - dy);
			break;
		case NE:
			rectangle.setFrame(rectangle.getX(), rectangle.getY() + dy, rectangle.getWidth() + dx,
					rectangle.getHeight() - dy);
			break;
		case S:
			rectangle.setFrame(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight() + dy);
			break;
		case SW:
			rectangle.setFrame(rectangle.getX() + dx, rectangle.getY(), rectangle.getWidth() - dx,
					rectangle.getHeight() + dy);
			break;
		case SE:
			rectangle.setFrame(rectangle.getX(), rectangle.getY(), rectangle.getWidth() + dx,
					rectangle.getHeight() + dy);
			break;
		case W:
			rectangle.setFrame(rectangle.getX() + dx, rectangle.getY(), rectangle.getWidth() - dx,
					rectangle.getHeight());
			break;
		case E:
			rectangle.setFrame(rectangle.getX(), rectangle.getY(), rectangle.getWidth() + dx, rectangle.getHeight());
			break;
		}
		px = x;
		py = y;
	}

}