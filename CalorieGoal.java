public class CalorieGoal implements Goal {
	private int goal;
	private int age, weight;

	public CalorieGoal(int cal, int age, int weight) {
		goal = cal;
		this.age = age;
		this.weight = weight;
	}

	/** {@inheritDoc} */
	public boolean checkIfDone(Session session) {
		return session.getCalories(age, weight) >= goal;
	}
}
