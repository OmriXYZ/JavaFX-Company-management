package View;

import java.util.ArrayList;

import Model.Department;
import Model.Role;
import listeners.GuiEventsListener;

public interface AbstractCompanyView {
	void registerListener(GuiEventsListener listener);
	void setCompanyNameToGui(String name);
	void addDepartmentToGui(String departmentName);
	void addRoleToGui(String roleName, int roleIndex);
	void addEmployeeToGui(String name);
	void addCompanyDetailsToGui(String toString);
	void sendTotalEfficiencyToGui(Double totalEfficiency);
	void sendDepartmentsEfficiencyToGui(ArrayList<Double> departmentsEfficiency);
	void sendEmployeesEfficiencyToGui(ArrayList<Double> employeesEfficiency);
	void dialog(String msg);
	void loadData();
}
