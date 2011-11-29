import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Controls the Session. Shows all the important information and data about the
 * current run session.
 *
 * @version 2.2
 */
public class ControlTab extends JPanel {
	/* Control Elements */
	private JButton quickStart_Resume, pause_Stop;
	private JButton goal_Run_Start;
	private JButton weightLoss, cardio, hill, random;
	/*private JButton radioOn, radioOff;*/
	private JSpinner sSpeed, sIncline;
	private JSpinner sUserSpeed, sAge, sWeight;
	private JCheckBox lockUserSpeed;
	private JRadioButton[] radioButtonsGoalRun;

	/* Display Elements */
	private JLabel message;
	private JLabel labelTimeElapsedVal, labelTimeTargVal;
	private JLabel labelSpeedCurVal, labelSpeedAvgVal;
	private JLabel labelInclineCurVal;
	private JLabel labelDistanceCurVal, labelDistanceTargVal;
	private JLabel labelCaloriesCurVal, labelCaloriesTargVal;
	private JTextField goalTextField;
	private JTextField timeMultiplierTextField;

	/* Other instance variables */
	private Session myTreadmill;
	private int age, weight;
	private int timeMultiplier;
	private int timeSinceLastChange;
	private Timer timer;
	private Goal goalDist, goalDur, goalCal;


	/** Maximum speed of the belt. Tenths of a mile per hour */
	private static final int MAX_SPEED = 150;
	/** Maximum speed of the user. Tenths of a mile per hour. */
	private static final int MAX_USERSPEED = 150;
	/** Maximum incline. Percent. */
	private static final int MAX_INCLINE = 15;
	/** Maximum run time. Seconds. */
	private static final int MAX_TIME = 3600;
	/** Frames per second. */
	private static final int FPS = 60;
	private static final String DEFAULT_MESSAGE = "Welcome to the PiTech Treadmill Simulator!";

	
	public ControlTab(History hist) {
		ButtonListener bl = new ButtonListener();
		SpinnerListener sl = new SpinnerListener();


		/* Set up all the panels, top to bottom, left to right: */

		/* Message Panel */
		message = new JLabel(DEFAULT_MESSAGE);
		message.setForeground(Color.blue);
		Font curFont = message.getFont();
		message.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 15));

		JPanel panelMessage = new JPanel(); 
		panelMessage.setBorder(BorderFactory.createLineBorder(Color.black));
		panelMessage.add(message);



		/* Outputs */
		// JPanel panelOutputs = new JPanel(new GridLayout(1, 6)); 
		JPanel panelOutputs = new JPanel(new GridLayout(1, 5));
		panelOutputs.setBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.BLACK));

		/* Time */
		labelTimeElapsedVal  = new JLabel("00:00:00", JLabel.CENTER);
		labelTimeTargVal = new JLabel("00:00:00", JLabel.CENTER);
		JPanel timePanel = new JPanel(new GridBagLayout());
		timePanel.add(new JLabel("TIME"), new GBC(0, 0).inset(6,0,6).weight(1, 1).anchor(GBC.SOUTH));
		timePanel.add(new JLabel("Elapsed"), new GBC(0, 1));
		timePanel.add(labelTimeElapsedVal, new GBC(0, 2).inset(0,0,10));
		timePanel.add(new JLabel("Target"), new GBC(00, 3));
		timePanel.add(labelTimeTargVal, new GBC(0, 4).inset(0,0,10).weight(1, 1).anchor(GBC.NORTH));
		timePanel.setBorder(BorderFactory.createMatteBorder(0,1,0,1,Color.BLACK));
		panelOutputs.add(timePanel);
		
		/* Speed */
		labelSpeedCurVal = new JLabel("0.000 mph", JLabel.CENTER);
		labelSpeedAvgVal = new JLabel("0.000 mph", JLabel.CENTER);
		JPanel speedPanel = new JPanel(new GridBagLayout());
		speedPanel.add(new JLabel("SPEED"), new GBC(0, 0).inset(6,0,6).weight(1, 0).anchor(GBC.SOUTH));
		speedPanel.add(new JLabel("Current"), new GBC(0, 1));
		speedPanel.add(labelSpeedCurVal, new GBC(0, 2).inset(0,0,10));
		speedPanel.add(new JLabel("Average"), new GBC(0, 3));
		speedPanel.add(labelSpeedAvgVal, new GBC(0, 4).inset(0,0,10).anchor(GBC.NORTH));
		speedPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK));
		panelOutputs.add(speedPanel);
		
		/* Incline */
		labelInclineCurVal = new JLabel("0 %", JLabel.CENTER); 
		JPanel incPanel = new JPanel(new GridBagLayout());
		incPanel.add(new JLabel("INCLINE"), new GBC(0, 0).inset(6,0,6).weight(1, 0).anchor(GBC.SOUTH));
		incPanel.add(new JLabel("Current"), new GBC(0, 1));
		incPanel.add(labelInclineCurVal, new GBC(0, 2).inset(0,0,10));
		/* Dirty hack to align labels */
		incPanel.add(new JLabel(" "), new GBC(0, 3));
		incPanel.add(new JLabel(" "), new GBC(0, 4).inset(0,0,10).anchor(GBC.NORTH));
		incPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK));
		panelOutputs.add(incPanel);

		/* Distance */
		labelDistanceCurVal = new JLabel("0.000 mi", JLabel.CENTER); 
		labelDistanceTargVal = new JLabel("0.000 mi", JLabel.CENTER); 
		JPanel distPanel = new JPanel(new GridBagLayout());
		distPanel.add(new JLabel("DISTANCE"), new GBC(0, 0).inset(6,0,6).weight(1, 0).anchor(GBC.SOUTH));
		distPanel.add(new JLabel("Current"), new GBC(0, 1));
		distPanel.add(labelDistanceCurVal, new GBC(0, 2).inset(0,0,10));
		distPanel.add(new JLabel("Target"), new GBC(0, 3));
		distPanel.add(labelDistanceTargVal, new GBC(0, 4).inset(0,0,10).anchor(GBC.NORTH));
		distPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK));
		panelOutputs.add(distPanel);
		
		/* Calories */
		labelCaloriesCurVal = new JLabel("0 Cal", JLabel.CENTER); 
		labelCaloriesTargVal = new JLabel("0 Cal", JLabel.CENTER);
		JPanel calPanel = new JPanel(new GridBagLayout());
		calPanel.add(new JLabel("CALORIES"), new GBC(0, 0).inset(6,0,6).weight(1, 0).anchor(GBC.SOUTH));
		calPanel.add(new JLabel("Current"), new GBC(0, 1));
		calPanel.add(labelCaloriesCurVal, new GBC(0, 2).inset(0,0,10));
		calPanel.add(new JLabel("Target"), new GBC(0, 3));
		calPanel.add(labelCaloriesTargVal, new GBC(0, 4).inset(0,0,10).anchor(GBC.NORTH));
		calPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK));
		panelOutputs.add(calPanel);

		/* Radio Controls */
		/*
		radioOn = new JButton("On");
		radioOff = new JButton("Off");
		JPanel radioPanel = new JPanel(new GridBagLayout());
		radioPanel.add(new JLabel("RADIO"), new GBC(0, 0).inset(6,0,15).weight(1, 0).anchor(GBC.SOUTH));
		radioPanel.add(radioOn, new GBC(0, 1).height(2).anchor(GBC.SOUTH).fill(GBC.HORIZONTAL));
		radioPanel.add(radioOff, new GBC(0, 3).height(2).anchor(GBC.NORTH).fill(GBC.HORIZONTAL));
		panelOutputs.add(radioPanel);
		*/



		/* Inputs */
		JPanel panelInputs = new JPanel(new GridBagLayout());

		/* Basic functions */
		quickStart_Resume = new JButton("QuickStart");
		// The following useless-looking lines keep the buttons from changing sizes when the labels change; see
		// http://docs.oracle.com/javase/1.4.2/docs/api/javax/swing/JComponent.html#setPreferredSize%28java.awt.Dimension%29
		quickStart_Resume.setPreferredSize(quickStart_Resume.getPreferredSize());
		quickStart_Resume.setMinimumSize(quickStart_Resume.getMinimumSize());
		quickStart_Resume.addActionListener(bl);
		pause_Stop = new JButton("Stop");
		pause_Stop.addActionListener(bl);

		JPanel panelBasicFunc = new JPanel(new GridBagLayout());
		panelBasicFunc.add(quickStart_Resume, new GBC(0, 0).weight(1,1).fill(GBC.BOTH).inset(0, 0, 5));
		panelBasicFunc.add(pause_Stop, new GBC(0, 1).weight(1,1).fill(GBC.BOTH));
		
		/* Speed/Incline controls */
		sSpeed = new JSpinner(new SpinnerNumberModel(0.0, 0.0, (double)MAX_SPEED/10.0, 0.1));
		sSpeed.setEditor(new JSpinner.NumberEditor(sSpeed, "#0.0"));
		sSpeed.addChangeListener(sl);
		sIncline = new JSpinner(new SpinnerNumberModel(0, 0, MAX_INCLINE, 1));
		sIncline.setEditor(new JSpinner.NumberEditor(sIncline, "#0"));
		sIncline.addChangeListener(sl);

		JPanel panelSpeedIncline = new JPanel(new GridBagLayout());
		panelSpeedIncline.add(new JLabel("Speed: "), new GBC(10, 0).anchor(GBC.EAST));
		panelSpeedIncline.add(sSpeed, new GBC(12, 0).fill(GBC.HORIZONTAL).weight(1,0));
		panelSpeedIncline.add(new JLabel("Incline: "), new GBC(10, 1).anchor(GBC.EAST));
		panelSpeedIncline.add(sIncline, new GBC(12, 1).fill(GBC.HORIZONTAL).inset(5,0,0));

		/* Programs */
		weightLoss = new JButton("Weight Loss");
		cardio = new JButton("Cardio");
		hill = new JButton("Hill");
		random = new JButton("Random");

		JPanel panelPrograms = new JPanel(new GridBagLayout());
		panelPrograms.add(weightLoss, new GBC(20, 0).fill(GBC.BOTH).weight(1,1).inset(0, 0, 3));
		panelPrograms.add(cardio, new GBC(20, 1).fill(GBC.BOTH).weight(1,1).inset(0, 0, 3));
		panelPrograms.add(hill, new GBC(20, 2).fill(GBC.BOTH).weight(1,1).inset(0, 0, 3));
		panelPrograms.add(random, new GBC(20, 3).fill(GBC.BOTH).weight(1,1));

		/* Goals */
		goal_Run_Start = new JButton("Goal Run Start");
		goal_Run_Start.addActionListener(bl);
		radioButtonsGoalRun = new JRadioButton[3];
		radioButtonsGoalRun[0] = new JRadioButton("distance", true);
		radioButtonsGoalRun[1] = new JRadioButton("duration");
		radioButtonsGoalRun[2] = new JRadioButton("calories");
		ButtonGroup myButtonGroup = new ButtonGroup();
		myButtonGroup.add(radioButtonsGoalRun[0]);
		myButtonGroup.add(radioButtonsGoalRun[1]);
		myButtonGroup.add(radioButtonsGoalRun[2]);
		goalTextField = new JTextField();

		JPanel panelGoals = new JPanel(new GridBagLayout());
		panelGoals.add(goal_Run_Start, new GBC(30, 0).weight(1, 1).fill(GBC.HORIZONTAL).anchor(GBC.SOUTH));
		panelGoals.add(radioButtonsGoalRun[0], new GBC(30, 1).anchor(GBC.WEST));
		panelGoals.add(radioButtonsGoalRun[1], new GBC(30, 2).anchor(GBC.WEST));
		panelGoals.add(radioButtonsGoalRun[2], new GBC(30, 3).anchor(GBC.WEST));
		panelGoals.add(goalTextField, new GBC(30, 4).weight(1, 1).fill(GBC.HORIZONTAL).anchor(GBC.NORTH));
		
		/* Only the numbers 1 to 0 on the keyboard change the text field.
		 *
		 * 		http://www.codeguru.com/forum/showthread.php?t=221440
		 */
		/* XXX: what about decimals? or hh:mm:ss? this is harder than it
		 * seems. */
		goalTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
			  char c = e.getKeyChar();
			  if (!((c >= '0') && (c <= '9') ||
				 (c == KeyEvent.VK_BACK_SPACE) ||
				 (c == KeyEvent.VK_DELETE))) {
				//getToolkit().beep();
				e.consume();
			  }
			}
		  });

		/* User Info */
		sUserSpeed = new JSpinner(new SpinnerNumberModel(0.0, 0.0, (double)MAX_USERSPEED/10.0, 0.1));  
		sUserSpeed.setEditor(new JSpinner.NumberEditor(sUserSpeed, "#0.0"));
		sUserSpeed.addChangeListener(sl);
		sUserSpeed.setEnabled(false);
		sAge =  new JSpinner(new SpinnerNumberModel(30, 12, 100, 1));  
		sAge.addChangeListener(sl);
		sWeight =  new JSpinner(new SpinnerNumberModel(150, 75, 300, 1));
		sWeight.addChangeListener(sl);
		timeMultiplierTextField = new JTextField("4");
		timeMultiplierTextField.addActionListener(bl);
		lockUserSpeed = new JCheckBox("Lock", true);
		lockUserSpeed.addActionListener(bl);

		JPanel panelUserInfo = new JPanel(new GridBagLayout());
		panelUserInfo.add(new JLabel("USER CONTROLS"), new GBC(0, 0).inset(0,0,5).width(4));
		panelUserInfo.add(new JLabel("Speed: "), new GBC(0, 1).inset(0, 0, 5).anchor(GBC.EAST));
		panelUserInfo.add(sUserSpeed, new GBC(1, 1).weight(1, 0).inset(0, 0, 5).fill(GBC.HORIZONTAL).width(2));
		panelUserInfo.add(lockUserSpeed, new GBC(3, 1).inset(0,0,5).anchor(GBC.EAST));
		panelUserInfo.add(new JLabel("Age: "), new GBC(0, 2).inset(0, 0, 5).anchor(GBC.EAST));
		panelUserInfo.add(sAge, new GBC(1, 2).inset(0, 0, 5).fill(GBC.HORIZONTAL).weight(1,0));
		panelUserInfo.add(new JLabel("Weight: "), new GBC(2, 2).inset(0, 0, 5).anchor(GBC.EAST));
		panelUserInfo.add(sWeight, new GBC(3, 2).inset(0, 0, 5).fill(GBC.HORIZONTAL).weight(1,0));
		panelUserInfo.add(new JLabel("Time Mult: "), new GBC(2, 3).anchor(GBC.EAST));
		panelUserInfo.add(timeMultiplierTextField, new GBC(3, 3).fill(GBC.HORIZONTAL));
		

		/* All inputs */
		panelInputs.add(panelBasicFunc, new GBC(0, 0).weight(2, 1).fill(GBC.BOTH).inset(5));
		panelInputs.add(panelSpeedIncline, new GBC(1, 0).weight(1, 1).fill(GBC.BOTH).inset(5, 0, 5, 5));
		panelInputs.add(panelPrograms, new GBC(2, 0).weight(1, 1).fill(GBC.BOTH).inset(5, 0, 5, 5));
		panelInputs.add(panelGoals, new GBC(3, 0).weight(1, 1).fill(GBC.BOTH).inset(5, 0, 5, 5));
		panelInputs.add(panelUserInfo, new GBC(4, 0).weight(1, 1).fill(GBC.BOTH).inset(5, 0, 5, 5));
		
		
		/* Bring it all together */
		setLayout(new GridBagLayout());
		add(panelMessage, new GBC(0, 0).fill(GBC.HORIZONTAL));
		add(panelOutputs, new GBC(0, 10).weight(1, 1).fill(GBC.BOTH));
		add(panelInputs, new GBC(0, 20).weight(1, 1).fill(GBC.BOTH));
		

		/* Set up the instance variables. */
		age = 30;
		weight = 150;
		myTreadmill = new Session(hist, age, weight);
		timeMultiplier = 4;

		Timer timer = new Timer(1000 / FPS, new TimerListener());
		timer.setInitialDelay(0);
		timer.start();

		/* And update the initial labels. */
		updateLabels();
	}


	/**
	 * Updates all the labels based on session.
	 */
	private void updateLabels() {
		labelTimeElapsedVal.setText(String.format("%02d:%02d:%02d", (int)(myTreadmill.getTimeElapsed() / 3600),
				(int)(myTreadmill.getTimeElapsed() / 60) % 60, (int)(myTreadmill.getTimeElapsed()) % 60));
		labelSpeedCurVal.setText(String.format("%5.3f mph", (double)myTreadmill.getSpeed() / 10.0));
		labelSpeedAvgVal.setText(String.format("%5.3f mph", myTreadmill.getAverageSpeed()));
		labelInclineCurVal.setText(Integer.toString(myTreadmill.getIncline()) + " %"); 
		labelDistanceCurVal.setText(String.format("%5.3f mi", myTreadmill.getDistance()));
		labelCaloriesCurVal.setText(Integer.toString(myTreadmill.getCalories(age, weight)) + " Cal");

		/* Updates goal labels. */
		if (goalDist != null)
			labelDistanceTargVal.setText(goalDist.getProgress(myTreadmill));
		else
			labelDistanceTargVal.setText("0.000 mi");
		if (goalDur != null)
			labelTimeTargVal.setText(goalDur.getProgress(myTreadmill));
		else
			labelTimeTargVal.setText("00:00:00");
		if (goalCal != null)
			labelCaloriesTargVal.setText(goalCal.getProgress(myTreadmill));
		else
			labelCaloriesTargVal.setText("0 Cal");
	}

	/**
	 * Writes a message to the messagebox.
	 *
	 * @param msg the message to write
	 */
	private void writeMessage(String msg) {
		message.setText(msg);
	}

	/**
	 * Checks goal input string for validity.
	 *
	 * @param input the input from the textbox
	 */
	private boolean inputIsValid(String input) {
		if (Integer.parseInt(input) <= 1000)
			return true;
		else
			return false;
	}

	
	/** Determines which goal to set, sets it, and starts/resumes the treadmill. */
	private void setGoal() {
		if (radioButtonsGoalRun[0].isSelected()) {
			writeMessage("Distance goal set.");
			goalDist = new DistanceGoal(Double.parseDouble(goalTextField.getText()), myTreadmill);
		} else if (radioButtonsGoalRun[1].isSelected()) {
			writeMessage("Duration goal set.");
			goalDur = new DurationGoal(Double.parseDouble(goalTextField.getText()), myTreadmill);
		} else {
			writeMessage("Calories goal set.");
			goalCal = new CalorieGoal(Integer.parseInt(goalTextField.getText()), age, weight, myTreadmill);
		}
		/* if the session is paused or stopped,
		   and we hit the goalStartButton, it starts it back up */
		if (myTreadmill.getState() == Session.State.STOPPED) {
			quickStart_Resume.setText("QuickStart");
			pause_Stop.setText("Pause");
			myTreadmill.start();
		} else if (myTreadmill.getState() == Session.State.PAUSED) {
			quickStart_Resume.setText("QuickStart");
			pause_Stop.setText("Pause");
			myTreadmill.resume();
		}
	}

	/** Listener for button events. */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object src = event.getSource();
			if (src == quickStart_Resume) {
				quickStart_Resume.setText("QuickStart");
				pause_Stop.setText("Pause");
				if (myTreadmill.getState() == Session.State.STOPPED) {
					Double val = (Double)(sSpeed.getValue());
					// always best not to compare floats directly
					if (val.doubleValue() < 0.05) {
						sSpeed.setValue(new Double(1.0));
					}
					myTreadmill.start();
				} else {
					myTreadmill.resume();
				}
			} else if (src == pause_Stop) {
				pause_Stop.setText("Stop");
				if (myTreadmill.getState() == Session.State.RUNNING) {
					quickStart_Resume.setText("Resume");
					myTreadmill.pause();
				} else {
					quickStart_Resume.setText("QuickStart");
					myTreadmill.stop();
					/* Reset speed and incline */
					sSpeed.setValue(new Double(0.0));
					sIncline.setValue(new Integer(0));
					goalDist = null;
					goalDur = null;
					goalCal = null;
				}
			} else if (src == goal_Run_Start) {
				if (goalTextField.getText().equals("")) {
					writeMessage("Goal input field is empty.  Please enter a value.");
				} else {
					if (inputIsValid(goalTextField.getText())) {
						setGoal();
					} else {
						writeMessage("That is not a valid goal input.");
					}
				}
			} else if (src == timeMultiplierTextField) {
				int newmult = -1;
				try {
					newmult = Integer.parseInt(timeMultiplierTextField.getText());
				} catch (NumberFormatException e) {}
				if (newmult > 0) {
					timeMultiplier = newmult;
				} else {
					writeMessage("Time multiplier must be a positive integer.");
					timeMultiplierTextField.setText(Integer.toString(timeMultiplier));
				}
			} else if (src == lockUserSpeed) {
				if (lockUserSpeed.isSelected()) {
					sUserSpeed.setEnabled(false);
					sUserSpeed.setValue(sSpeed.getValue());
				} else {
					sUserSpeed.setEnabled(true);
				}
			}
			updateLabels();
		}
	}

	/** Listener for spinner events. */
	private class SpinnerListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSpinner sp = (JSpinner)e.getSource();
			Number val = (Number)sp.getValue();
			if (sp == sSpeed) {
				myTreadmill.setSpeed(Math.round(10*val.floatValue()));
				if (lockUserSpeed.isSelected()) {
					sUserSpeed.setValue(val);
				}
			} else if (sp == sIncline) {
				myTreadmill.setIncline(val.intValue());
			}
			updateLabels();
		}
	}

	/** Listener for timer events. */
	private class TimerListener implements ActionListener {
		private long lastCall;
		
		public TimerListener() {
			super();
			lastCall = System.currentTimeMillis();
		}

		public void actionPerformed(ActionEvent e) {
			long curTime = System.currentTimeMillis();
			myTreadmill.update((double)(curTime - lastCall) / 1000.0 * timeMultiplier);

			if (goalDist != null) {
				if (goalDist.checkIfDone(myTreadmill)) {
					writeMessage("Distance goal reached!");
					goalDist = null;
				} else {
					//writeMessage(DEFAULT_MESSAGE);
				}
			}

			if (goalDur != null) {
				if (goalDur.checkIfDone(myTreadmill)) {
					writeMessage("Duration goal reached!");
					goalDur = null;
				} else {
					//writeMessage(DEFAULT_MESSAGE);
				}
			}

			if (goalCal != null) {
				if (goalCal.checkIfDone(myTreadmill)) {
					writeMessage("Calories goal reached!");
					goalCal = null;
				} else {
					//writeMessage(DEFAULT_MESSAGE);
				}
			}

			updateLabels();
			lastCall = curTime;
		}
	}
}
