/**
 * A class to save the history for a certain time with constant speed and
 * inclination.
 *
 * @version 0.3
 */
public class HistoryData {
	private int speed; // tenths of a mph
	private int incline; // slope %
	double duration; // seconds


	/**
	 * Creates a HistoryData object with a start time of the time of the
	 * call.
	 *
	 * @param sp    speed in tenths of a mile per hour
	 * @param inc   inclination in percent
	 */
	public HistoryData(int sp, int inc) {
		speed = sp;
		incline = inc;
		duration = 0.0;
	}


	public void update(double timespan) {
		duration += timespan;
	}


	/**
	 * Returns the total distance run during this object's lifetime.
	 *
	 * @return distance run in miles
	 */
	public double getDistance() {
		// miles = miles/hr * s * hr/s
		return (double)speed/10.0 * duration / 3600.0;
	}

	/**
	 * Returns the lifespan of the object.
	 *
	 * @return time the object was alive in seconds
	 */
	public double getTime() { return duration; }

	/**
	 * Returns the speed.
	 *
	 * @return speed running in tenths of a mile per hour
	 */
	public int getSpeed() { return speed; }

	/**
	 * Returns the incline of the treadmill.
	 *
	 * @return inclination in percent
	 */
	public int getIncline() { return incline; }

	/**
	 * Returns the number of calories burnt during the lifetime of the
	 * object. Formula is from <a
	 * href="http://www.runnersworld.com/article/0,7120,s6-242-304-311-8402-0,00.html">Runner's
	 * World</a>.
	 *
	 * @param age    age of user in years
	 * @param weight weight in lbs
	 * @return       calories burnt
	 */
	// XXX: This calculation is not very good, it should be changed later
	// 0.75 * weight * #miles
	public double getCalories(int age, int weight) {
		return (0.75 * weight * getDistance());
	}
}
