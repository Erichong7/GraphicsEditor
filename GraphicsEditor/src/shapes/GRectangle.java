package shapes;

import java.awt.Rectangle;

public class GRectangle extends GShape {

	public GRectangle() {

	}

	public void movePoint(int x2, int y2) {
		Rectangle rectangle = (Rectangle) shape;
		rectangle.setFrame(rectangle.getX(), rectangle.getY(), x2 - rectangle.getX(), y2 - rectangle.getY());

	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		shape = new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}

	@Override
	public GShape clone() {
		return new GRectangle();
	}

}