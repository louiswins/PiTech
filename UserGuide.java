import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * A class to display the User Guide.
 *
 * @version 1.0
 */
public class UserGuide extends JPanel {
	private JTextArea area;
	private JScrollPane scroller;
	public static final String userGuideString = 
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here\n" +
		"User Guide Goes Here\t\t\tUser Guide Goes Here";

	public UserGuide() {
		super(new GridBagLayout());
		area = new JTextArea();
		area.setEditable(false);
		area.setText(userGuideString);

		scroller = new JScrollPane(area);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		add(scroller, new GBC(0,0).weight(1,1).fill(GBC.BOTH));
	}
}
