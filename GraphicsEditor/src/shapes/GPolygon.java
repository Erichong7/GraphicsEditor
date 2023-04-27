package shapes;

import java.awt.Polygon;

import shapes.GAnchor.EAnchors;

public class GPolygon extends GShape {

	private int px, py;

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

	@Override
	public void moveShape(int x, int y) {
		Polygon polygon = (Polygon) shape;
		polygon.translate(x - px, y - py);
		px = x;
		py = y;
	}

	@Override
	public void resizeShape(EAnchors selectedAnchor, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPoint(int x, int y) {
		px = x;
		py = y;
	}

}
