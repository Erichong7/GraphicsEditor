package shapes;

import java.awt.geom.Ellipse2D;

public class GOval extends GShape {

	public GOval() {
	}

	public void movePoint(int x2, int y2) {
		Ellipse2D ellipse2D = (Ellipse2D) shape;
		ellipse2D.setFrame(ellipse2D.getX(), ellipse2D.getY(), x2 - ellipse2D.getX(), y2 - ellipse2D.getY());

	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		shape = new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1);
	}

	@Override
	public GShape clone() {
		return new GOval();
	}

	@Override
	public void moveShape(int x2, int y2) {
		Ellipse2D ellipse2D = (Ellipse2D) shape;
		ellipse2D.setFrame(ellipse2D.getX() + x2, ellipse2D.getY() + y2, ellipse2D.getWidth(), ellipse2D.getHeight());
	}

}