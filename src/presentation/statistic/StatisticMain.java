package presentation.statistic;

import businness.service.statistiFcunction.StatistiFcunctionSevice;
import businness.service.statistiFcunction.StatistiFcunctionSeviceImp;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StatisticMain {
    private static final Scanner sc = new Scanner(System.in);
    private static final StatistiFcunctionSevice statisticService = new StatistiFcunctionSeviceImp();

    public static void run() {
        int choice ;
        do {
            System.out.println("\n=== THỐNG KÊ NHÂN VIÊN ===");
            System.out.println("1. Số lượng nhân viên theo phòng ban");
            System.out.println("2. Tổng số nhân viên");
            System.out.println("3. Phòng ban có nhiều nhân viên nhất");
            System.out.println("4. Phòng ban có lương trung bình cao nhất");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    List<Map<String, Object>> result = statisticService.getCountEmployeesByDepartment();
                    if (result.isEmpty()) {
                        System.out.println("Không có dữ liệu.");
                    } else {
                        result.forEach(row -> {
                            System.out.println("Phòng ban: " + row.get("DepartmentName")
                                    + " | Số nhân viên: " + row.get("EmployeeCount"));
                        });
                    }
                    break;
                case 2:
                    int total = statisticService.getTotalEmployees();
                    System.out.println("Tổng số nhân viên: " + total);
                    break;
                case 3:
                    Map<String, Object> mostEmp = statisticService.getDepartmentWithMostEmployees();
                    if (mostEmp.isEmpty()) {
                        System.out.println("Không có dữ liệu.");
                    } else {
                        System.out.println("Phòng ban nhiều nhân viên nhất: " + mostEmp.get("DepartmentName")
                                + " | Số lượng: " + mostEmp.get("EmployeeCount"));
                    }
                    break;
                case 4:
                    Map<String, Object> highestSalary = statisticService.getDepartmentWithHighestSalary();
                    if (highestSalary.isEmpty()) {
                        System.out.println("Không có dữ liệu.");
                    } else {
                        System.out.println("Phòng ban lương TB cao nhất: " + highestSalary.get("DepartmentName")
                                + " | TB lương: " + highestSalary.get("AverageSalary"));
                    }
                    break;
                case 0:
                    System.out.println("Thoát thống kê.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 0);
    }
}
