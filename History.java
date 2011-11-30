import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * Shows the history of the current running session.
 *
 * @version 0.8
 */
public class History extends JPanel {
	private JTextArea area;
	private JScrollPane scroller;
	private int nlines;

	History() {
		area = new JTextArea();
		area.setFont(new Font("Sans", Font.PLAIN, 14));
		area.setEditable(false);

		scroller = new JScrollPane(area);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setPreferredSize(new Dimension(610,270));
		add(scroller);

		resetHistory();
	}

	/** Prints the column headers */
	private void showHeaders() {
		updateHistory("Distance\tTime\tSpeed\tIncline\tCalories");
		nlines = 0;
	}

	/**
	 * Adds to the remembered history data.
	 *
	 * @param line the data to add
	 */
	public void updateHistory(String line) {
		area.append("\n" + line);
		if (++nlines == 15) {
			showHeaders();
		}
		area.setCaretPosition(area.getDocument().getLength());
	}

	/**
	 * Resets the remembered history data.
	 */
	public void resetHistory() {
		area.setText("User History for Current Session\n");
		showHeaders();
	}
}
