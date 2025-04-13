package presentation;

import presentation.department.DepartmentMain;
import presentation.employee.EmployeeMain;
import presentation.statistic.StatisticMain;
import businness.dao.pesion.PesionDao;
import businness.dao.pesion.PesionDaoImp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PesionDao pesionDao = new PesionDaoImp();

        boolean isLoggedIn = false;

        // Đăng nhập
        while (!isLoggedIn) {
            System.out.println("=== ĐĂNG NHẬP HỆ THỐNG ===");
            System.out.print("Tài khoản: ");
            String username = scanner.nextLine();
            System.out.print("Mật khẩu: ");
            String password = scanner.nextLine();

            int loginResult = pesionDao.login(username, password);
            if (loginResult == 1) {
                System.out.println("✅ Đăng nhập thành công!\n");
                isLoggedIn = true;
            } else {
                System.out.println("❌ Sai tài khoản hoặc mật khẩu, vui lòng thử lại.\n");
            }
        }

        // Menu chính
        int choice;
        do {
            System.out.println("=== HỆ THỐNG QUẢN LÝ NHÂN SỰ ===");
            System.out.println("1. Quản lý phòng ban");
            System.out.println("2. Quản lý nhân viên");
            System.out.println("3. Thống kê");
            System.out.println("0. Đăng xuất và thoát");
            System.out.print("Lựa chọn của bạn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    DepartmentMain.run();
                    break;
                case 2:
                    EmployeeMain.run();
                    break;
                case 3:
                    StatisticMain.run();
                    break;
                case 0:
                    pesionDao.logout();
                    System.out.println("👋 Đã đăng xuất. Hẹn gặp lại!");
                    break;
                default:
                    System.out.println("❗ Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }
}
