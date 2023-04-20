package shapes;

import java.awt.Rectangle;

import shapes.GAnchor.EAnchors;

public class GRectangle extends GShape {

	private int originX, originY;

	public GRectangle() {

	}

	public void movePoint(int x2, int y2) {
		Rectangle rectangle = (Rectangle) shape;
		if (x2 <= originX && y2 <= originY) {
			rectangle.setFrame(x2, y2, originX - x2, originY - y2);
		} else if (x2 <= originX && y2 >= originY) {
			rectangle.setFrame(x2, originY, originX - x2, y2 - originY);
		} else if (x2 >= originX && y2 <= originY) {
			rectangle.setFrame(originX, y2, x2 - originX, originY - y2);
		} else {
			rectangle.setFrame(originX, originY, x2 - originX, y2 - originY);
		}
	}

	public void setOriginPoint() {
		Rectangle rectangle = (Rectangle) shape;
		originX = rectangle.x;
		originY = rectangle.y;
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		shape = new Rectangle(x1, y1, x2 - x1, y2 - y1);
		originX = x1;
		originY = y1;
	}

	@Override
	public GShape clone() {
		return new GRectangle();
	}

	@Override
	public void moveShape(int x2, int y2) {
		Rectangle rectangle = (Rectangle) shape;
		rectangle.x += x2;
		rectangle.y += y2;
		originX = rectangle.x;
		originY = rectangle.y;
	}

	@Override
	public void resizeShape(EAnchors selectedAnchor, int x2, int y2) {
		Rectangle rectangle = (Rectangle) shape;
		switch (selectedAnchor) {
		case N:
			rectangle.setFrame(rectangle.getX(), y2, rectangle.getWidth(),
					rectangle.getHeight() + rectangle.getY() - y2);
			break;
		case NW:
			rectangle.setFrame(x2, y2, rectangle.getWidth() + rectangle.getX() - x2,
					rectangle.getHeight() + rectangle.getY() - y2);
			break;
		case NE:
			rectangle.setFrame(rectangle.getX(), y2, x2 - rectangle.getX(),
					rectangle.getHeight() + rectangle.getY() - y2);
			break;
		case S:
			rectangle.setFrame(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), y2 - rectangle.getY());
			break;
		case SW:
			rectangle.setFrame(x2, rectangle.getY(), rectangle.getX() + rectangle.getWidth() - x2,
					y2 - rectangle.getY());
			break;
		case SE:
			movePoint(x2, y2);
			break;
		case W:
			rectangle.setFrame(x2, rectangle.getY(), rectangle.getWidth() + rectangle.getX() - x2,
					rectangle.getHeight());
			break;
		case E:
			rectangle.setFrame(rectangle.getX(), rectangle.getY(), x2 - rectangle.getX(), rectangle.getHeight());
			break;
		}
	}

}