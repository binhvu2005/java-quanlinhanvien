package businness.dao.employee;

import businness.config.ConnectionDB;
import businness.model.employees.Employees;
import businness.model.employees.EmployeesSex;
import businness.model.employees.EmployeesStatus;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImp implements EmployeeDao {

    @Override
    public int getPageEmployees() {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL getPageEmployees()}");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPage;
    }

    @Override
    public List<Employees> getEmployeesByPage(int page) {
        List<Employees> list = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL getEmployeesByPage(?)}")) {
            stmt.setInt(1, page);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void addEmployee(Employees employee) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL addEmployee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            stmt.setString(1, employee.getEmployeeId());
            stmt.setString(2, employee.getEmployeeName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhoneNumber());
            stmt.setString(5, employee.getSex() != null ? employee.getSex().name() : null);

            stmt.setInt(6, employee.getSalaryGrade());
            stmt.setDouble(7, employee.getSalary());
            stmt.setDate(8, employee.getBirthDate() != null ? Date.valueOf(employee.getBirthDate()) : null);

            stmt.setString(9, employee.getAddress());
            stmt.setInt(10, employee.getDepartmentId());
            stmt.executeUpdate();
            System.out.println(" Thêm nhân viên thành công!");
        } catch (SQLException e) {
            System.out.println("lỗi khi thêm nhân viên: " + e.getMessage());
        }
    }

    @Override
    public Employees getEmployeeById(String employeeId) {
        Employees employee = null;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL getEmployeeById(?)}")) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                employee = mapEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void updateEmployeeName(String employeeId, String name) {
        executeSimpleUpdate("{CALL updateEmployeeName(?, ?)}", employeeId, name);
    }

    @Override
    public void updateEmployeeEmail(String employeeId, String email) {
        executeSimpleUpdate("{CALL updateEmployeeEmail(?, ?)}", employeeId, email);
    }

    @Override
    public void updateEmployeePhoneNumber(String employeeId, String phoneNumber) {
        executeSimpleUpdate("{CALL updateEmployeePhoneNumber(?, ?)}", employeeId, phoneNumber);
    }

    @Override
    public void updateEmployeeSex(String employeeId, EmployeesSex sex) {
        executeSimpleUpdate("{CALL updateEmployeeSex(?, ?)}", employeeId, sex.name());
    }

    @Override
    public void updateEmployeeSalaryGrade(String employeeId, int salaryGrade) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL updateEmployeeSalaryGrade(?, ?)}")) {
            stmt.setString(1, employeeId);
            stmt.setInt(2, salaryGrade);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployeeSalary(String employeeId, BigDecimal salary) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL updateEmployeeSalary(?, ?)}")) {
            stmt.setString(1, employeeId);
            stmt.setBigDecimal(2, salary);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployeeBirthDate(String employeeId, Date birthDate) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL updateEmployeeBirthDate(?, ?)}")) {
            stmt.setString(1, employeeId);
            stmt.setDate(2, birthDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployeeAddress(String employeeId, String address) {
        executeSimpleUpdate("{CALL updateEmployeeAddress(?, ?)}", employeeId, address);
    }

    @Override
    public void updateEmployeeStatus(String employeeId, EmployeesStatus status) {
        executeSimpleUpdate("{CALL updateEmployeeStatus(?, ?)}", employeeId, status.name());
    }

    @Override
    public void updateEmployeeDepartmentId(String employeeId, int departmentId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL updateEmployeeDepartmentId(?, ?)}")) {
            stmt.setString(1, employeeId);
            stmt.setInt(2, departmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(String employeeId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL deleteEmployee(?)}")) {
            stmt.setString(1, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("lôỗi khi xóa nhân viên: " + e.getMessage());
        }
    }

    @Override
    public List<Employees> searchEmployeeByName(String name) {
        List<Employees> list = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL searchEmployeeByName(?)}")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Employees> searchEmployeeByAge(int age) {
        List<Employees> list = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL searchEmployeeByAge(?)}")) {
            stmt.setInt(1, age);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Employees> sortEmployeesBySalaryDesc() {
        return sortEmployees("{CALL sortEmployeesBySalaryDesc()}");
    }

    @Override
    public List<Employees> sortEmployeesByNameAsc() {
        return sortEmployees("{CALL sortEmployeesByNameAsc()}");
    }

    private Employees mapEmployee(ResultSet rs) throws SQLException {
        Employees e = new Employees();
        e.setEmployeeId(rs.getString("EmployeesId"));
        e.setEmployeeName(rs.getString("EmployeesName"));
        e.setEmail(rs.getString("Email"));
        e.setPhoneNumber(rs.getString("phoneNumber"));
        e.setSex(EmployeesSex.valueOf(rs.getString("sex")));
        e.setSalaryGrade(rs.getInt("salaryGrade"));
        e.setSalary(rs.getDouble("salary"));
        e.setBirthDate(rs.getDate("birthDate").toLocalDate());
        e.setAddress(rs.getString("address"));
        e.setStatus(EmployeesStatus.valueOf(rs.getString("status")));
        e.setDepartmentId(rs.getInt("DepartmentId"));
        return e;
    }

    private void executeSimpleUpdate(String query, String param1, String param2) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall(query)) {
            stmt.setString(1, param1);
            stmt.setString(2, param2);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Employees> sortEmployees(String procedureCall) {
        List<Employees> list = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall(procedureCall);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}