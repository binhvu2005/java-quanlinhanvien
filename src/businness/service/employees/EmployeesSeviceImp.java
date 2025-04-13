package businness.service.employees;

import businness.dao.employee.EmployeeDao;
import businness.dao.employee.EmployeeDaoImp;
import businness.model.employees.Employees;
import businness.model.employees.EmployeesSex;
import businness.model.employees.EmployeesStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class EmployeesSeviceImp implements EmployeesSevice {

    private final EmployeeDao employeeDao = new EmployeeDaoImp();

    @Override
    public int getPageEmployees() {
        return employeeDao.getPageEmployees();
    }

    @Override
    public List<Employees> getEmployeesByPage(int page) {
        return employeeDao.getEmployeesByPage(page);
    }

    @Override
    public void addEmployee(Employees employee) {
        employeeDao.addEmployee(employee);
    }

    @Override
    public Employees getEmployeeById(String employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }

    @Override
    public void updateEmployeeName(String employeeId, String name) {
        employeeDao.updateEmployeeName(employeeId, name);
    }

    @Override
    public void updateEmployeeEmail(String employeeId, String email) {
        employeeDao.updateEmployeeEmail(employeeId, email);
    }

    @Override
    public void updateEmployeePhoneNumber(String employeeId, String phoneNumber) {
        employeeDao.updateEmployeePhoneNumber(employeeId, phoneNumber);
    }

    @Override
    public void updateEmployeeSex(String employeeId, EmployeesSex sex) {
        employeeDao.updateEmployeeSex(employeeId, sex);
    }

    @Override
    public void updateEmployeeSalaryGrade(String employeeId, int salaryGrade) {
        employeeDao.updateEmployeeSalaryGrade(employeeId, salaryGrade);
    }

    @Override
    public void updateEmployeeSalary(String employeeId, Double salary) {
        employeeDao.updateEmployeeSalary(employeeId, BigDecimal.valueOf(salary));
    }

    @Override
    public void updateEmployeeBirthDate(String employeeId, Date birthDate) {
        employeeDao.updateEmployeeBirthDate(employeeId, birthDate);
    }

    @Override
    public void updateEmployeeAddress(String employeeId, String address) {
        employeeDao.updateEmployeeAddress(employeeId, address);
    }

    @Override
    public void updateEmployeeStatus(String employeeId, EmployeesStatus status) {
        employeeDao.updateEmployeeStatus(employeeId, status);
    }

    @Override
    public void updateEmployeeDepartmentId(String employeeId, int departmentId) {
        employeeDao.updateEmployeeDepartmentId(employeeId, departmentId);
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employeeDao.deleteEmployee(employeeId);
    }

    @Override
    public List<Employees> searchEmployeeByName(String name) {
        return employeeDao.searchEmployeeByName(name);
    }

    @Override
    public List<Employees> searchEmployeeByAge(int age) {
        return employeeDao.searchEmployeeByAge(age);
    }

    @Override
    public List<Employees> sortEmployeesBySalaryDesc() {
        return employeeDao.sortEmployeesBySalaryDesc();
    }

    @Override
    public List<Employees> sortEmployeesByNameAsc() {
        return employeeDao.sortEmployeesByNameAsc();
    }
}
