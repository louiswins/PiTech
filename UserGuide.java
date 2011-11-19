import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UserGuide extends JPanel {
	JTextArea area;
	JScrollPane scroller;
   
    UserGuide(){
    	area = new JTextArea();
    	area.setEditable(false);
    	area.setText(addUserGuide());

    	scroller = new JScrollPane(area);
    	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
    	scroller.setPreferredSize(new Dimension(610,270));
    	add(scroller);
    }
    
    public String addUserGuide(){
    	String userGuide = 
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
    		"User Guide Goes Here\t\t\tUser Guide Goes Here\n";
    	return userGuide;
    	
    }
}
