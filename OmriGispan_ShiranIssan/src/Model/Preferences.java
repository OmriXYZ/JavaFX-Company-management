package Model;

public interface Preferences {
//	public void employeeSync(boolean choise);
//	public void changeWorkHours(boolean choise);
	public void startWorkEarlier(int begHour);
	public void startWorkLater(int begHour);
	public void stayOnBasicWork(); //stay on the basic working hours
	public void workInHome();
}
