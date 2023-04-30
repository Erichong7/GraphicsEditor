package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import shapes.GLine;
import shapes.GOval;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GSelect;
import shapes.GShape;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	private ButtonGroup buttonGroup;
	private ButtonGroup colorButtonGroup;
	public JButton undoButton;
	public JButton redoButton;
	public JButton clearButton;
	public JButton fillButton;
	public JRadioButton redColorButton;

	public enum EShape {
		eSelect("Select", new GSelect()), eRectangle("Rectangle", new GRectangle()), eOval("Oval", new GOval()),
		eLine("Line", new GLine()), ePolygon("Polygon", new GPolygon());

		private String name;
		private GShape gShape;

		private EShape(String name, GShape gShape) {
			this.name = name;
			this.gShape = gShape;
		}

		public String getName() {
			return this.name;
		}

		public GShape getGShape() {
			return this.gShape;
		}
	}

	private EShape eSelectedShape;

	public EShape getESelectedShape() {
		return eSelectedShape;
	}

	public void resetESelectedShape() {
		buttonGroup.clearSelection();
		eSelectedShape = EShape.eSelect;
	}

	public enum EColor {
		eBlack("Black"), eRed("Red"), eBlue("Blue");

		private String name;

		private EColor(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	private EColor eSelectedColor;

	public EColor getESelectedColor() {
		return eSelectedColor;
	}

	public GToolBar() {
		super();
		setFloatable(false); // 드래그 못하게

		ActionHandler ActionHandler = new ActionHandler();
		buttonGroup = new ButtonGroup();

		for (EShape eButtonShape : EShape.values()) {
			if (eButtonShape != EShape.eSelect) {
				JRadioButton buttonShape = new JRadioButton(eButtonShape.getName());
				add(buttonShape);
				buttonShape.setActionCommand(eButtonShape.toString());
				buttonShape.addActionListener(ActionHandler);
				buttonGroup.add(buttonShape);
			}
		}

		addSeparator();

		undoButton = new JButton(new ImageIcon("Graphic/undo.png"));
		add(undoButton);

		redoButton = new JButton(new ImageIcon("Graphic/redo.png"));
		add(redoButton);

		addSeparator();

		clearButton = new JButton("CLEAR");
		add(clearButton);

		fillButton = new JButton("FILL");
		add(fillButton);

		addSeparator();

		colorButtonGroup = new ButtonGroup();

		for (EColor eColor : EColor.values()) {
			JRadioButton buttonShape = new JRadioButton(eColor.getName());
			add(buttonShape);
			buttonShape.setActionCommand(eColor.toString());
			buttonShape.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eSelectedColor = EColor.valueOf(e.getActionCommand());
				}
			});
			if (eColor == EColor.eBlack) {
				buttonShape.setSelected(true);
			}
			colorButtonGroup.add(buttonShape);
		}

		resetESelectedShape();
	}

	class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			eSelectedShape = EShape.valueOf(e.getActionCommand());
		}

	}

}
