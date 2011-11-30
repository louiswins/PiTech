public class DurationGoal implements Goal {
	private double goal;
	private double startTime;
	private User runner;

	public DurationGoal(double dur, User runner) {
		goal = dur*60;
		this.runner = runner;
		startTime = runner.getTimeElapsed();
	}

	/** {@inheritDoc} */
	public boolean checkIfDone() {
		return (runner.getTimeElapsed() - startTime) >= goal;
	}

	/** {@inheritDoc} */
	public String getProgress() {
		int timeElapsed = (int)((goal - (runner.getTimeElapsed() - startTime)));
		return String.format("%02d:%02d:%02d", timeElapsed / 3600,
				(timeElapsed / 60) % 60, timeElapsed % 60);
	}
}
