import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
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
	private int distanceTarget, caloriesTarget;
	private Session session;
	private int age, weight;
	private int timeMultiplier;
	private Timer timer;

	/* Buttons */
	private JButton quickStart_Resume, pause_Stop, reset, goal_Run_Start, speedUp, speedDown, inclineUp, inclineDown;
	private JRadioButton[] radioButtonsGoalRun;
	private ButtonGroup myButtonGroup;

	/* Panels */
	private JPanel panelMessage, panelInputOutput, panelOutputs, panelInputs;
	private JPanel panelBasicFunc, panelGoalStart;
	private JPanel panelSpeedIncline;
	private JPanel panelTime, panelSpeed, panelIncline, panelDistance, panelCalories;
	private JPanel panelDistanceRadio, panelDurationRadio, panelCaloriesRadio;

	/* Labels */
	private JLabel labelTimeCurVal, labelTimeElapsedVal, labelSpeedCurVal, labelSpeedAvgVal;
	private JLabel labelInclineCurVal, labelDistanceCurVal, labelDistanceTargVal, labelCaloriesCurVal, labelCaloriesTargVal;
	private JLabel message;

	/* Misc */
	private Border blackline;
	private Font currentFont;
	private JTextField textFieldGoalDistance, textFieldGoalDuration, textFieldGoalCalories;

	/* XXX: Should these be here? */
	/** Maximum speed in tenths of a mile per hour */
	private static int MAX_SPEED = 150;
	/** Maximum incline in percent */
	private static int MAX_INCLINE = 15;
	private static int FPS = 100;

	
	public ControlTab() {
		ButtonListener bl = new ButtonListener();

		/* Set up buttons */
		quickStart_Resume = new JButton("QuickStart");
		quickStart_Resume.addActionListener(bl);
		pause_Stop = new JButton("Stop");
		pause_Stop.addActionListener(bl);
		reset = new JButton("Reset");
		goal_Run_Start = new JButton("Goal Run Start");
		speedUp = new JButton("Speed Up");
		speedUp.addActionListener(bl);
		speedDown = new JButton("Speed Down");
		speedDown.addActionListener(bl);
		inclineUp = new JButton("Incline Up");
		inclineUp.addActionListener(bl);
		inclineDown = new JButton("Incline Down");
		inclineDown.addActionListener(bl);

		radioButtonsGoalRun = new JRadioButton[3];
		radioButtonsGoalRun[0] = new JRadioButton("distance", true);
		radioButtonsGoalRun[1] = new JRadioButton("duration");
		radioButtonsGoalRun[2] = new JRadioButton("calories");
		myButtonGroup = new ButtonGroup();
		myButtonGroup.add(radioButtonsGoalRun[0]);
		myButtonGroup.add(radioButtonsGoalRun[1]);
		myButtonGroup.add(radioButtonsGoalRun[2]);
		
		
		/* Set up labels */
		labelTimeCurVal  = new JLabel("00:00:00");
		labelTimeElapsedVal = new JLabel("00:00:00");
		labelSpeedCurVal = new JLabel("0");
		labelSpeedAvgVal = new JLabel("0");
		labelInclineCurVal = new JLabel("0"); 
		labelDistanceCurVal = new JLabel("0 mi"); 
		labelDistanceTargVal = new JLabel("0 mi"); 
		labelCaloriesCurVal = new JLabel("0"); 
		labelCaloriesTargVal = new JLabel("0");

		message = new JLabel("Welcome to the treadmill machine");
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
		panelTime.add(new JLabel("Current Time"));
		panelTime.add(labelTimeCurVal);
		panelTime.add(new JLabel("Elapsed Time"));
		panelTime.add(labelTimeElapsedVal);

		panelSpeed = new JPanel(); 
		panelSpeed.setLayout(new GridLayout(5,1));
		panelSpeed.add(new JLabel("Current Speed"));
		panelSpeed.add(labelSpeedCurVal);
		panelSpeed.add(new JLabel("Average Speed"));
		panelSpeed.add(labelSpeedAvgVal);

		panelIncline = new JPanel(); 
		panelIncline.setLayout(new GridLayout(5,1));
		panelIncline.add(new JLabel("Current Incline"));
		panelIncline.add(labelInclineCurVal);

		panelDistance = new JPanel(); 
		panelDistance.setLayout(new GridLayout(5,1));
		panelDistance.add(new JLabel("Current Distance"));
		panelDistance.add(labelDistanceCurVal);
		panelDistance.add(new JLabel("Target Distance"));
		panelDistance.add(labelDistanceTargVal);
		
		panelCalories = new JPanel(); 
		panelCalories.setLayout(new GridLayout(5,1));
		panelCalories.add(new JLabel("Current Calories"));
		panelCalories.add(labelCaloriesCurVal);
		panelCalories.add(new JLabel("Target Calories"));
		panelCalories.add(labelCaloriesTargVal);

		panelOutputs = new JPanel(); 
		panelOutputs.setLayout(new GridLayout(1,5));
		panelOutputs.add(panelTime);
		panelOutputs.add(panelSpeed);
		panelOutputs.add(panelIncline);
		panelOutputs.add(panelDistance);
		panelOutputs.add(panelCalories);
		

		/* Inputs */
		/* Basic functions: left */
		panelBasicFunc = new JPanel();
		panelBasicFunc.setLayout(new GridLayout(3,1));
		panelBasicFunc.add(quickStart_Resume);
		panelBasicFunc.add(pause_Stop);
		panelBasicFunc.add(reset);
		
		/* Goals & speed/incline controls: right */
		panelDistanceRadio = new JPanel();
		panelDistanceRadio.setLayout(new GridLayout(1,2));		
		panelDistanceRadio.add(radioButtonsGoalRun[0]);
		panelDistanceRadio.add(textFieldGoalDistance);
		panelDurationRadio = new JPanel();
		panelDurationRadio.setLayout(new GridLayout(1,2));
		panelDurationRadio.add(radioButtonsGoalRun[1]);
		panelDurationRadio.add(textFieldGoalDuration);
		panelCaloriesRadio = new JPanel();
		panelCaloriesRadio.setLayout(new GridLayout(1,2));
		panelCaloriesRadio.add(radioButtonsGoalRun[2]);
		panelCaloriesRadio.add(textFieldGoalCalories);
		
		panelSpeedIncline = new JPanel();
		panelSpeedIncline.setLayout(new GridLayout(2,2));
		panelSpeedIncline.add(speedUp);
		panelSpeedIncline.add(inclineUp);
		panelSpeedIncline.add(speedDown);
		panelSpeedIncline.add(inclineDown);
//		panelSpeedIncline.add(labelSpeed);
//		panelSpeedIncline.add(labelIncline);

		panelGoalStart = new JPanel();
		panelGoalStart.setLayout(new GridLayout(5,1));
		panelGoalStart.add(goal_Run_Start);
		panelGoalStart.add(panelDistanceRadio);
		panelGoalStart.add(panelDurationRadio);
		panelGoalStart.add(panelCaloriesRadio);
		panelGoalStart.add(panelSpeedIncline);

		/* All inputs */
		panelInputs = new JPanel(); 
		panelInputs.setLayout(new GridLayout(1,2));
		panelInputs.add(panelBasicFunc);
		panelInputs.add(panelGoalStart);
		
		
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
		session = new Session();
		age = 21;
		weight = 180;
		timeMultiplier = 1;

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
		labelTimeElapsedVal.setText(String.format("%02d:%02d:%02d", (int)(session.getTimeElapsed() / 3600),
				(int)(session.getTimeElapsed() / 60) % 60, (int)(session.getTimeElapsed()) % 60));
		labelSpeedCurVal.setText(Double.toString((double)session.getSpeed() / 10.0) + " mph");
		labelSpeedAvgVal.setText(String.format("%5.4f mph", session.getAverageSpeed()));
		labelInclineCurVal.setText(Integer.toString(session.getIncline()) + " %"); 
		labelDistanceCurVal.setText(String.format("%5.4f miles", session.getDistance()));
		labelCaloriesCurVal.setText(Integer.toString(session.getCalories(age, weight)));
	}



	/** Listener for button events */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object src = event.getSource();
			if (src == quickStart_Resume) {
				quickStart_Resume.setText("QuickStart");
				pause_Stop.setText("Pause");
				if (session.getState() == Session.State.STOPPED) {
					session.start();
				} else {
					session.resume();
				}
			} else if (src == pause_Stop) {
				pause_Stop.setText("Stop");
				if (session.getState() == Session.State.RUNNING) {
					quickStart_Resume.setText("Resume");
					session.pause();
				} else {
					quickStart_Resume.setText("QuickStart");
					session.stop();
				}
			} else if (src == speedUp) {
				int cursp = session.getSpeed();
				cursp += 1;
				if (cursp >= MAX_SPEED) {
					cursp = MAX_SPEED;
				}
				session.setSpeed(cursp);
			} else if (src == speedDown) {
				int cursp = session.getSpeed();
				cursp -= 1;
				if (cursp <= 0) {
					cursp = 0;
				}
				session.setSpeed(cursp);
			} else if (src == inclineUp) {
				int curinc = session.getIncline();
				curinc += 1;
				if (curinc >= MAX_INCLINE) {
					curinc = MAX_INCLINE;
				}
				session.setIncline(curinc);
			} else if (src == inclineDown) {
				int curinc = session.getIncline();
				curinc -= 1;
				if (curinc <= 0) {
					curinc = 0;
				}
				session.setIncline(curinc);
			}
			updateLabels();
		}
	}

	/** Listener for timer events. */
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.update(1.0 / FPS);
			updateLabels();
		}
	}
}
