package businness.service.statistiFcunction;

import java.util.List;
import java.util.Map;

public interface StatistiFcunctionSevice {
    List<Map<String, Object>> getCountEmployeesByDepartment();

    int getTotalEmployees();

    Map<String, Object> getDepartmentWithMostEmployees();

    Map<String, Object> getDepartmentWithHighestSalary();
}
