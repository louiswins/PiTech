public class DistanceGoal implements Goal {
	private double goal;
	private double startDist;

	public DistanceGoal(double dist, Session session) {
		goal = dist;
		startDist = session.getDistance();
	}

	/** {@inheritDoc} */
	public boolean checkIfDone(Session session) {
		return (session.getDistance() - startDist) >= goal;
	}

	public String getProgress(Session session) {
		return String.format("%5.3f mi", goal - (session.getDistance() - startDist));
	}
}
