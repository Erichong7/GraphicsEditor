package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class GAnchor {

	public enum EAnchors {
		N(new Ellipse2D.Double(0, 0, 0, 0)), NW(new Ellipse2D.Double(0, 0, 0, 0)), NE(new Ellipse2D.Double(0, 0, 0, 0)),
		S(new Ellipse2D.Double(0, 0, 0, 0)), SW(new Ellipse2D.Double(0, 0, 0, 0)), SE(new Ellipse2D.Double(0, 0, 0, 0)),
		W(new Ellipse2D.Double(0, 0, 0, 0)), E(new Ellipse2D.Double(0, 0, 0, 0));

		private Ellipse2D anchor;

		private EAnchors(Ellipse2D anchor) {
			this.anchor = anchor;
		}

		public Ellipse2D getEllipse() {
			return anchor;
		}

		public void setEllipse(int x, int y, int l) {
			anchor.setFrame(x, y, l, l);
		}

	}

	private static Rectangle shapeBounds;
	private int l = 12; // Anchor 크기 정하기

	public GAnchor(GShape shape) {
		shapeBounds = shape.shape.getBounds();
	}

	public void setAnchors() {
		for (EAnchors anchor : EAnchors.values()) {
			int x = 0;
			int y = 0;
			switch (anchor) {
			case N:
				x = shapeBounds.x + (shapeBounds.width / 2);
				y = shapeBounds.y;
				break;
			case NW:
				x = shapeBounds.x;
				y = shapeBounds.y;
				break;
			case NE:
				x = shapeBounds.x + shapeBounds.width;
				y = shapeBounds.y;
				break;
			case S:
				x = shapeBounds.x + (shapeBounds.width / 2);
				y = shapeBounds.y + shapeBounds.height;
				break;
			case SW:
				x = shapeBounds.x;
				y = shapeBounds.y + shapeBounds.height;
				break;
			case SE:
				x = shapeBounds.x + shapeBounds.width;
				y = shapeBounds.y + shapeBounds.height;
				break;
			case W:
				x = shapeBounds.x;
				y = shapeBounds.y + (shapeBounds.height / 2);
				break;
			case E:
				x = shapeBounds.x + shapeBounds.width;
				y = shapeBounds.y + (shapeBounds.height / 2);
				break;
			}
			x -= (l / 2);
			y -= (l / 2);
			anchor.setEllipse(x, y, l);
		}
	}

	public EAnchors onShape(Point p) {
		for (EAnchors anchor : EAnchors.values()) {
			Shape shape = anchor.getEllipse();
			if (shape.contains(p.x, p.y)) {
				return anchor;
			}
		}
		return null;
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(shapeBounds);
		for (EAnchors anchor : EAnchors.values()) {
			graphics2d.setColor(Color.WHITE);
			graphics2d.fill(anchor.getEllipse());
			graphics2d.setColor(Color.BLACK);
			graphics2d.draw(anchor.getEllipse());
		}
	}

}
