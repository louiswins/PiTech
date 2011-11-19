public interface Goal {
	/**
	 * Checks to see if the goal has been reached.
	 *
	 * @param session the current running session
	 * @return whether the goal has been met
	 */
	public boolean checkIfDone(Session session);
	public String getProgress(Session session);
}
