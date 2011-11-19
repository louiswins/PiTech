// Cat 11/18: found bug: after you stop, and hit quickstart again, labels don't update until you change the speed

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
 * <p>Note that in this version, the QuickStart button just adds 10s to the time
 * run, it doesn't actually do anything with a timer or anything.
 *
 * @version 1.1
 */
public class ControlTab extends JPanel {
	/* Buttons */
	private JButton quickStart_Resume, pause_Stop, goal_Run_Start, weightLoss, cardio, hill, random, radioOn, radioOff;
	private JSpinner sSpeed, sIncline, sUserSpeed, sAge, sWeight;
	private JRadioButton[] radioButtonsGoalRun;
	private ButtonGroup myButtonGroup;

	/* Panels */
	private JPanel panelMessage, panelInputOutput, panelOutputs, panelInputs;
	private JPanel panelBasicFunc, panelGoalStart;
	private JPanel panelSpeedIncline;
	private JPanel panelTime, panelSpeed, panelIncline, panelDistance, panelCalories;
	private JPanel panelDistanceRadio, panelDurationRadio, panelCaloriesRadio;
	private JPanel panelGoals, panelUserInfo, panelPrograms, panelRadio;
	private JPanel[] panelSpeedArray, panelTimeArray, panelInclinationArray, panelDistanceArray, panelCalorieArray;

	/* Labels */
	private JLabel labelTimeCurVal, labelTimeElapsedVal, labelSpeedCurVal, labelSpeedAvgVal;
	private JLabel labelInclineCurVal, labelDistanceCurVal, labelDistanceTargVal, labelCaloriesCurVal, labelCaloriesTargVal;
	private JLabel message, labelUserSpeed, labelAge, labelWeight, labelRealTime;
	private JLabel time, speed, inclination, distance, calories;

	/* Misc */
	private Border blackline;
	private Font currentFont;
	private JTextField textFieldGoalDistance, textFieldGoalDuration, textFieldGoalCalories;
	private JTextField goalTextField, timeMultiplierTextField, textFieldAge, textFieldWeight;
	private JSeparator mySeparator;

	private int distanceTarget, caloriesTarget;
	private Session myTreadmill;
	private int age, weight;
	private int timeMultiplier;
	private Timer timer;
	private Goal goalDist, goalDur, goalCal;
	//private Goal[] goal; //maybe later, if there's time


	/** Maximum speed in tenths of a mile per hour */
	private static final int MAX_SPEED = 150;
	/** Maximum incline in percent */
	private static final int MAX_INCLINE = 15;
	private static final int MAX_USERSPEED = 150;
	private static final int FPS = 100;
	private static final String DEFAULT_MESSAGE = "Welcome to the PiTech Treadmill Simulator!";

	
	public ControlTab() {
		ButtonListener bl = new ButtonListener();

		/* Set up buttons */
		quickStart_Resume = new JButton("QuickStart");
		quickStart_Resume.addActionListener(bl);
		pause_Stop = new JButton("Stop");
		pause_Stop.addActionListener(bl);
		goal_Run_Start = new JButton("Goal Run Start");
		goal_Run_Start.addActionListener(bl);

		//spinners
		SpinnerListener sl = new SpinnerListener();
		sSpeed = new JSpinner(new SpinnerNumberModel(0.0, 0.0, (double)MAX_SPEED/10.0, 0.1));
		sSpeed.setEditor(new JSpinner.NumberEditor(sSpeed, "#0.0"));
		sSpeed.addChangeListener(sl);
		sIncline = new JSpinner(new SpinnerNumberModel(0, 0, MAX_INCLINE, 1));
		sIncline.setEditor(new JSpinner.NumberEditor(sIncline, "#0"));
		sIncline.addChangeListener(sl);
		sUserSpeed = new JSpinner(new SpinnerNumberModel(0.0, 0.0, (double)MAX_USERSPEED/10.0, 0.1));  
		sUserSpeed.setEditor(new JSpinner.NumberEditor(sUserSpeed, "#0.0"));
		sUserSpeed.addChangeListener(sl);
		sAge =  new JSpinner(new SpinnerNumberModel(30, 12, 100, 1));  
		sAge.addChangeListener(sl);
		sWeight =  new JSpinner(new SpinnerNumberModel(150, 75, 300, 1));
		sWeight.addChangeListener(sl);

		//for user info panel
		panelUserInfo = new JPanel();
		panelUserInfo.setLayout(new GridLayout(4, 4));
		timeMultiplierTextField = new JTextField(20);
		labelUserSpeed = new JLabel("User Speed:");
		labelAge = new JLabel("Age:");
		labelWeight = new JLabel("Weight:");
		labelRealTime = new JLabel("Real Time:");
		panelUserInfo.add(labelUserSpeed);
		panelUserInfo.add(sUserSpeed);
		panelUserInfo.add(labelAge);
		panelUserInfo.add(sAge);
		panelUserInfo.add(labelWeight);
		panelUserInfo.add(sWeight);
		panelUserInfo.add(labelRealTime);
		panelUserInfo.add(timeMultiplierTextField);
		
		//for programs
		panelPrograms = new JPanel(new GridLayout(4,1));
		weightLoss = new JButton("Weight Loss");
		cardio = new JButton("Cardio");
		hill = new JButton("Hill");
		random = new JButton("Random");
		panelPrograms.add(weightLoss);
		panelPrograms.add(cardio);
		panelPrograms.add(hill);
		panelPrograms.add(random);
		
		//for radio
		panelRadio = new JPanel();
		panelRadio.setLayout(new GridLayout(5,1));
		radioOn = new JButton("On");
		radioOff = new JButton("Off");
		panelRadio.add(new JLabel("RADIO", JLabel.CENTER));
		panelRadio.add(new JLabel(""));
		panelRadio.add(radioOn);
		panelRadio.add(radioOff);
		panelRadio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));


		radioButtonsGoalRun = new JRadioButton[3];
		radioButtonsGoalRun[0] = new JRadioButton("distance", true);
		radioButtonsGoalRun[1] = new JRadioButton("duration");
		radioButtonsGoalRun[2] = new JRadioButton("calories");
		myButtonGroup = new ButtonGroup();
		myButtonGroup.add(radioButtonsGoalRun[0]);
		myButtonGroup.add(radioButtonsGoalRun[1]);
		myButtonGroup.add(radioButtonsGoalRun[2]);
		
		
		/* Set up labels */
		labelTimeCurVal  = new JLabel("00:00:00", JLabel.CENTER);
		labelTimeElapsedVal = new JLabel("00:00:00", JLabel.CENTER);
		labelSpeedCurVal = new JLabel("0.000 mph", JLabel.CENTER);
		labelSpeedAvgVal = new JLabel("0.000 mph", JLabel.CENTER);
		labelInclineCurVal = new JLabel("0 %", JLabel.CENTER); 
		labelDistanceCurVal = new JLabel("0.000 mi", JLabel.CENTER); 
		labelDistanceTargVal = new JLabel("0.000 mi", JLabel.CENTER); 
		labelCaloriesCurVal = new JLabel("0 cal", JLabel.CENTER); 
		labelCaloriesTargVal = new JLabel("0 cal", JLabel.CENTER);

		message = new JLabel(DEFAULT_MESSAGE);
		message.setForeground(Color.blue);
		currentFont = message.getFont();
		message.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), 15));
		
		textFieldGoalDistance = new JTextField(30);
		textFieldGoalDuration = new JTextField(30);
		textFieldGoalCalories = new JTextField(30);


		
		/* Set up all the panels, top to bottom, left to right: */

		/* Message */
		panelMessage = new JPanel(); 
		panelMessage.setBorder(BorderFactory.createLineBorder(Color.black));
		panelMessage.add(message);


		/* Outputs */		
		panelTime = new JPanel();
		panelTime.setLayout(new GridLayout(5,1));
		panelTimeArray = new JPanel[5];
		for(int i =0; i< 5; i++){
			panelTimeArray[i] = new JPanel();
			panelTimeArray[i].setLayout(new BorderLayout());
			panelTime.add(panelTimeArray[i]);
		}
		panelTimeArray[0].add(new JLabel("TIME", JLabel.CENTER), BorderLayout.CENTER);
		panelTimeArray[1].add(new JLabel("Elapsed", JLabel.CENTER), BorderLayout.SOUTH);
		panelTimeArray[2].add(labelTimeElapsedVal, BorderLayout.NORTH);
		panelTimeArray[3].add(new JLabel("Target", JLabel.CENTER), BorderLayout.SOUTH);
		panelTimeArray[4].add(labelTimeCurVal, BorderLayout.NORTH);
		
		panelSpeed = new JPanel();
		panelSpeed.setLayout(new GridLayout(5,1));
		panelSpeed.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		panelSpeedArray = new JPanel[5];
		for(int i =0; i< 5; i++){
			panelSpeedArray[i] = new JPanel();
			panelSpeedArray[i].setLayout(new BorderLayout());
			panelSpeed.add(panelSpeedArray[i]);
		}
		panelSpeedArray[0].add(new JLabel("SPEED", JLabel.CENTER), BorderLayout.CENTER);
		panelSpeedArray[1].add(new JLabel("Current", JLabel.CENTER), BorderLayout.SOUTH);
		panelSpeedArray[2].add(labelSpeedCurVal, BorderLayout.NORTH);
		panelSpeedArray[3].add(new JLabel("Average", JLabel.CENTER), BorderLayout.SOUTH);
		panelSpeedArray[4].add(labelSpeedAvgVal, BorderLayout.NORTH);
		
		panelIncline = new JPanel();
		panelIncline.setLayout(new GridLayout(5,1));
		panelInclinationArray = new JPanel[5];
		for(int i =0; i< 5; i++){
			panelInclinationArray[i] = new JPanel();
			panelInclinationArray[i].setLayout(new BorderLayout());
			panelIncline.add(panelInclinationArray[i]);
		}
		panelInclinationArray[0].add(new JLabel("INCLINATION", JLabel.CENTER), BorderLayout.CENTER);
		panelInclinationArray[1].add(new JLabel("Current", JLabel.CENTER), BorderLayout.SOUTH);
		panelInclinationArray[2].add(labelInclineCurVal, BorderLayout.NORTH);

		panelDistance = new JPanel();
		panelDistance.setLayout(new GridLayout(5,1));
		panelDistance.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		panelDistanceArray = new JPanel[5];
		for(int i =0; i< 5; i++){
			panelDistanceArray[i] = new JPanel();
			panelDistanceArray[i].setLayout(new BorderLayout());
			panelDistance.add(panelDistanceArray[i]);
		}
		panelDistanceArray[0].add(new JLabel("DISTANCE", JLabel.CENTER), BorderLayout.CENTER);
		panelDistanceArray[1].add(new JLabel("Current", JLabel.CENTER), BorderLayout.SOUTH);
		panelDistanceArray[2].add(labelDistanceCurVal, BorderLayout.NORTH);
		panelDistanceArray[3].add(new JLabel("Target", JLabel.CENTER), BorderLayout.SOUTH);
		panelDistanceArray[4].add(labelDistanceTargVal, BorderLayout.NORTH);
		
		panelCalories = new JPanel();
		panelCalories.setLayout(new GridLayout(5,1));
		panelCalorieArray = new JPanel[5];
		for(int i =0; i< 5; i++){
			panelCalorieArray[i] = new JPanel();
			panelCalorieArray[i].setLayout(new BorderLayout());
			panelCalories.add(panelCalorieArray[i]);
		}
		panelCalorieArray[0].add(new JLabel("CALORIES BURNED", JLabel.CENTER), BorderLayout.CENTER);
		panelCalorieArray[1].add(new JLabel("Current", JLabel.CENTER), BorderLayout.SOUTH);
		panelCalorieArray[2].add(labelCaloriesCurVal, BorderLayout.NORTH);
		panelCalorieArray[3].add(new JLabel("Target", JLabel.CENTER), BorderLayout.SOUTH);
		panelCalorieArray[4].add(labelCaloriesTargVal, BorderLayout.NORTH);
		
		
//		mySeparator = new JSeparator(SwingConstants.VERTICAL);
//		mySeparator.setSize(30, 40);
		panelOutputs = new JPanel(); 
		panelOutputs.setLayout(new GridLayout(1,5));
		panelOutputs.add(panelTime);
//		panelOutputs.add(new JSeparator(SwingConstants.VERTICAL));
		panelOutputs.add(panelSpeed);
//		panelOutputs.add(new JSeparator(SwingConstants.VERTICAL));
		panelOutputs.add(panelIncline);
//		panelOutputs.add(new JSeparator(SwingConstants.VERTICAL));
		panelOutputs.add(panelDistance);
//		panelOutputs.add(new JSeparator(SwingConstants.VERTICAL));
		panelOutputs.add(panelCalories);
		panelOutputs.add(panelRadio);
		

		/* Inputs */
		/* Basic functions: left */
		panelBasicFunc = new JPanel();
		panelBasicFunc.setLayout(new GridLayout(2,1));
		panelBasicFunc.add(quickStart_Resume);
		panelBasicFunc.add(pause_Stop);
		
		/* Goals & speed/incline controls: right */
		goalTextField = new JTextField(20);

		/** 
		 * Only the numbers 1 to 0 on the keyboard change the text field.
		 *
		 * 		http://www.codeguru.com/forum/showthread.php?t=221440
		 */
		goalTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
			  char c = e.getKeyChar();
			  if (!((c >= '0') && (c <= '9') ||
				 (c == KeyEvent.VK_BACK_SPACE) ||
				 (c == KeyEvent.VK_DELETE))) {
				getToolkit().beep();
				e.consume();
			  }
			}
		  });

		panelGoals = new JPanel();
		panelGoals.setLayout(new GridLayout(5,1));
		panelGoals.add(goal_Run_Start);
		panelGoals.add(radioButtonsGoalRun[0]);
		panelGoals.add(radioButtonsGoalRun[1]);
		panelGoals.add(radioButtonsGoalRun[2]);
		panelGoals.add(goalTextField);

		panelSpeedIncline = new JPanel();
		panelSpeedIncline.setLayout(new GridLayout(2,2));
		panelSpeedIncline.add(new JLabel("Speed: "));
		panelSpeedIncline.add(sSpeed);
		panelSpeedIncline.add(new JLabel("Incline: "));
		panelSpeedIncline.add(sIncline);

		/* All inputs */
		panelInputs = new JPanel(); 
		panelInputs.setLayout(new GridLayout(1,2));
		panelInputs.add(panelBasicFunc);
		panelInputs.add(panelGoals);
		panelInputs.add(panelSpeedIncline);
		panelInputs.add(panelPrograms);
		panelInputs.add(panelGoals);
		panelInputs.add(panelUserInfo);
		
		
		/* Bring it all together */
		panelInputOutput = new JPanel();
		panelInputOutput.setLayout(new GridLayout(2,1));
		panelInputOutput.add(panelOutputs);
		panelInputOutput.add(panelInputs);
		
		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, panelMessage);
		add(BorderLayout.CENTER, panelInputOutput);


		/* Set up the current variables. */
		distanceTarget = 0;
		caloriesTarget = 0;
		myTreadmill = new Session();
		age = 21;
		weight = 180;
		timeMultiplier = 4;

		//goal = new DistanceGoal(0.5);

		timer = new Timer(1000 / FPS, new TimerListener());
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
		labelCaloriesCurVal.setText(Integer.toString(myTreadmill.getCalories(age, weight)) + " cal");

		/* Updates goal labels. */
		if (goalDist != null)
			labelDistanceTargVal.setText(goalDist.getProgress(myTreadmill));
		else
			labelDistanceTargVal.setText("0.000 mi");
		if (goalDur != null)
			labelTimeCurVal.setText(goalDur.getProgress(myTreadmill));
		else
			labelTimeCurVal.setText("00:00:00");
		if (goalCal != null)
			labelCaloriesTargVal.setText(goalCal.getProgress(myTreadmill));
		else
			labelCaloriesTargVal.setText("0 cal");
	}
	
	/**
	 * Writes a message to the messagebox.
	 *
	 * @param msg the message to write
	 */
	private void writeMessage(String msg) {
		message.setText(msg);
	}

	/* Checks goal input string for validity. */
	private boolean inputIsValid(String input) {
		if (Integer.parseInt(input) <= 1000)
			return true;
		else
			return false;
	}
	
	/* Determines which goal to set, sets it, and starts/resumes the treadmill. */
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
