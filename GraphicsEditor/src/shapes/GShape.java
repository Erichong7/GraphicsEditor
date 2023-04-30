package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

import frames.GToolBar.EColor;
import shapes.GAnchor.EAnchors;

public abstract class GShape {
	protected Shape shape;
	private Graphics2D graphics2d;

	public GShape() {
	}

	abstract public GShape clone();

	public abstract void movePoint(int x2, int y2);

	public abstract void setShape(int x1, int y1, int x2, int y2);

	public abstract void moveShape(int x2, int y2);

	public abstract void resizeShape(EAnchors selectedAnchor, int x, int y);

	public abstract void setPoint(int x, int y);

	public void addPoint(int x2, int y2) {
	}

	public boolean onShape(Point p) {
		if (shape.contains(p.x, p.y)) {
			return true;
		}
		return false;
	}

	private boolean fillClicked = false;
	private EColor selectedColor;

	public void fill() {
		graphics2d.fill(shape);
		fillClicked = true;
	}

	public void setSelectedColor(EColor selectedColor) {
		this.selectedColor = selectedColor;
	}

	public void draw(Graphics graphics) {
		graphics2d = (Graphics2D) graphics;
		if (selectedColor == EColor.eBlack) {
			graphics2d.setColor(Color.BLACK);
		} else if (selectedColor == EColor.eRed) {
			graphics2d.setColor(Color.RED);
		} else if (selectedColor == EColor.eBlue) {
			graphics2d.setColor(Color.BLUE);
		}
		if (fillClicked) {
			fill();
		} else {
			graphics2d.draw(shape);
		}

	}

}