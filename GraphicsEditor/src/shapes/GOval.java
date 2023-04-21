package shapes;

import java.awt.geom.Ellipse2D;

import shapes.GAnchor.EAnchors;

public class GOval extends GShape {

	public GOval() {
	}

	public void movePoint(int x2, int y2) {
		Ellipse2D ellipse = (Ellipse2D) shape;
		if (x2 <= originX && y2 <= originY) {
			ellipse.setFrame(x2, y2, originX - x2, originY - y2);
		} else if (x2 <= originX && y2 >= originY) {
			ellipse.setFrame(x2, originY, originX - x2, y2 - originY);
		} else if (x2 >= originX && y2 <= originY) {
			ellipse.setFrame(originX, y2, x2 - originX, originY - y2);
		} else {
			ellipse.setFrame(ellipse.getX(), ellipse.getY(), x2 - ellipse.getX(), y2 - ellipse.getY());
		}
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		shape = new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1);
		originX = x1;
		originY = y1;
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

	@Override
	public void resizeShape(EAnchors selectedAnchor, int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) shape;
		switch (selectedAnchor) {
		case N:
			ellipse.setFrame(ellipse.getX(), ellipse.getY() + y, ellipse.getWidth(), ellipse.getHeight() - y);
			break;
		case NW:
			ellipse.setFrame(ellipse.getX() + x, ellipse.getY() + y, ellipse.getWidth() - x, ellipse.getHeight() - y);
			break;
		case NE:
			ellipse.setFrame(ellipse.getX(), ellipse.getY() + y, ellipse.getWidth() + x, ellipse.getHeight() - y);
			break;
		case S:
			ellipse.setFrame(ellipse.getX(), ellipse.getY(), ellipse.getWidth(), ellipse.getHeight() + y);
			break;
		case SW:
			ellipse.setFrame(ellipse.getX() + x, ellipse.getY(), ellipse.getWidth() - x, ellipse.getHeight() + y);
			break;
		case SE:
			ellipse.setFrame(ellipse.getX(), ellipse.getY(), ellipse.getWidth() + x, ellipse.getHeight() + y);
			break;
		case W:
			ellipse.setFrame(ellipse.getX() + x, ellipse.getY(), ellipse.getWidth() - x, ellipse.getHeight());
			break;
		case E:
			ellipse.setFrame(ellipse.getX(), ellipse.getY(), ellipse.getWidth() + x, ellipse.getHeight());
			break;
		}
	}

}