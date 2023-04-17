package shapes;

import java.awt.Polygon;

public class GPolygon extends GShape {

	public GPolygon() {
	}

	public void addPoint(int x2, int y2) {
		Polygon polygon = (Polygon) shape;
		polygon.addPoint(x2, y2);
	}

	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon) shape;
		polygon.xpoints[polygon.npoints - 1] = x;
		polygon.ypoints[polygon.npoints - 1] = y;
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		this.shape = new Polygon();
		Polygon polygon = (Polygon) shape;
		polygon.addPoint(x1, y1);
		polygon.addPoint(x2, y2);
	}

	@Override
	public GShape clone() {
		return new GPolygon();
	}

}
