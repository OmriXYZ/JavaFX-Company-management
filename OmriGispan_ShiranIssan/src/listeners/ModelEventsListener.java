package listeners;

import Model.Department;
import Model.Role;

public interface ModelEventsListener {
	void setedCompanyNameFromCompany(String name);
	void addedDepartmentFromCompany(String name, boolean mustEmployeeSync, boolean canChangePreferences);
	void addedRoleFromCompany(Role role);
	void addedEmployeeFromCompany(int iD, String name, String nameRole, String nameDepartment, int begHour, int endHour, String pref);
}
