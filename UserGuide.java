import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.io.IOException;

/**
 * A class to display the User Guide.
 *
 * @version 1.0
 */
public class UserGuide extends JPanel {
	private JTextComponent area;
	private JScrollPane scroller;
	private static final java.net.URL userGuideURL = UserGuide.class.getResource("rsc/userguide.html");
	private static final String errorString = "Error loading User Guide.";

	public UserGuide() {
		super(new GridBagLayout());
		/* If the HTML User Guide is not available, we display an error
		 * message. */
		try {
			area = new JEditorPane(userGuideURL);
		} catch (IOException ioe) {
			area = new JTextArea(errorString);
		}
		area.setEditable(false);

		scroller = new JScrollPane(area);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		add(scroller, new GBC(0,0).weight(1,1).fill(GBC.BOTH));
	}
}
