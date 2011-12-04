/**
 * Stores the main variables and session data. This class is in charge of all
 * the visible "stuff". It knows what the treadmill is doing, what the
 * statistics are, and so forth.
 *
 * @version 1.0
 */
public class Session {
	private State state;
	private int speed;
	private int incline;
	private User runner;

	/**
	 * State of the treadmill.
	 */
	public enum State {
		RUNNING, PAUSED, STOPPED;
	}



	/**
	 * Sets up the treadmill and puts it in a stopped state.
	 *
	 * @param runner the treadmill runner
	 */
	public Session(User runner) {
		state = State.STOPPED;
		speed = 0;
		incline = 0;
		this.runner = runner;
	}


	/**
	 * Updates the state of the simulation. Updates the values for all the
	 * getWhatever functions.
	 *
	 * @param timespan amount of time in s since you last called update().
	 * @return         if the user is still on the treadmill
	 */
	public boolean update(double timespan) {
		if (state != State.RUNNING) return true;
		return runner.update(timespan, speed);
	}

	/**
	 * Sets a new speed for the treadmill.
	 *
	 * @param sp new speed in tenths of a mile per hour
	 */
	public void setSpeed(int sp) {
		System.err.printf("New speed: %f mph\n", sp / 10.0);
		speed = sp;
	}
	/**
	 * Sets a new inclination for the treadmill.
	 *
	 * @param inc new inclination in percent
	 */
	public void setIncline(int inc) {
		incline = inc;
		runner.setIncline(inc);
	}


	/**
	 * Inclination of the treadmill.
	 *
	 * @return current inclination in percent
	 */
	public int getIncline() {
		return incline;
	}
	/**
	 * Speed of the treadmill.
	 *
	 * @return current speed in mph
	 */
	public double getSpeed() {
		if (state == State.RUNNING) {
			return getSetSpeed();
		} else {
			return 0.0;
		}
	}
	/**
	 * Speed treadmill is set at, whether it's running or not.
	 *
	 * @return current speed in mph
	 */
	public double getSetSpeed() {
		return speed / 10.0;
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
		state = State.STOPPED;
		runner.reset();
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
		this.speed = speed;
		this.incline = incline;
		runner.setIncline(incline);
		start();
	}
	/**
	 * Starts the treadmill. If the treadmill is currently running or
	 * paused, this method has no effect.
	 */
	public void start() {
		if (state != State.STOPPED) return;
		runner.reorient();
		runner.recalculateHistory();
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
		runner.reorient();
		state = State.RUNNING;
	}
}
