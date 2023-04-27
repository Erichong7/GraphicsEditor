package shapes;

import java.awt.Point;
import java.awt.geom.Line2D;

import shapes.GAnchor.EAnchors;

public class GLine extends GShape {

	private int px, py;
	private int ox1, ox2, oy1, oy2;

	public GLine() {
	}

	public GShape clone() {
		return new GLine();
	}

	public void movePoint(int x2, int y2) {
		Line2D line2D = (Line2D) shape;
		line2D.setLine(line2D.getX1(), line2D.getY1(), x2, y2);
		ox2 = x2;
		oy2 = y2;
	}

	public void setShape(int x1, int y1, int x2, int y2) {
		shape = new Line2D.Double(x1, y1, x2, y2);
		ox1 = x1;
		oy1 = y1;
	}

	public void setPoint(int x, int y) {
		px = x;
		py = y;
	}

	public void moveShape(int x2, int y2) {
		Line2D line2D = (Line2D) shape;
		line2D.setLine(line2D.getX1() + x2, line2D.getY1() + y2, line2D.getX2() + x2, line2D.getY2() + y2);
	}

	public void resizeShape(EAnchors selectedAnchor, int x, int y) {
		// TODO Auto-generated method stub

	}

	public boolean onShape(Point p) {
		Line2D line2D = (Line2D) shape;
		if (line2D.ptSegDist(p) < 8) {
			return true;
		}
		return false;
	}

}