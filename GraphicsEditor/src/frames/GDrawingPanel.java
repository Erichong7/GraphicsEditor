package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JPanel;

import shapes.GAnchor;
import shapes.GAnchor.EAnchors;
import shapes.GPolygon;
import shapes.GShape;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private enum EDrawingState {
		eIdel, eDrawing, eMoving, eResizing, eRotating, eSelecting
	}

	private EDrawingState eDrawingState;
	static Stack<GShape> shapes;
	private GShape currentShape;
	private GAnchor anchors;
	private EAnchors selectedAnchor;
	private Queue<GShape> undoShapes;
	private boolean fillClicked;

	// association
	private GToolBar toolBar;

	public void setToolbar(GToolBar toolBar) {
		this.toolBar = toolBar;
		toolBar.undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shapes.size() != 0) {
					undoShapes.add(shapes.pop());
					repaint();
				}
			}
		});
		toolBar.redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (undoShapes.size() > 0) {
					shapes.add(undoShapes.poll());
					repaint();
				}
			}
		});
		toolBar.clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapes.clear();
				repaint();
			}
		});
		toolBar.fillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillClicked = true;
			}
		});
	}

	public GDrawingPanel() {
		super();
		eDrawingState = EDrawingState.eIdel;
		shapes = new Stack<GShape>();
		undoShapes = new LinkedList<>();

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

	public boolean onShape(Point point) {
		for (GShape shape : shapes) {
			if (shape.onShape(point)) {
				currentShape = shape;
				return true;
			}
		}
		return false;
	}

	public void prepareTransforming(int x, int y) {
		if (eDrawingState == EDrawingState.eDrawing) {
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			currentShape = toolBar.getESelectedShape().getGShape().clone();
			currentShape.setSelectedColor(toolBar.getESelectedColor());
			currentShape.setShape(x, y, x, y);
		} else if (eDrawingState == EDrawingState.eSelecting) {
			currentShape = toolBar.getESelectedShape().getGShape().clone();
			currentShape.setShape(x, y, x, y);
		} else if (eDrawingState == EDrawingState.eMoving) {
			currentShape.setPoint(x, y);
		} else if (eDrawingState == EDrawingState.eResizing) {
			currentShape.setPoint(x, y);
		}
	}

	public void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(getBackground());
		if (eDrawingState == EDrawingState.eDrawing) {
			currentShape.draw(graphics2d);
			currentShape.movePoint(x, y);
			currentShape.draw(graphics2d);
		} else if (eDrawingState == EDrawingState.eSelecting) {
			currentShape.draw(graphics2d);
			currentShape.movePoint(x, y);
			currentShape.draw(graphics2d);
		} else if (eDrawingState == EDrawingState.eMoving) {
			Dimension d = getSize();
			if (x > 0 && x < d.width && y > 0 && y < d.height) {
				currentShape.draw(graphics2d);
				currentShape.moveShape(x, y);
				currentShape.draw(graphics2d);
			}
		} else if (eDrawingState == EDrawingState.eResizing) {
			currentShape.resizeShape(selectedAnchor, x, y);
		}
	}

	public void continueTransforming(int x, int y) {
		currentShape.addPoint(x, y);
	}

	public void finalizeTransforming(int x, int y) {
		if (eDrawingState == EDrawingState.eDrawing) {
			shapes.add(currentShape);
			currentShape = null;
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else if (eDrawingState == EDrawingState.eSelecting) {
			Graphics2D graphics2d = (Graphics2D) this.getGraphics();
			graphics2d.setXORMode(getBackground());
			currentShape.draw(graphics2d);
			currentShape = null;
		} else if (eDrawingState == EDrawingState.eMoving) {
			currentShape = null;
		} else if (eDrawingState == EDrawingState.eResizing) {
			drawAnchor(currentShape);
		}
	}

	public void drawAnchor(GShape shape) {
		anchors = new GAnchor(shape);
		Graphics2D graphics2d = (Graphics2D) getGraphics();
		anchors.setAnchors();
		anchors.draw(graphics2d);
	}

	private class MouseEventHandler implements MouseListener, MouseMotionListener {

		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdel) {
				if (toolBar.getESelectedShape() == GToolBar.EShape.eSelect) {
					onShape(e.getPoint());
					if (currentShape != null) { // move
						if (fillClicked) {
							currentShape.setSelectedColor(toolBar.getESelectedColor());
							currentShape.fill();
							repaint();
							fillClicked = false;
						}
						drawAnchor(currentShape);
						selectedAnchor = anchors.onShape(e.getPoint());
						if (selectedAnchor != null) {
							eDrawingState = EDrawingState.eResizing;
							prepareTransforming(e.getX(), e.getY());
						} else if (currentShape.onShape(e.getPoint())) {
							eDrawingState = EDrawingState.eMoving;
							prepareTransforming(e.getX(), e.getY());
						} else {
							repaint();
							currentShape = null;
						}
					} else {
						repaint();
						eDrawingState = EDrawingState.eSelecting;
						prepareTransforming(e.getX(), e.getY());
					}
				} else if (!(toolBar.getESelectedShape().getGShape() instanceof GPolygon)) {
					repaint();
					eDrawingState = EDrawingState.eDrawing;
					prepareTransforming(e.getX(), e.getY());
				}
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eMoving) {
				repaint();
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eSelecting) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eResizing) {
				repaint();
				keepTransforming(e.getX(), e.getY());
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if (!(toolBar.getESelectedShape().getGShape() instanceof GPolygon)) {
					finalizeTransforming(e.getX(), e.getY());
					toolBar.resetESelectedShape();
					eDrawingState = EDrawingState.eIdel;
				}
			} else if (eDrawingState == EDrawingState.eMoving) {
				drawAnchor(currentShape);
				eDrawingState = EDrawingState.eIdel;
			} else if (eDrawingState == EDrawingState.eSelecting) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdel;
			} else if (eDrawingState == EDrawingState.eResizing) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdel;
			}
		}

		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if (toolBar.getESelectedShape().getGShape() instanceof GPolygon)
					keepTransforming(e.getX(), e.getY());
			}
			if (onShape(e.getPoint())) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

		public void mouseClicked(MouseEvent e) {
			if (toolBar.getESelectedShape().getGShape() instanceof GPolygon) {
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
				if (toolBar.getESelectedShape().getGShape() instanceof GPolygon) {
					eDrawingState = EDrawingState.eDrawing;
					prepareTransforming(e.getX(), e.getY());
				}
			} else if (eDrawingState == EDrawingState.eDrawing) {
				if (toolBar.getESelectedShape().getGShape() instanceof GPolygon) {
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
