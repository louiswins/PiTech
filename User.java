import java.lang.Math;

/**
 * A class to represent a user.
 *
 * @version 0.1
 */
public class User {
	private double speed; // tenths of a mph
	private double position; // offset in feet from center of treadmill
	private int age; // years
	private int weight; // pounds
	// length of treadmill belt in feet
	private final static double TREADMILL_LENGTH = 4.0;


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
		this.speed = 0;
		this.position = 0;
	}

	/**
	 * Returns the current true speed of the user.
	 *
	 * @return Speed of user in tenths of a mile per hour
	 */
	public double getSpeed() throws UserFellOffException {
		if (!isOnTreadmill()) {
			throw new UserFellOffException();
		}
		return speed;
	}
	/**
	 * Returns the relative position of the user.
	 *
	 * @return Distance from center of treadmill in feet
	 */
	public double getPosition() throws UserFellOffException {
		if (!isOnTreadmill()) {
			throw new UserFellOffException();
		}
		return position;
	}
	/**
	 * Returns the weight of the user.
	 *
	 * @return Weight of the user in lbs
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * Returns the age of the user.
	 *
	 * @return Age of the user in years
	 */
	public int getAge() {
		return age;
	}
	/**
	 * Determines if a user is still on the treadmill.
	 *
	 * @return If user is on treadmill
	 */
	private boolean isOnTreadmill() {
		return Math.abs(position) < TREADMILL_LENGTH / 2.0;
	}
}
