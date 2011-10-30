public class DistanceGoal implements Goal {
	private double goal;

	public DistanceGoal(double dist) {
		goal = dist;
	}

	/** {@inheritDoc} */
	public boolean checkIfDone(Session session) {
		return session.getDistance() >= goal;
	}
}
