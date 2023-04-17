package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import shapes.GPolygon;
import shapes.GShape;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private enum EDrawingState {
		eIdel, eDrawing, eMoving, eResizing, eRotating
	}

	private EDrawingState eDrawingState;
	private Vector<GShape> shapes;
	private GShape currentShape;

	// association
	private GToolBar toolBar;

	public void setToolbar(GToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public GDrawingPanel() {
		super();
		eDrawingState = EDrawingState.eIdel;
		shapes = new Vector<GShape>();
		currentShape = null;

		setBackground(Color.white);
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		addMouseListener(mouseEventHandler);
		addMouseMotionListener(mouseEventHandler);

	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		for (GShape shape : shapes) {
			shape.draw(graphics);
		}
	}

	public GShape onShape(Point point) {
		for (GShape shape : shapes) {
			if (shape.onShape(point)) {
				return shape;
			}
		}
		return null;
	}

	public void prepareTransforming(int x, int y) {
		currentShape = toolBar.GetESelectedShape().getGShape().clone();
		currentShape.setShape(x, y, x, y);
	}

	public void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(getBackground());
		currentShape.draw(graphics2d);
		currentShape.movePoint(x, y);
		currentShape.draw(graphics2d);
	}

	public void continueTransforming(int x, int y) {
		currentShape.addPoint(x, y);
	}

	public void finalizeTransforming(int x, int y) {
		if (toolBar.GetESelectedShape() == GToolBar.EShape.eSelect) {
			Graphics2D graphics2d = (Graphics2D) this.getGraphics();
			currentShape.draw(graphics2d);
			currentShape = null;
			eDrawingState = EDrawingState.eIdel;
		} else {
			shapes.add(currentShape);
			currentShape = null;
		}
	}

//	private class Transformer {
//		public void prepareTransforming() {
//
//		}
//
//		public void keepTransforming() {
//
//		}
//
//		public void finalizeTransforming() {
//
//		}
//	}

	private class MouseEventHandler implements MouseListener, MouseMotionListener {

		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdel) {
				if (toolBar.GetESelectedShape() == GToolBar.EShape.eSelect) {
					currentShape = onShape(e.getPoint());
					if (currentShape != null) {
						eDrawingState = EDrawingState.eMoving;
					}
				} else {
					if (!(toolBar.GetESelectedShape().getGShape() instanceof GPolygon)) {
						prepareTransforming(e.getX(), e.getY());
						eDrawingState = EDrawingState.eDrawing;
					}

				}
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eMoving) {
				// move
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if (!(toolBar.GetESelectedShape().getGShape() instanceof GPolygon)) {
					finalizeTransforming(e.getX(), e.getY());
					toolBar.resetESelectedShape();
					eDrawingState = EDrawingState.eIdel;
				}
			} else if (eDrawingState == EDrawingState.eMoving) {
				eDrawingState = EDrawingState.eIdel;
			}
		}

		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		public void mouseClicked(MouseEvent e) {
			if (toolBar.GetESelectedShape().getGShape() instanceof GPolygon) {
				if (e.getClickCount() == 1) {
					mouse1Clicked(e);
				} else if (e.getClickCount() == 2) {
					mousr2Clicked(e);
				}
			}

		}

		private void mousr2Clicked(MouseEvent e) {
			finalizeTransforming(e.getX(), e.getY());
			toolBar.resetESelectedShape();
			eDrawingState = EDrawingState.eIdel;
		}

		private void mouse1Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdel) {
				if (toolBar.GetESelectedShape().getGShape() instanceof GPolygon) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eDrawing;
				}
			} else if (eDrawingState == EDrawingState.eDrawing) {
				if (toolBar.GetESelectedShape().getGShape() instanceof GPolygon) {
					continueTransforming(e.getX(), e.getY());
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

	}
}
