/**
 * A Program to simulate running up and down hills. It slowly adjusts the 
 * incline up and down in a precisely-calculated manner.
 *
 * @version 0.1
 */
public class HillProgram extends Program {
	private double timeElapsed;
	private double lockTimer;
	private boolean isIncreasing;

	/**
	 * Sets up the program with an initial inclination of 0%.
	 *
	 * @param ses session it is running on
	 * @param usr virtual runner
	 */
	public HillProgram(Session ses, User usr) {
		super(ses, usr);
		setIncline(00);
		timeElapsed = 0.0;
		lockTimer = 0.0;
		isIncreasing = true;
	}

	/**
	 * Updates the inclination of the treadmill. It alternately climbs and falls
	 * between 0% and 15%.
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
				if (getIncline() <= 14) {
					if ((int)timeElapsed >= 60) {
						setIncline(getIncline()+1);
						timeElapsed = 0;
					}
				} else {
					isIncreasing = false;
				}
			} else {
				if (getIncline() >= 1) {
					if ((int)timeElapsed >= 60) {
						setIncline(getIncline()-1);
						timeElapsed = 0;
					}
				} else {
					isIncreasing = true;
				}
			}
		}
	}
}
