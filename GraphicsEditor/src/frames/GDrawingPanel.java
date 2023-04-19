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

import shapes.GAnchor;
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
	private GAnchor anchors;
	private Vector<GShape> selectedShapes;

	// association
	private GToolBar toolBar;

	public void setToolbar(GToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public GDrawingPanel() {
		super();
		eDrawingState = EDrawingState.eIdel;
		shapes = new Vector<GShape>();
		selectedShapes = new Vector<GShape>();
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
			graphics2d.setXORMode(getBackground());
			currentShape.draw(graphics2d);
			currentShape = null;
			eDrawingState = EDrawingState.eIdel;
		} else {
			shapes.add(currentShape);
			currentShape = null;
		}
	}

	public void selectShapes() {
		anchors = new GAnchor(currentShape);
		Graphics2D graphics2d = (Graphics2D) getGraphics();
		anchors.setAnchors();
		anchors.draw(graphics2d);
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

		private int initialX;
		private int initialY;

		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdel) {
				if (toolBar.GetESelectedShape() == GToolBar.EShape.eSelect) {
					currentShape = onShape(e.getPoint());
					if (currentShape != null) {
						initialX = e.getX();
						initialY = e.getY();
						selectShapes();
						selectedShapes.add(currentShape);
						eDrawingState = EDrawingState.eMoving;
					} else {
						repaint();
						prepareTransforming(e.getX(), e.getY());
						eDrawingState = EDrawingState.eDrawing;
					}
				} else if (!(toolBar.GetESelectedShape().getGShape() instanceof GPolygon)) {
					repaint();
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eDrawing;
				}
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eMoving) {
				repaint();
				Graphics2D graphics2d = (Graphics2D) getGraphics();
				graphics2d.setXORMode(getBackground());
				if (selectedShapes.size() > 2) {
					for (GShape shape : selectedShapes) {
						shape.draw(graphics2d);
						shape.moveShape(e.getX() - initialX, e.getY() - initialY);
						shape.draw(graphics2d);
					}
				} else {
					currentShape.draw(graphics2d);
					currentShape.moveShape(e.getX() - initialX, e.getY() - initialY);
					currentShape.draw(graphics2d);
					selectedShapes = new Vector<>();
				}
				initialX = e.getX();
				initialY = e.getY();
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
				selectShapes();
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
