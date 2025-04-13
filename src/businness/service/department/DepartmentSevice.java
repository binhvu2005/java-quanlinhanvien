package businness.service.department;

import businness.model.department.Department;
import businness.model.department.DepartmentStatus;

import java.util.List;

public interface DepartmentSevice {
    int getPageDepartment();
    List<Department> getDepartmentByPage(int page);

    void addDepartment(String departmentName, String departmentDescription);

    Department getDepartmentById(int id);

    void updateDepartmentName(int departmentId, String departmentName);
    void updateDepartmentDescription(int departmentId, String departmentDescription);
    void updateDepartmentStatus(int departmentId, DepartmentStatus status);

    void deleteDepartment(int departmentId);
    List<Department> getDepartmentByName(String departmentName);
}
