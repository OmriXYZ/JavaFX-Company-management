package listeners;

import java.util.ArrayList;

import Model.Department;
import Model.Role;

public interface ModelEventsListener {
	void setedCompanyNameFromCompany(String name);
	void addedDepartmentFromCompany(String departmentName);
	void addedRoleFromCompany(String roleName, int departmentIndex);
	void addedEmployeeFromCompany(String name);
	void sendCompanyDetails(String toString);
	void sendTotalEfficiency(Double totalEfficiency);
	void sendDepartmentsEfficiency(ArrayList<Double> departmentsEfficiency);
	void sendEmployeesEfficiency(ArrayList<Double> employeesEfficiency);
	void sendDataMsg(String dataMsg);

}
