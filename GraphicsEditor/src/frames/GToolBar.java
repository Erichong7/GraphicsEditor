package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
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
	private JRadioButton selectButton;
	public JButton undoButton;
	public JButton redoButton;

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

	public EShape GetESelectedShape() {
		return eSelectedShape;
	}

	public void resetESelectedShape() {
		selectButton = (JRadioButton) getComponent(EShape.eSelect.ordinal());
		selectButton.setSelected(true);
		eSelectedShape = EShape.eSelect;
	}

	public GToolBar() {
		super();
		ActionHandler ActionHandler = new ActionHandler();
		buttonGroup = new ButtonGroup();

		for (EShape eButtonShape : EShape.values()) {
			JRadioButton buttonShape = new JRadioButton(eButtonShape.getName());
			add(buttonShape);
			buttonShape.setActionCommand(eButtonShape.toString());
			buttonShape.addActionListener(ActionHandler);
			buttonGroup.add(buttonShape);
		}

		undoButton = new JButton("UNDO");
		add(undoButton);

		redoButton = new JButton("REDO");
		add(redoButton);

		resetESelectedShape();
	}

	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			eSelectedShape = EShape.valueOf(e.getActionCommand());
		}

	}

}
