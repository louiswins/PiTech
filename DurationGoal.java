public class DurationGoal implements Goal {
	private double goal;

	public DurationGoal(double dur) {
		goal = dur;
	}

	/** {@inheritDoc} */
	public boolean checkIfDone(Session session) {
		return session.getTimeElapsed() >= goal;
	}
}
