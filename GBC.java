import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Eases use of GridBagConstraints.
 *
 * This class makes it much easier to create and use GridBagConstraints, making
 * the use of the powerful GridBagLayout much less painful.
 *
 * @version 1.0
 */
public class GBC extends GridBagConstraints {
	public GBC(int x, int y) {
		super();
		gridx = x;
		gridy = y;
	}

	public GBC width(int width) {
		gridwidth = width;
		return this;
	}
	public GBC height(int height) {
		gridheight = height;
		return this;
	}
	public GBC size(int width, int height) {
		gridwidth = width;
		gridheight = height;
		return this;
	}

	public GBC fill(int fill) {
		this.fill = fill;
		return this;
	}

	public GBC pad(int x, int y) {
		ipadx = x;
		ipady = y;
		return this;
	}

	public GBC inset(Insets inset) {
		insets = inset;
		return this;
	}
	public GBC inset(int all) {
		insets = new Insets(all, all, all, all);
		return this;
	}
	public GBC inset(int tb, int lr) {
		insets = new Insets(tb, lr, tb, lr);
		return this;
	}
	public GBC inset(int top, int lr, int bottom) {
		insets = new Insets(top, lr, bottom, lr);
		return this;
	}
	public GBC inset(int top, int left, int bottom, int right) {
		insets = new Insets(top, left, bottom, right);
		return this;
	}

	public GBC anchor(int anchor) {
		this.anchor = anchor;
		return this;
	}

	public GBC weight(double x, double y) {
		weightx = x;
		weighty = y;
		return this;
	}
}
