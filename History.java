import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class History extends JPanel{

	JTextArea area;
	Dimension size;
	History(){
		size = new Dimension(600,270);
		area = new JTextArea("User History for Current Session\n\n");
		area.setEditable(false);
		area.setPreferredSize(size);
		add(area);
		
	}
	
	public String updateHistory(Object obj){	//IDEA: input might be of type other than Object
		String history = ""; 
//		history = obj.toString();				//IDEA: you can write a toString in another class for this object
		area.append(history);					//you can use area.setText("text") if you prefer
		return history;
	}
}
