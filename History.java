import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * Shows the history of the current running session.
 *
 * @version 0.7
 */
public class History extends JPanel{

	JTextArea area;
	JScrollPane scroller;
	Dimension size;
	History(){
		//size = new Dimension(600,270);
		area = new JTextArea();
		area.setEditable(false);
		//area.setPreferredSize(size);
		add(area);

		scroller = new JScrollPane(area);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setPreferredSize(new Dimension(610,270));
		add(scroller);

		resetHistory();
	}

	/**
	 * Adds to the remembered history data.
	 *
	 * @param line the data to add
	 */
	public void updateHistory(String line) {
		area.append(line + "\n");					//you can use area.setText("text") if you prefer
	}

	/**
	 * Resets the remembered history data.
	 */
	public void resetHistory() {
		area.setText("User History for Current Session\n\n");
	}
}
