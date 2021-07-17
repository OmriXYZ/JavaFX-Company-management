package View;

import Model.Department;
import Model.Role;
import listeners.GuiEventsListener;

public interface AbstractCompanyView {
	void registerListener(GuiEventsListener listener);
	void setCompanyNameToGui(String name);
	void addDepartmentToGui(Department department);
	void addRoleToGui(String name, boolean mustEmployeeSync, boolean canChangeWorkHours, int indexDepartment);
	void addEmployeeToGui(int iD, String name, Role role, Department department, int begHour, int endHour, String pref);
}
