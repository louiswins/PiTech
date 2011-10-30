public class DistanceGoal implements Goal {
	private int goal;

	public DistanceGoal(int dist) {
		goal = dist;
	}

	/** {@inheritDoc} */
	public boolean checkIfDone(Session session) {
		return session.getDistance() >= goal;
	}
}
