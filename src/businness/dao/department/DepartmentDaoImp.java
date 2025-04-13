package businness.dao.department;

import businness.config.ConnectionDB;
import businness.model.department.Department;
import businness.model.department.DepartmentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImp implements departmentDao {

    @Override
    public int getPageDepartment() {
        int page = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL getPageDepartment()}")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                page = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public List<Department> getDepartmentByPage(int page) {
        List<Department> departments = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL getDepartmentByPage(?)}")) {
            stmt.setInt(1, page);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));
                d.setDepartmentName(rs.getString("DepartmentName"));
                d.setDescrition(rs.getString("descrition"));
                d.setStatus(DepartmentStatus.valueOf(rs.getString("status")));
                departments.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public void addDepartment(String departmentName, String departmentDescription) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL addDepartment(?, ?)}")) {
            stmt.setString(1, departmentName);
            stmt.setString(2, departmentDescription);
            stmt.execute();
            System.out.println(" Đã thêm phòng ban thành công.");
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm phòng ban: " + e.getMessage());
        }
    }


    @Override
    public Department getDepartmentById(int id) {
        Department department = null;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL getDepartmentById(?)}")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                department = new Department();
                department.setId(rs.getInt("id"));
                department.setDepartmentName(rs.getString("DepartmentName"));
                department.setDescrition(rs.getString("descrition"));
                department.setStatus(DepartmentStatus.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public void updeteDepartmentbyName(int departmentId, String departmentName) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL updeteDepartmentbyName(?, ?)}")) {
            stmt.setInt(1, departmentId);
            stmt.setString(2, departmentName);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updeteDepartmentbyDescrition(int departmentId, String departmentDescription) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL updeteDepartmentbyDescrition(?, ?)}")) {
            stmt.setInt(1, departmentId);
            stmt.setString(2, departmentDescription);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updeteDepartmentbyStatus(int departmentId, int status) {

    }

    @Override
    public void updeteDepartmentbyStatus(int departmentId, DepartmentStatus status) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL updeteDepartmentbyStatus(?, ?)}")) {
            stmt.setInt(1, departmentId);
            stmt.setString(2, status.name());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDepartment(int departmentId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL deleteDepartment(?)}")) {
            stmt.setInt(1, departmentId);
            stmt.execute();
            System.out.println("Phòng ban đã được xóa.");
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa phòng ban: " + e.getMessage());
        }
    }

    @Override
    public List<Department> getDepartmentByName(String departmentName) {
        List<Department> departments = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL searchDepartmentByName(?)}")) {
            stmt.setString(1, departmentName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));
                d.setDepartmentName(rs.getString("DepartmentName"));
                d.setDescrition(rs.getString("descrition"));
                d.setStatus(DepartmentStatus.valueOf(rs.getString("status")));
                departments.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
}
