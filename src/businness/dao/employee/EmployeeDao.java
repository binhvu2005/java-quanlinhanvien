package businness.dao.employee;

import businness.dao.AppDao;
import businness.model.employees.Employees;
import businness.model.employees.EmployeesSex;
import businness.model.employees.EmployeesStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface EmployeeDao extends AppDao {

    // Phân trang
    int getPageEmployees();
    List<Employees> getEmployeesByPage(int page);

    // Thêm mới
    void addEmployee(Employees employee);

    // Lấy nhân viên theo ID
    Employees getEmployeeById(String employeeId);

    // Cập nhật thông tin
    void updateEmployeeName(String employeeId, String name);
    void updateEmployeeEmail(String employeeId, String email);
    void updateEmployeePhoneNumber(String employeeId, String phoneNumber);
    void updateEmployeeSex(String employeeId, EmployeesSex sex);
    void updateEmployeeSalaryGrade(String employeeId, int salaryGrade);
    void updateEmployeeSalary(String employeeId, BigDecimal salary);
    void updateEmployeeBirthDate(String employeeId, Date birthDate);
    void updateEmployeeAddress(String employeeId, String address);
    void updateEmployeeStatus(String employeeId, EmployeesStatus status);
    void updateEmployeeDepartmentId(String employeeId, int departmentId);

    // Xóa (chuyển trạng thái về INACTIVE)
    void deleteEmployee(String employeeId);

    // Tìm kiếm
    List<Employees> searchEmployeeByName(String name);
    List<Employees> searchEmployeeByAge(int age);

    // Sắp xếp
    List<Employees> sortEmployeesBySalaryDesc();
    List<Employees> sortEmployeesByNameAsc();

}
