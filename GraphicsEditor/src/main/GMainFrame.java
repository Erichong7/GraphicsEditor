package main;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import frames.GDrawingPanel;
import frames.GMenuBar;
import frames.GToolBar;

public class GMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private GDrawingPanel drawingPanel;
	private GMenuBar menuBar;
	private GToolBar toolBar;

	public GMainFrame() {
		// attributes
		setTitle("Title");
		setLocationRelativeTo(null);
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// components 자식 부품같은 것
		BorderLayout layout = new BorderLayout();
		setLayout(layout);

		menuBar = new GMenuBar();
		setJMenuBar(menuBar);

		toolBar = new GToolBar();
		add(toolBar, BorderLayout.NORTH);

		drawingPanel = new GDrawingPanel();
		add(drawingPanel, BorderLayout.CENTER);

		// association
		drawingPanel.setToolbar(toolBar);
	}

}
