public class CalorieGoal implements Goal {
	private int goal;
	private int age, weight;
	private int startCals;

	public CalorieGoal(int cal, int age, int weight, Session session) {
		goal = cal;
		this.age = age;
		this.weight = weight;
		startCals = session.getCalories(age, weight);
	}

	/** {@inheritDoc} */
	public boolean checkIfDone(Session session) {
		return (session.getCalories(age, weight) - startCals) >= goal;
	}

	public String getProgress(Session session) {
		return Integer.toString(goal - (session.getCalories(age,weight) - startCals)) + " cal";
	}
}
