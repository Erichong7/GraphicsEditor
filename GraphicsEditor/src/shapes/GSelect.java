package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GSelect extends GRectangle {

	public GSelect() {
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setXORMode(graphics2d.getBackground());
		graphics2d.setColor(Color.LIGHT_GRAY);
		graphics2d.fill(shape);
		graphics2d.setColor(Color.BLACK);
		graphics2d.draw(shape);
	}

	public GShape clone() {
		return new GSelect();
	}

}