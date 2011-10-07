/**
 * A class to save the history for a certain time with constant speed and
 * inclination.
 *
 * @version 0.1
 */
public class HistoryData {
	private int speed; // mph
	private int incline; // degrees
	private long time; // ms


	/**
	 * Creates a HistoryData object without a time spanned. This
	 * constructor is meant for a HistoryData object which is currently
	 * being ran (to take advantage of, for example, the {@link
	 * #calculateCalories(int, long)} method). In this case, you must
	 * specify the timespan ran in all of the calculation methods.
	 *
	 * @param vel velocity in miles per hour
	 * @param inc inclination in degrees
	 */
	public HistoryData(int vel, int inc) {
		this(vel, inc, 0);
	}
	/**
	 * Creates a HistoryData object with a time spanned.
	 *
	 * @param sp  velocity in miles per hour
	 * @param inc inclination in degrees
	 * @param ts  time in milliseconds
	 */
	public HistoryData(int sp, int inc, long ts) {
		speed = sp;
		incline = inc;
		time = ts;
	}



	/**
	 * Sets the timespan.
	 *
	 * @param ts time in milliseconds
	 */
	public void setTime(long ts) { time = ts; }

	/**
	 * Returns the total distance run during this object's lifetime.
	 *
	 * @return distance run in miles
	 */
	public double getDistance() {
		return getDistance(time);
	}
	/**
	 * Returns the distance run during the timespan specified.
	 *
	 * @param ts time in milliseconds
	 * @return   distance run in miles
	 */
	public double getDistance(long ts) {
		// miles = miles/hr * ms * (s/ms * hr/s)
		return (double)(speed * ts) / 3600000.0;
	}

	/**
	 * Returns the lifespan of the object.
	 *
	 * @return time the object was alive in milliseconds
	 */
	public long getTime() { return time; }

	/**
	 * Returns the speed.
	 *
	 * @return speed ran in miles per hour
	 */
	public int getSpeed() { return speed; }

	/**
	 * Returns the incline of the treadmill.
	 *
	 * @return inclination in degrees
	 */
	public int getIncline() { return incline; }

	/**
	 * Returns the number of calories burnt during the timespan specified.
	 *
	 * @param weight weight of the runner in pounds
	 * @param ts     time in milliseconds
	 * @return       calories burnt
	 */
	// XXX: This calculation is not very good, it should be changed later
	public int calculateCalories(int weight, long ts) {
		return (int) (0.75 * weight * ts);
	}
	/**
	 * Returns the number of calories burnt during the lifetime of the
	 * object.
	 *
	 * @param weight weight of the runner in pounds
	 * @return       calories burnt
	 */
	public int calculateCalories(int weight) {
		return calculateCalories(weight, time);
	}
}
