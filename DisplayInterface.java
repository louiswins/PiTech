// Assignment #: 6
//         Name:
//    StudentID:
//      Lecture:
//  Description: The Assignment 6 class creates a Tabbed Pane with
//               two tabs, one for Account creation and one for
//               Account transfer. and adds it as its Applet content
//               and also sets its size.

import javax.swing.*;
import java.util.*;

public class DisplayInterface extends JApplet
{

	private int APPLET_WIDTH = 975, APPLET_HEIGHT = 525;

	private JTabbedPane tPane;
	private SessionTab myTreadmill;
	//private TransferPanel transferPanel;
	// private Vector accountList;

	//The method init initializes the Applet with a Pane with two tabs
	public void init()
	{
		//list of accounts to be used in every panel
		//accountList = new Vector();

		//register panel uses the list of accounts
		//transferPanel = new TransferPanel(accountList);

		//    createPanel = new CreatePanel(accountList, transferPanel);

		//create a tabbed pane with two tabs
		myTreadmill = new SessionTab();
		tPane = new JTabbedPane();
		tPane.addTab("Treadmill", myTreadmill);
		// tPane.addTab("Account transfer", transferPanel);

		getContentPane().add(tPane);
		setSize (APPLET_WIDTH, APPLET_HEIGHT); //set Applet size
	}
}


