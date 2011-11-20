import javax.swing.*;
import java.util.*;

/**
 * Main interface of the program. This class just sets up the window and passes
 * control to the active tab.
 *
 * @version 0.5
 */
public class DisplayInterface extends JFrame
{

	/** Default width. */
	private int APP_WIDTH = 800;
	/** Default height. */
	private int APP_HEIGHT = 400;

	private ControlTab controlTab;
	private UserGuide userGuide;
	private History history;

	/**
	 * Initializes the App with a Pane of two tabs.
	 */
	public DisplayInterface()
	{
		super("PiTech Treadmill Simulator");
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
		history = new History();
		controlTab = new ControlTab(history);
		userGuide = new UserGuide();

		JTabbedPane tPane = new JTabbedPane();
		tPane.addTab("Treadmill", controlTab);
		tPane.addTab("User Guide", userGuide);
		tPane.addTab("History", history);

		getContentPane().add(tPane);
		setSize(APP_WIDTH, APP_HEIGHT);
		//pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new DisplayInterface();
	}
}

