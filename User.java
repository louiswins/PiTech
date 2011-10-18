import java.lang.Math;
import java.util.Random;

/**
 * A class to represent a user.
 *
 * @version 0.2
 */
public class User {
	private double speed; // tenths of a mph
	private double position; // offset in feet from center of treadmill
	private int age; // years
	private int weight; // pounds
	private Random gen;

	/**
	 * Average (gaussian) variation in treadmill speed and user speed
	 */
	private static final double VARIATION = 0.05;

	/**
	 * Default constructor. Uses default values of 25 years and 180 lbs.
	 */
	public User() {
		this(25, 180);
	}
	/**
	 * User constructor.
	 *
	 * @param age    Age in years
	 * @param weight Weight in lbs
	 */
	public User(int age, int weight) {
		this.age = age;
		this.weight = weight;
		speed = 0;
		position = 0;
		gen = new Random();
	}

	/**
	 * Updates the speed and position of the runner based on the speed of
	 * the treadmill.
	 *
	 * @param treadmillSpeed speed of the treadmill in tenths of a mile per
	 *                       hour
	 * @param timeStep       number of milliseconds since last update
	 */
	public void updateSpeedPos(double treadmillSpeed, long timeStep) {
		double factor = 1.0 + VARIATION * gen.nextGaussian();
		speed = treadmillSpeed * factor; // mph / 10
 		// feet = mph * ms * hr/ms * feet/mile
		position += (speed - treadmillSpeed)*10.0 * timeStep / 3600000.0 * 5280.0;
	}
	/**
	 * Returns the current true speed of the user.
	 *
	 * @return Speed of user in tenths of a mile per hour
	 */
	public double getSpeed() { return speed; }
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
}
