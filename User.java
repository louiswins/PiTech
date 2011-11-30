import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a user.
 *
 * @version 0.2
 */
public class User {
	private List<HistoryData> history;
	private HistoryData currentValues;
	private History historyStore;
	private int age;
	private int weight;
	private double position;

	/* Cache variables: these should be the sums of all the values in
	 * this.history; to get usable data while running, add in the values in
	 * currentValues. */
	private double timeElapsedCache;
	private double totalDistanceCache;

	/** Half the length of the treadmill, in feet. */
	private static final double HALF_TM_LENGTH = 2.5;

	/**
	 * Default constructor. Uses default values of 30 years and 150 lbs.
	 *
	 * @param hist history tab that we'll update
	 */
	public User(History hist) {
		this(hist, 30, 150);
	}
	/**
	 * User constructor.
	 *
	 * @param hist   history tab that we'll update
	 * @param age    age in years
	 * @param weight weight in lbs
	 */
	public User(History hist, int age, int weight) {
		historyStore = hist;
		this.age = age;
		this.weight = weight;
		reset();
	}

	/**
	 * Forgets the history data. Resets as if to a newly-created object.
	 */
	public void reset() {
		reorient();
		timeElapsedCache = 0.0;
		totalDistanceCache = 0.0;
		/* Pre-allocate memory for 50 */
		history = new ArrayList<HistoryData>(50);
		currentValues = new HistoryData(0, 0);
	}
	/**
	 * Puts the runner back in the middle of the treadmill. This should be
	 * called, e.g., when the treadmill is paused or stopped.
	 */
	public void reorient() {
		position = 0;
	}

	/**
	 * Updates the speed and position of the runner based on the speed of
	 * the treadmill.
	 *
	 * @param timeSpan       number of seconds since last update
	 * @param treadmillSpeed speed of the treadmill in tenths of a mph
	 * @return               if the user is safely on the treadmill
	 */
	public boolean update(double timeSpan, int treadmillSpeed) {
 		// feet = 10*mph * s * hr/s * (feet/mile / 10)
		position += (currentValues.getSpeed() - treadmillSpeed) * timeSpan / 3600.0 * 528.0;
		timeElapsedCache += timeSpan;
		currentValues.update(timeSpan);
		return (position >= (-1.0) * HALF_TM_LENGTH) && (position <= HALF_TM_LENGTH);
	}

	/**
	 * Sets a new speed for the runner.
	 *
	 * @param sp new speed in tenths of a mile per hour
	 */
	public void setSpeed(int sp) {
		if (currentValues.getSpeed() == sp) return;
		int inclineSetting = currentValues.getIncline();
		if (currentValues.getTime() > 0.0) {
			saveHistoryData();
		}
		currentValues = new HistoryData(sp, inclineSetting);
	}
	/**
	 * Sets a new inclination for the runner.
	 *
	 * @param inc new inclination in percent
	 */
	public void setIncline(int inc) {
		if (currentValues.getIncline() == inc) return;
		int speedSetting = currentValues.getSpeed();
		if (currentValues.getTime() > 0.0) {
			saveHistoryData();
		}
		currentValues = new HistoryData(speedSetting, inc);
	}


	/**
	 * Speed of the runner.
	 *
	 * @return current speed in mph
	 */
	public double getSpeed() {
		return currentValues.getSpeed() / 10.0;
	}

	/**
	 * Average speed throughout the session.
	 *
	 * @return average speed in mph
	 */
	public double getAverageSpeed() {
		if (timeElapsedCache > 0.0) {
			return getDistance() * 3600.0 / timeElapsedCache;
		} else {
			return 0.0;
		}
	}

	/**
	 * Total distance run.
	 *
	 * @return distance run in miles
	 */
	public double getDistance() {
		return totalDistanceCache + currentValues.getDistance();
	}
	/**
	 * Total time spent running
	 *
	 * @return time run in seconds
	 */
	public double getTimeElapsed() {
		return timeElapsedCache;
	}

	/**
	 * Calories burnt.
	 *
	 * @return calories burnt while running
	 */
	public int getCalories() {
		double cal = currentValues.getCalories(age, weight);
		for (HistoryData curHist : history) {
			cal += curHist.getCalories(age, weight);
		}
		return (int)cal;
	}

	/**
	 * Returns the relative position of the user.
	 *
	 * @return Distance from center of treadmill in feet
	 */
	public double getPosition() { return position; }
	/**
	 * Returns the weight of the user.
	 *
	 * @return Weight of the user in lbs
	 */
	public int getWeight() { return weight; }
	/**
	 * Returns the age of the user.
	 *
	 * @return Age of the user in years
	 */
	public int getAge() { return age; }
	/**
	 * Sets the weight of the user.
	 *
	 * @param weight weight of the user in lbs
	 */
	public void setWeight(int weight) {
		this.weight = weight;
		/* We need to update the history; calories are calculated based
		 * on the weight. */
		recalculateHistory();
	}
	/**
	 * Sets the age of the user.
	 *
	 * @param age age of the user in years
	 */
	public void setAge(int age) { this.age = age; }


	/**
	 * Saves the history data. Call this when you want to put
	 * <tt>currentValues</tt> into the <tt>history</tt> data store.
	 */
	private void saveHistoryData() {
		totalDistanceCache += currentValues.getDistance();
		history.add(currentValues);
		/* Let's only do this if the time is >= 1s because otherwise the
		 * user was probably just scrolling. */
		if (currentValues.getTime() < 1.0) return;
		/* Write to history tab. */
		historyStore.updateHistory(currentValues.toString(age, weight));
	}

	/**
	 * Recalculates the entire history tab from scratch.
	 */
	public void recalculateHistory() {
		historyStore.resetHistory();
		for (HistoryData curHist : history) {
			historyStore.updateHistory(curHist.toString(age, weight));
		}
	}
}
