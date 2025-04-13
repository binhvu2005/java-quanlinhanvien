package businness.service.department;

import businness.dao.department.departmentDao;
import businness.dao.department.DepartmentDaoImp;
import businness.model.department.Department;
import businness.model.department.DepartmentStatus;

import java.util.List;

public class DepartmentSeviceImp implements DepartmentSevice {
    private departmentDao dao;

    public DepartmentSeviceImp() {
        this.dao = new DepartmentDaoImp(); // Triển khai DAO
    }

    @Override
    public int getPageDepartment() {
        return dao.getPageDepartment();
    }

    @Override
    public List<Department> getDepartmentByPage(int page) {
        return dao.getDepartmentByPage(page);
    }

    @Override
    public void addDepartment(String departmentName, String departmentDescription) {
        dao.addDepartment(departmentName, departmentDescription);
    }

    @Override
    public Department getDepartmentById(int id) {
        return dao.getDepartmentById(id);
    }

    @Override
    public void updateDepartmentName(int departmentId, String departmentName) {
        dao.updeteDepartmentbyName(departmentId, departmentName);
    }

    @Override
    public void updateDepartmentDescription(int departmentId, String departmentDescription) {
        dao.updeteDepartmentbyDescrition(departmentId, departmentDescription);
    }

    @Override
    public void updateDepartmentStatus(int departmentId, DepartmentStatus status) {
        dao.updeteDepartmentbyStatus(departmentId, status);
    }

    @Override
    public void deleteDepartment(int departmentId) {
        dao.deleteDepartment(departmentId);
    }

    @Override
    public List<Department> getDepartmentByName(String departmentName) {
        return dao.getDepartmentByName(departmentName);
    }
}
