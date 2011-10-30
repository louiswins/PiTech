import javax.swing.*;
import java.util.*;

/**
 * Main interface of the program. This class just sets up the window and passes
 * control to the active tab.
 *
 * @version 0.2
 */
public class DisplayInterface extends JApplet
{

	/** Default width of applet. */
	private int APPLET_WIDTH = 975;
	/** Default height of applet. */
	private int APPLET_HEIGHT = 525;

	private JTabbedPane tPane;
	private ControlTab controlTab;

	/**
	 * Initializes the Applet with a Pane of two tabs.
	 */
	public void init()
	{
		/* The following is from
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}

		//create a tabbed pane with two tabs
		controlTab = new ControlTab();
		tPane = new JTabbedPane();
		tPane.addTab("Treadmill", controlTab);

		getContentPane().add(tPane);
		setSize(APPLET_WIDTH, APPLET_HEIGHT); //set Applet size
	}
}
