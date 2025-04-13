package businness.dao.statistiFcunction;

import businness.config.ConnectionDB;

import java.sql.*;
import java.util.*;

public class StatistiFcunctionDaoImp implements StatistiFcunctionDao {

    @Override
    public List<Map<String, Object>> countEmployeesByDepartment() {
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL countEmployeesByDepartment()}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("DepartmentName", rs.getString("DepartmentName"));
                row.put("EmployeeCount", rs.getInt("EmployeeCount"));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int countTotalEmployees() {
        int total = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL countTotalEmployees()}")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt("TotalEmployees");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public Map<String, Object> departmentWithMostEmployees() {
        Map<String, Object> result = new HashMap<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL departmentWithMostEmployees()}")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result.put("DepartmentName", rs.getString("DepartmentName"));
                result.put("EmployeeCount", rs.getInt("EmployeeCount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> departmentWithHighestSalary() {
        Map<String, Object> result = new HashMap<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL departmentWithHighestSalary()}")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result.put("DepartmentName", rs.getString("DepartmentName"));
                result.put("AverageSalary", rs.getBigDecimal("AverageSalary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
