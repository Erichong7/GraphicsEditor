package shapes;

import java.awt.geom.Ellipse2D;

import shapes.GAnchor.EAnchors;

public class GOval extends GShape {

	private int ox, oy;
	private int px, py;

	public GOval() {
	}

	public GShape clone() {
		return new GOval();
	}

	public void movePoint(int x2, int y2) {
		Ellipse2D ellipse = (Ellipse2D) shape;
		if (x2 <= ox && y2 <= oy) {
			ellipse.setFrame(x2, y2, ox - x2, oy - y2);
		} else if (x2 <= ox && y2 >= oy) {
			ellipse.setFrame(x2, oy, ox - x2, y2 - oy);
		} else if (x2 >= ox && y2 <= oy) {
			ellipse.setFrame(ox, y2, x2 - ox, oy - y2);
		} else {
			ellipse.setFrame(ellipse.getX(), ellipse.getY(), x2 - ellipse.getX(), y2 - ellipse.getY());
		}
	}

	public void setShape(int x1, int y1, int x2, int y2) {
		shape = new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1);
		ox = x1;
		oy = y1;
	}

	public void setPoint(int x, int y) {
		px = x;
		py = y;
	}

	public void moveShape(int x, int y) {
		Ellipse2D ellipse2D = (Ellipse2D) shape;
		ellipse2D.setFrame(ellipse2D.getX() + x - px, ellipse2D.getY() + y - py, ellipse2D.getWidth(),
				ellipse2D.getHeight());
		px = x;
		py = y;
	}

	public void resizeShape(EAnchors selectedAnchor, int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) shape;
		int dx = x - px;
		int dy = y - py;

		switch (selectedAnchor) {
		case N:
			ellipse.setFrame(ellipse.getX(), ellipse.getY() + dy, ellipse.getWidth(), ellipse.getHeight() - dy);
			break;
		case NW:
			ellipse.setFrame(ellipse.getX() + dx, ellipse.getY() + dy, ellipse.getWidth() - dx,
					ellipse.getHeight() - dy);
			break;
		case NE:
			ellipse.setFrame(ellipse.getX(), ellipse.getY() + dy, ellipse.getWidth() + dx, ellipse.getHeight() - dy);
			break;
		case S:
			ellipse.setFrame(ellipse.getX(), ellipse.getY(), ellipse.getWidth(), ellipse.getHeight() + dy);
			break;
		case SW:
			ellipse.setFrame(ellipse.getX() + dx, ellipse.getY(), ellipse.getWidth() - dx, ellipse.getHeight() + dy);
			break;
		case SE:
			ellipse.setFrame(ellipse.getX(), ellipse.getY(), ellipse.getWidth() + dx, ellipse.getHeight() + dy);
			break;
		case W:
			ellipse.setFrame(ellipse.getX() + dx, ellipse.getY(), ellipse.getWidth() - dx, ellipse.getHeight());
			break;
		case E:
			ellipse.setFrame(ellipse.getX(), ellipse.getY(), ellipse.getWidth() + dx, ellipse.getHeight());
			break;
		}
		px = x;
		py = y;
	}

}