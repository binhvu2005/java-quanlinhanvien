package businness.service.statistiFcunction;

import businness.dao.statistiFcunction.StatistiFcunctionDao;
import businness.dao.statistiFcunction.StatistiFcunctionDaoImp;

import java.util.List;
import java.util.Map;

public class StatistiFcunctionSeviceImp implements StatistiFcunctionSevice {

    private final StatistiFcunctionDao statistiFcunctionDao;

    public StatistiFcunctionSeviceImp() {
        this.statistiFcunctionDao = new StatistiFcunctionDaoImp();
    }

    public List<Map<String, Object>> getCountEmployeesByDepartment() {
        return statistiFcunctionDao.countEmployeesByDepartment();
    }

    public int getTotalEmployees() {
        return statistiFcunctionDao.countTotalEmployees();
    }

    public Map<String, Object> getDepartmentWithMostEmployees() {
        return statistiFcunctionDao.departmentWithMostEmployees();
    }

    public Map<String, Object> getDepartmentWithHighestSalary() {
        return statistiFcunctionDao.departmentWithHighestSalary();
    }
}
