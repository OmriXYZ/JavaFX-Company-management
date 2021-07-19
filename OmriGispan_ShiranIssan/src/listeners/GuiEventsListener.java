package listeners;

import Model.Department;
import Model.Role;

public interface GuiEventsListener {
	void setCompanyNameFromGui(String name);
	void addDepartmentFromGui(String name, boolean mustEmployeeSync, boolean canChangePreferences);
	void addRoleFromGui(String name, boolean mustEmployeeSync, boolean canChangePreferences, int indexDepartment);
	void addEmployeeFromGui(String name, int indexRole, int indexDepartment, int begHour, String pref) throws Exception;
	
}
