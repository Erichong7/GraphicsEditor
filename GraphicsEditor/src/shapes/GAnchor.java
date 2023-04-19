package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GAnchor {

	public enum EAnchors {
		N(new Rectangle(0, 0, 0, 0)), NW(new Rectangle(0, 0, 0, 0)), NE(new Rectangle(0, 0, 0, 0)),
		S(new Rectangle(0, 0, 0, 0)), SW(new Rectangle(0, 0, 0, 0)), SE(new Rectangle(0, 0, 0, 0)),
		W(new Rectangle(0, 0, 0, 0)), E(new Rectangle(0, 0, 0, 0));

		private Rectangle anchor;

		private EAnchors(Rectangle anchor) {
			this.anchor = anchor;
		}

		public Rectangle getEllipse() {
			return anchor;
		}

		public void setEllipse(int x, int y, int l) {
			anchor.setFrame(x, y, l, l);
		}
	}

	static Rectangle shapeBounds;
	private int l = 8; // Anchor 크기 정하기

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

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(shapeBounds);
		for (EAnchors anchor : EAnchors.values()) {
			graphics2d.setColor(Color.BLACK);
			graphics2d.draw(anchor.getEllipse());
		}
	}

}
