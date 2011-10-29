public class SessionTest {
	public static void main(String[] args) {
		Session s = new Session();
		// start running at 10mph, 0% incline
		s.start(100, 0);

		// run for 5min
		s.update(5*60);
		System.out.printf("Ran for 5min at %f mph; have run %f miles, burned %d calories.\n",
				(double)s.getSpeed() / 10.0, s.getDistance(), s.getCalories(21, 180));

		s.setSpeed(60);

		s.update(2*60);
		System.out.printf("Ran for 2min at %f mph; have run %f miles, burned %d calories.\n",
				(double)s.getSpeed() / 10.0, s.getDistance(), s.getCalories(21, 180));

		s.pause();
		s.resume();
		System.out.printf("Paused treadmill, then resumed at %f mph; have run %f miles, burned %d calories.\n",
				(double)s.getSpeed() / 10.0, s.getDistance(), s.getCalories(21, 180));

		s.setSpeed(80);
		s.update(5*60);
		System.out.printf("Ran for 5min at %f mph; have run %f miles, burned %d calories.\n",
				(double)s.getSpeed() / 10.0, s.getDistance(), s.getCalories(21, 180));


		s.stop();
		s.resume();
		s.update(100);
		System.out.printf("Treadmill is stopped; speed = %f mph, distance = %f miles, burned %d calories.\n",
				(double)s.getSpeed() / 10.0, s.getDistance(), s.getCalories(21, 180));
	}
}
