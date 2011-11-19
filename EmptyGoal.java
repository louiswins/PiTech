public class EmptyGoal implements Goal {
	/**
	 * Always returns false. If there's no goal to reach, you can never
	 * reach it.
	 *
	 * @return false
	 */
	public boolean checkIfDone(Session session) {
		return false;
	}

	public String getProgress(Session session) {
		return "empty";
	}
}
