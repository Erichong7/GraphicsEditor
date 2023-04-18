package shapes;

import java.awt.geom.Line2D;

public class GLine extends GShape {

	public GLine() {
	}

	public void movePoint(int x2, int y2) {
		Line2D line2D = (Line2D) shape;
		line2D.setLine(line2D.getX1(), line2D.getY1(), x2, y2);
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		shape = new Line2D.Double(x1, y1, x2, y2);
	}

	@Override
	public GShape clone() {
		return new GLine();
	}

	@Override
	public void moveShape(int x2, int y2) {

	}

//	public boolean onShape(Point p) {
//		return false;
//	}
}