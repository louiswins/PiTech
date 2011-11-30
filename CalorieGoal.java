public class CalorieGoal implements Goal {
	private int goal;
	private User runner;
	private int startCals;

	public CalorieGoal(int cal, User runner) {
		goal = cal;
		this.runner = runner;
		startCals = runner.getCalories();
	}

	/** {@inheritDoc} */
	public boolean checkIfDone() {
		return (runner.getCalories() - startCals) >= goal;
	}

	/** {@inheritDoc} */
	public String getProgress() {
		return Integer.toString(goal - (runner.getCalories() - startCals)) + " cal";
	}
}
