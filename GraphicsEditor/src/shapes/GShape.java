package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

import shapes.GAnchor.EAnchors;

public abstract class GShape {
	protected Shape shape;

	protected int originX;
	protected int originY;

	public GShape() {
	}

	abstract public GShape clone();

	public abstract void movePoint(int x2, int y2);

	public abstract void setShape(int x1, int y1, int x2, int y2);

	public abstract void moveShape(int x2, int y2);

	public abstract void resizeShape(EAnchors selectedAnchor, int x, int y);

	public void addPoint(int x2, int y2) {
	}

	public void setOriginPoint() {
	}

	public boolean onShape(Point p) {
		if (shape.contains(p.x, p.y)) {
			return true;
		}
		return false;
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(shape);
	}

}