package frames;
import javax.swing.JMenuBar;

public class GMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public GMenuBar() {
		GFileMenu fileMenu = new GFileMenu("File");
		add(fileMenu);
	}
}
