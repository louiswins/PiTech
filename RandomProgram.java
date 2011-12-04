import java.util.Random;

/**
 * A Program for fun. It sets speed and incline to a random setting
 * every minute.
 *
 * @version 0.1
 */
public class RandomProgram extends Program {
	private double timeElapsed;
	private double lockTimer;
	private boolean isIncreasing;
	Random rnd = new Random(19580427);

	/**
	 * Sets up the program with an initial speed of 1mph and inclination of
	 * 0%.
	 *
	 * @param ses session it is running on
	 * @param usr virtual runner
	 */
	public RandomProgram(Session ses, User usr) {
		super(ses, usr);
		setSpeed(10);
		setIncline(00);
		timeElapsed = 0.0;
		lockTimer = 0.0;
	}

	/**
	 * Updates the speed and inclination of the treadmill.
	 * Randomly generated.
	 *
	 * @param timespan amount of time since the last call
	 */
	public void update(double timespan) {
		/* We don't do anything if it's paused or stopped. */
		if (ses.getState() != Session.State.RUNNING) return;
		timeElapsed += timespan;
		lockTimer += timespan;
		if (lockTimer > 60.0) {
			lockTimer = 0.0;
			setSpeed(rnd.nextInt(140)+1);
			setIncline(rnd.nextInt(14)+1);
			timeElapsed = 0;
		}
	}
}
