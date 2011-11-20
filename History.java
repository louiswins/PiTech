import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class History extends JPanel{

	JTextArea area;
	JScrollPane scroller;
	Dimension size;
	History(){
		//size = new Dimension(600,270);
		area = new JTextArea("User History for Current Session\n\n");
		area.setEditable(false);
		//area.setPreferredSize(size);
		add(area);

    	scroller = new JScrollPane(area);
    	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	scroller.setPreferredSize(new Dimension(610,270));
    	add(scroller);
	}
	
	public String updateHistory(String line){	//IDEA: input might be of type other than Object
		String history = "test"; 
//		history = obj.toString();				//IDEA: you can write a toString in another class for this object
		area.append(line + "\n");					//you can use area.setText("text") if you prefer
		return history;
	}
}
