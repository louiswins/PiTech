public class WeightLossProgram {
	Session ses;
	User usr;
	double timeElapsed;
	double lockTimer;
	boolean isIncreasing;

	WeightLossProgram(Session ses, User usr) {
		this.ses = ses;
		this.usr = usr;
		setSpeed(30);
		setIncline(00);
		timeElapsed = 0.0;
		lockTimer = 0.0;
		isIncreasing = true;
	}

	public int getSpeed() {
		return (int)ses.getSpeed()*10;
	}

	public void setSpeed(int sp) {
		ses.setSpeed(sp);
		usr.setSpeed(sp);
	}
	
	public void setIncline(int in) {
		ses.setIncline(in);
	}

	public void update(double timespan) {
		timeElapsed += timespan;
		lockTimer += timespan;
		if (lockTimer > 1.0) {
			lockTimer = 0.0;
			if (isIncreasing) {
				if (getSpeed() <= 149) {
					if ((int)timeElapsed >= 60) {
						setSpeed(getSpeed()+10);
						timeElapsed = 0;
					}
				} else {
					isIncreasing = false;
				}
			} else {
				if (getSpeed() >= 31) {
					if ((int)timeElapsed >= 60) {
						setSpeed(getSpeed()-10);
						timeElapsed = 0;
					}
				} else {
					isIncreasing = true;
				}
			}
		}
	}
}
