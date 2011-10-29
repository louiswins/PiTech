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
 * @version 0.2
 */
public class ControlTab extends JPanel{
	private int timeCurrent, timeElapsed, speedCurrent, speedAverage, inclineCurrent, distanceCurrent, distanceTarget, caloriesCurrent, caloriesTarget;
	/* Buttons */
	private JButton quickStart_Resume, pause_Stop, reset, goal_Run_Start, speedUp, speedDown, inclineUp, inclineDown;
	private JRadioButton[] radioButtonsGoalRun;
	private ButtonGroup myButtonGroup;

	/* Panels */
	private JPanel panelMessage, panelInputOutput, panelOutputs, panelInputs;
	private JPanel panelBasicFunc, panelGoalStart;
	private JPanel panelGoalStartLeft, panelGoalStartRight, panelSpeedIncline;
	private JPanel panelTime, panelSpeed, panelIncline, panelDistance, panelCalories;
	private JPanel panelDistanceRadio, panelDurationRadio, panelCaloriesRadio;

	/* Labels */
	private JLabel labelTimeCur, labelTimeElapsed, labelSpeedCur, labelSpeedAvg;
	private JLabel labelTimeCurVal, labelTimeElapsedVal, labelSpeedCurVal, labelSpeedAvgVal;
	private JLabel labelInclineCur, labelDistanceCur, labelDistanceTarg, labelCaloriesCur, labelCaloriesTarg;
	private JLabel labelInclineCurVal, labelDistanceCurVal, labelDistanceTargVal, labelCaloriesCurVal, labelCaloriesTargVal;
	private JLabel message, labelSpeed, labelIncline;

	/* Misc */
	private Border blackline;
	private Font currentFont;
	private JTextField textFieldGoal1, textFieldGoal2, textFieldGoal3;

	
	public ControlTab(){
		
		/* Set up the current variables. */
		timeCurrent = 0; 
		timeElapsed  = 0;
		speedCurrent = 0;
		speedAverage = 0;
		inclineCurrent = 0;
		distanceCurrent = 0;
		distanceTarget = 0;
		caloriesCurrent = 0;
		caloriesTarget= 0;

		/* Set up buttons */
		quickStart_Resume = new JButton("QuickStart / Resume");
		pause_Stop = new JButton("Pause / Stop");
		reset = new JButton("Reset");
		goal_Run_Start = new JButton("Goal Run Start");
		speedUp = new JButton("Speed Up");
		speedDown = new JButton("Speed Down");
		inclineUp = new JButton("Incline Up");
		inclineDown = new JButton("Incline Down");

		radioButtonsGoalRun = new JRadioButton[3];
		radioButtonsGoalRun[0] = new JRadioButton("distance", true);
		radioButtonsGoalRun[1] = new JRadioButton("duration");
		radioButtonsGoalRun[2] = new JRadioButton("calories");
		myButtonGroup = new ButtonGroup();
		myButtonGroup.add(radioButtonsGoalRun[0]);
		myButtonGroup.add(radioButtonsGoalRun[1]);
		myButtonGroup.add(radioButtonsGoalRun[2]);
		
		
		/* Set up labels */
		labelSpeed = new JLabel("Speed");
		labelIncline = new JLabel("Incline");
		labelTimeCur = new JLabel("Current Time"); 
		labelTimeElapsed = new JLabel("Elapsed Time"); 
		labelSpeedCur = new JLabel("Current Speed"); 
		labelSpeedAvg= new JLabel("Average Speed"); 
		labelInclineCur = new JLabel("Current Incline"); 
		labelDistanceCur = new JLabel("Current Distance"); 
		labelDistanceTarg = new JLabel("Target Distance"); 
		labelCaloriesCur = new JLabel("Current Calories"); 
		labelCaloriesTarg = new JLabel("Target Calories"); 
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
		blackline = BorderFactory.createLineBorder(Color.black);
		
		textFieldGoal1 = new JTextField(30);
		textFieldGoal2= new JTextField(30);
		textFieldGoal3 = new JTextField(30);


		
		/* Set up all the panels, top to bottom, left to right: */

		/* Message */
		panelMessage = new JPanel(); 
		panelMessage.setBorder(blackline);
		panelMessage.add(message);


		/* Outputs */
		panelTime = new JPanel(); 
		panelTime.setLayout(new GridLayout(5,1));
		panelTime.add(labelTimeCur);
		panelTime.add(labelTimeCurVal);
		panelTime.add(labelTimeElapsed);
		panelTime.add(labelTimeElapsedVal);

		panelSpeed = new JPanel(); 
		panelSpeed.setLayout(new GridLayout(5,1));
		panelSpeed.add(labelSpeedCur);
		panelSpeed.add(labelSpeedCurVal);
		panelSpeed.add(labelSpeedAvg);
		panelSpeed.add(labelSpeedAvgVal);

		panelIncline = new JPanel(); 
		panelIncline.setLayout(new GridLayout(5,1));
		panelIncline.add(labelInclineCur);
		panelIncline.add(labelInclineCurVal);

		panelDistance = new JPanel(); 
		panelDistance.setLayout(new GridLayout(5,1));
		panelDistance.add(labelDistanceCur);
		panelDistance.add(labelDistanceCurVal);
		panelDistance.add(labelDistanceTarg);
		panelDistance.add(labelDistanceTargVal);
		
		panelCalories = new JPanel(); 
		panelCalories.setLayout(new GridLayout(5,1));
		panelCalories.add(labelCaloriesCur);
		panelCalories.add(labelCaloriesCurVal);
		panelCalories.add(labelCaloriesTarg);
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
		panelDistanceRadio.add(textFieldGoal1);
		panelDurationRadio = new JPanel();
		panelDurationRadio.setLayout(new GridLayout(1,2));
		panelDurationRadio.add(radioButtonsGoalRun[1]);
		panelDurationRadio.add(textFieldGoal2);
		panelCaloriesRadio = new JPanel();
		panelCaloriesRadio.setLayout(new GridLayout(1,2));
		panelCaloriesRadio.add(radioButtonsGoalRun[2]);
		panelCaloriesRadio.add(textFieldGoal3);
		
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
		
		


		/* Unused? */
		panelGoalStartLeft = new JPanel();
		panelGoalStartRight = new JPanel();
	}



	/** Listener for button events */
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			/*if(event.getSource() == quickStart_Resume){
				
			}
			*/
			
		}
	}
}
