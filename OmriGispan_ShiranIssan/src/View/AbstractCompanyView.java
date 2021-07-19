package View;

import Model.Department;
import Model.Role;
import listeners.GuiEventsListener;

public interface AbstractCompanyView {
	void registerListener(GuiEventsListener listener);
	void setCompanyNameToGui(String name);
	void addDepartmentToGui(String departmentName);
	void addRoleToGui(String roleName, int roleIndex);
	void addEmployeeToGui(int iD, String name, Role role, Department department, int begHour, int endHour, String pref);
	void addCompanyDetailsToGui(String toString);

}
