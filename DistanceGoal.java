public class DistanceGoal implements Goal {
	private double goal;
	private double startDist;
	private User runner;

	public DistanceGoal(double dist, User runner) {
		goal = dist;
		this.runner = runner;
		startDist = runner.getDistance();
	}

	/** {@inheritDoc} */
	public boolean checkIfDone() {
		return (runner.getDistance() - startDist) >= goal;
	}

	/** {@inheritDoc} */
	public String getProgress() {
		return String.format("%5.3f mi", goal - (runner.getDistance() - startDist));
	}
}
