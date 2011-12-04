/**
 * A Program to help build cardiovascular strength. It slowly adjusts both
 * the speed and inclination up and down in a precisely-calculated manner.
 *
 * @version 0.1
 */
public class CardioProgram extends Program {
	private double timeElapsed;
	private double lockTimer;
	private boolean isIncreasing;

	/**
	 * Sets up the program with an initial speed of 1mph and inclination of
	 * 0%.
	 *
	 * @param ses session it is running on
	 * @param usr virtual runner
	 */
	public CardioProgram(Session ses, User usr) {
		super(ses, usr);
		setSpeed(10);
		setIncline(00);
		timeElapsed = 0.0;
		lockTimer = 0.0;
		isIncreasing = true;
	}

	/**
	 * Updates the speed of the treadmill. It alternately climbs and falls
	 * between 1mph and 15mph and 0% and 14%.
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
						setIncline(getIncline()+1);
						setSpeed(getSpeed()+10);
						timeElapsed = 0;
					}
				} else {
					isIncreasing = false;
				}
			} else {
				if (getSpeed() >= 31) {
					if ((int)timeElapsed >= 60) {
						setIncline(getIncline()-1);
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
