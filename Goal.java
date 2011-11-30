public interface Goal {
	/**
	 * Checks to see if the goal has been reached.
	 *
	 * @return whether the goal has been met
	 */
	public boolean checkIfDone();
	/**
	 * Returns a string with the current progress.
	 *
	 * @return a description of the progress towards the goal
	 */
	public String getProgress();
}
