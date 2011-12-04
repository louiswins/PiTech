/**
 * A Program. It allows you to specify a pattern that the treadmill will follow
 * by itself, without outside interaction.
 *
 * @version 0.3
 */
public class Program {
	protected Session ses;
	protected User usr;

	/**
	 * Initialize the variables.
	 *
	 * @param ses the treadmill session
	 * @param usr the virtual runner
	 */
	public Program(Session ses, User usr) {
		this.ses = ses;
		this.usr = usr;
	}

	/**
	 * Gets the speed of the treadmill, truncated to an integer number of
	 * miles per hour. If you are running at 3.5mph, it will return 30.
	 *
	 * @return speed in tens of mph
	 */
	protected int getSpeed() {
		return (int)ses.getSetSpeed()*10;
	}

	protected int getIncline() {
		return ses.getIncline();
	}

	/**
	 * Sets the speed of the user and treadmill.
	 *
	 * @param sp speed in tens of mph
	 */
	protected void setSpeed(int sp) {
		ses.setSpeed(sp);
		usr.setSpeed(sp);
	}

	/**
	 * Sets the incline of the treadmill.
	 *
	 * @param in incline in percent
	 */
	protected void setIncline(int in) {
		ses.setIncline(in);
	}

	/**
	 * Does nothing. If you are extending this class, this is the most
	 * important method to override.
	 *
	 * @param timespan the number of seconds since the last call
	 */
	public void update(double timespan) {}
}
