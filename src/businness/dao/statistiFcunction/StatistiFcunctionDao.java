package businness.dao.statistiFcunction;

import businness.dao.AppDao;

import java.util.List;
import java.util.Map;

public interface StatistiFcunctionDao extends AppDao {

    // Số lượng nhân viên theo từng phòng ban
    List<Map<String, Object>> countEmployeesByDepartment();

    // Tổng số nhân viên toàn hệ thống
    int countTotalEmployees();

    // Phòng ban có nhiều nhân viên nhất
    Map<String, Object> departmentWithMostEmployees();

    // Phòng ban có lương trung bình cao nhất
    Map<String, Object> departmentWithHighestSalary();
}
