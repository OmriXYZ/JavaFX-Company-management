package listeners;

import Model.Department;
import Model.Role;

public interface GuiEventsListener {
	void setCompanyNameFromGui(String name);
	void addDepartmentFromGui(String name);
	void addRoleFromGui(String name, int indexDepartment);
	void addEmployeeFromGui(String name, int indexRole, int indexDepartment, int begHour, String pref, String typeOfWorker, int hoursMonth) throws Exception;
	void setPrefDepartmentFromGui(int indexDepartment, boolean b);
	void setSyncDepartmentFromGui(int indexDepartment, boolean b, int syncHour, int endHour);
	void setPrefRoleFromGui(int indexDepartment, int indexRole, boolean b);
	void setSyncRoleFromGui(int indexDepartment, int indexRole, boolean b, int syncHour, int endHour);
	boolean getPrefDepartmentFromGui(int indexDepartment);
	boolean getSyncDepartmentFromGui(int indexDepartment);
	boolean getPrefRoleFromGui(int indexDepartment, int indexRole);
	boolean getSyncRoleFromGui(int indexDepartment, int indexRole);
	void calculateEfficiencyFromGui();
	void saveDataFromUi();
	void loadDataFromUi();
}
