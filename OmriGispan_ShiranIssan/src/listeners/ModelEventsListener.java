package listeners;

import Model.Department;
import Model.Role;

public interface ModelEventsListener {
	void setedCompanyNameFromCompany(String name);
	void addedDepartmentFromCompany(String departmentName);
	void addedRoleFromCompany(String roleName, int departmentIndex);
	void addedEmployeeFromCompany(String name, Role role, Department department,int begHour, String pref);
	void sendCompanyDetails(String toString);
}
