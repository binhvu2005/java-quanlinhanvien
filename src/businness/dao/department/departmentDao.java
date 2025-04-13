package businness.dao.department;

import businness.dao.AppDao;
import businness.model.department.Department;
import businness.model.department.DepartmentStatus;

import java.util.List;

public interface departmentDao extends AppDao {
    int getPageDepartment();
    List<Department> getDepartmentByPage(int page);
	void addDepartment (String departmentName, String departmentDescription);
    Department getDepartmentById(int id);

    void updeteDepartmentbyName(int departmentId, String departmentName);
    void updeteDepartmentbyDescrition(int departmentId, String departmentDescription);
    void updeteDepartmentbyStatus (int departmentId, int status);

    void updeteDepartmentbyStatus(int departmentId, DepartmentStatus status);

    void deleteDepartment(int departmentId);
    List<Department> getDepartmentByName(String departmentName);

}
