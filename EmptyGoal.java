public class EmptyGoal implements Goal {
	/**
	 * Always returns false. If there's no goal to reach, you can never
	 * reach it.
	 *
	 * @return false
	 */
	public boolean checkIfDone() {
		return false;
	}

	/** {@inheritDoc} */
	public String getProgress() {
		return "empty";
	}
}
