import java.util.ArrayList;
import java.util.List;

/**
 * Stores the main variables and session data. This class is in charge of all
 * the visible "stuff". It knows what the treadmill is doing, what the
 * statistics are, and so forth.
 *
 * @version 1.0
 */
public class Session {
	private List<HistoryData> history;
	private HistoryData currentValues;
	private State state;
	private History historyStore;
	private int age, weight;

	/* Cache variables: these should be the sums of all the values in
	 * this.history; to get usable data while running, add in the values in
	 * currentValues. */
	private double timeElapsedCache;
	private double totalDistanceCache;

	/**
	 * State of the treadmill.
	 */
	public enum State {
		RUNNING, PAUSED, STOPPED;
	}



	/**
	 * Sets up the treadmill and puts it in a stopped state. To actually
	 * start the treadmill, you must call {@link #start(int, int)}.
	 */
	public Session(History hist, int age, int weight) {
		// Pre-allocate memory for 50. Nice!
		history = new ArrayList<HistoryData>(50);
		historyStore = hist;
		this.age = age;
		this.weight = weight;
		timeElapsedCache = 0;
		totalDistanceCache = 0.0;
		currentValues = new HistoryData(0, 0);
		state = State.STOPPED;
	}


	/**
	 * Updates the state of the simulation. Updates the values for all the
	 * getWhatever functions.
	 *
	 * @param timespan amount of time in s since you last called update().
	 */
	public void update(double timespan) {
		if (state != State.RUNNING) return;
		timeElapsedCache += timespan;
		currentValues.update(timespan);
	}

	/**
	 * Sets a new speed for the treadmill.
	 *
	 * @param sp new speed in tenths of a mile per hour
	 */
	public void setSpeed(int sp) {
		int inclineSetting = currentValues.getIncline();
		if (currentValues.getTime() > 0.0) {
			saveHistoryData();
		}
		currentValues = new HistoryData(sp, inclineSetting);
	}
	/**
	 * Sets a new inclination for the treadmill.
	 *
	 * @param inc new inclination in percent
	 */
	public void setIncline(int inc) {
		int speedSetting = currentValues.getSpeed();
		if (currentValues.getTime() > 0.0) {
			saveHistoryData();
		}
		currentValues = new HistoryData(speedSetting, inc);
	}


	/**
	 * Inclination of the treadmill.
	 *
	 * @return current inclination in percent
	 */
	public int getIncline() {
		return currentValues.getIncline();
	}
	/**
	 * Speed of the treadmill.
	 *
	 * @return current speed in tenths of a mile per hour
	 */
	public int getSpeed() {
		if (state == State.RUNNING) {
			return currentValues.getSpeed();
		} else {
			return 0;
		}
	}

	/**
	 * Average speed throughout the session.
	 *
	 * @return average speed in miles per hour
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
	 * @param age    age of runner in years.
	 * @param weight weight of runner in lbs.
	 * @return calories burnt while running
	 */
	public int getCalories(int age, int weight) {
		if (state == State.STOPPED) {
			return 0;
		}
		double cal = currentValues.getCalories(age, weight);
		for (HistoryData curHist : history) {
			cal += curHist.getCalories(age, weight);
		}
		return (int)cal;
	}

	/**
	 * Current state of the treadmill.
	 *
	 * @return state of the treadmill
	 */
	public State getState() {
		return state;
	}

	/**
	 * Stops the treadmill. Forgets all of the session data including time
	 * elapsed, distance run, etc.
	 */
	public void stop() {
		timeElapsedCache = 0.0;
		totalDistanceCache = 0.0;
		
		if (state != State.STOPPED) {
			// If the state is already stopped, no need to
			// reallocate new objects
			history = new ArrayList<HistoryData>(50);
			currentValues = new HistoryData(0, 0);
		}
		state = State.STOPPED;
	}
	/**
	 * Starts the treadmill. If the treadmill is currently running or
	 * paused, this method has no effect.
	 *
	 * @param speed   speed in tenths of a mile per hour
	 * @param incline inclination in percent
	 */
	public void start(int speed, int incline) {
		if (state != State.STOPPED) return;
		currentValues = new HistoryData(speed, incline);
		state = State.RUNNING;
	}
	/**
	 * Starts the treadmill. If the treadmill is currently running or
	 * paused, this method has no effect.
	 */
	public void start() {
		if (state != State.STOPPED) return;
		state = State.RUNNING;
	}
	/**
	 * Pauses the treadmill. All data are retained. If the treadmill is
	 * already paused or stopped, this method has no effect.
	 */
	public void pause() {
		if (state != State.RUNNING) return;
		state = State.PAUSED;
	}
	/**
	 * Resumes from a paused state. If the treadmill is already going, or
	 * has been stopped completely, this method does nothing.
	 */
	public void resume() {
		if (state != State.PAUSED) return;
		state = State.RUNNING;
	}


	/**
	 * Saves the history data. Call this when you want to put
	 * <tt>currentValues</tt> into the <tt>history</tt> data store.
	 */
	private void saveHistoryData() {
		updateCaches(currentValues.getDistance());
		history.add(currentValues);
		/** Format history record. */
		String line = "Distance: " + String.format("%5.3f mi", currentValues.getDistance())
									+ "     Time: " + String.format("%02d:%02d:%02d", (int)(currentValues.getTime() / 3600), (int)(currentValues.getTime() / 60) % 60, (int)(currentValues.getTime()) % 60)
									+ "     Speed: " + String.format("%5.3f mph", (double)currentValues.getSpeed() / 10.0)
									+ "     Incline: " + currentValues.getIncline()
									+ "     Calories: " + (int)currentValues.getCalories(age, weight);
		/** Write to history tab. */
		historyStore.updateHistory(line);
	}
	/**
	 * Updates the distance cache. Should be called every time a {@link
	 * HistoryData} object is added to the <tt>history</tt> data store. If
	 * you forget to call this method, at least {@link
	 * #getCalories(int,int)} will be off.
	 *
	 * <p>Note that {@link #saveHistoryData()} calls this method, so if you
	 * operate through it, you will be A-OK.
	 *
	 * @param distToAdd additional distance in miles
	 */
	private void updateCaches(double distToAdd) {
		totalDistanceCache += distToAdd;
	}
}
