/**
 * A Program to help lose weight. It slowly adjusts the speed up and down in a
 * precisely-calculated manner.
 *
 * @version 0.7
 */
public class WeightLossProgram extends Program {
	private double timeElapsed;
	private double lockTimer;
	private boolean isIncreasing;

	/**
	 * Sets up the program with an initial speed of 3mph and inclination of
	 * 0%.
	 *
	 * @param ses session it is running on
	 * @param usr virtual runner
	 */
	public WeightLossProgram(Session ses, User usr) {
		super(ses, usr);
		setSpeed(30);
		setIncline(00);
		timeElapsed = 0.0;
		lockTimer = 0.0;
		isIncreasing = true;
	}

	/**
	 * Updates the speed of the treadmill. It alternately climbs and falls
	 * between 15mph and 30 mph.
	 *
	 * @param timespan amount of time since the last call
	 */
	public void update(double timespan) {
		/* We don't do anything if it's paused or stopped. */
		if (ses.getState() != Session.State.RUNNING) return;
		timeElapsed += timespan;
		lockTimer += timespan;
		if (lockTimer > 1.0) {
			lockTimer = 0.0;
			if (isIncreasing) {
				if (getSpeed() <= 149) {
					if ((int)timeElapsed >= 60) {
						setSpeed(getSpeed()+10);
						timeElapsed = 0;
					}
				} else {
					isIncreasing = false;
				}
			} else {
				if (getSpeed() >= 31) {
					if ((int)timeElapsed >= 60) {
						setSpeed(getSpeed()-10);
						timeElapsed = 0;
					}
				} else {
					isIncreasing = true;
				}
			}
		}
	}
}
