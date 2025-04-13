package presentation.department;


import businness.model.department.Department;
import businness.model.department.DepartmentStatus;
import businness.service.department.DepartmentSeviceImp;
import validate.Validator;

import java.util.List;
import java.util.Scanner;

public class DepartmentMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DepartmentSeviceImp DepartmentSevice = new DepartmentSeviceImp();

    public static void run() {
        int choice;

        do {
            System.out.println("\n=== QUẢN LÝ PHÒNG BAN ===");
            System.out.println("1. Hiển thị danh sách phòng ban (phân trang)");
            System.out.println("2. Thêm phòng ban");
            System.out.println("3. Cập nhật phòng ban");
            System.out.println("4. Xóa phòng ban (chỉ xóa nếu chưa có nhân viên)");
            System.out.println("5. Tìm kiếm phòng ban theo tên");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Lựa chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    displayDepartmentsByPage();
                    break;
                case 2:
                    addDepartment();
                    break;
                case 3:
                    updateDepartment();
                    break;
                case 4:
                    deleteDepartment();
                    break;
                case 5:
                    searchDepartmentByName();
                    break;
                case 0:
                    System.out.println("Quay lại menu chính.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void displayDepartmentsByPage() {
        int totalPages = DepartmentSevice.getPageDepartment();
        if (totalPages == 0) {
            System.out.println("Chưa có phòng ban nào.");
            return;
        }

        System.out.println("Có tổng cộng " + totalPages + " trang.");
        System.out.print("Nhập số trang muốn xem (1-" + totalPages + "): ");
        int page = scanner.nextInt();
        scanner.nextLine();

        if (page < 1 || page > totalPages) {
            System.out.println("Trang không hợp lệ.");
            return;
        }

        List<Department> departments = DepartmentSevice.getDepartmentByPage(page);
        if (departments.isEmpty()) {
            System.out.println("Không có phòng ban nào trong trang này.");
        } else {
            System.out.println("=== DANH SÁCH PHÒNG BAN (Trang " + page + ") ===");
            for (Department d : departments) {
                System.out.println(d);
            }
        }
    }

    private static void addDepartment() {
        String name;
        do {
            System.out.print("Nhập tên phòng ban: ");
            name = scanner.nextLine();

            if (!Validator.isValidDepartmentName(name)) {
                System.out.println(" Tên phòng ban phải từ 10 đến 100 ký tự. Vui lòng nhập lại.");
            }
        } while (!Validator.isValidDepartmentName(name));

        System.out.print("Nhập mô tả phòng ban: ");
        String description = scanner.nextLine();

        DepartmentSevice.addDepartment(name, description);

    }


    private static void updateDepartment() {
        System.out.print("Nhập ID phòng ban cần cập nhật: ");
        int id = scanner.nextInt();
        Department d = DepartmentSevice.getDepartmentById(id);
        if (d == null) {
            System.out.println("Không tìm thấy phòng ban.");
            return;
        }

        int choice;
        do {
            System.out.println("\n--- CẬP NHẬT PHÒNG BAN ---");
            System.out.println("1. Cập nhật tên");
            System.out.println("2. Cập nhật mô tả");
            System.out.println("3. Cập nhật trạng thái");
            System.out.println("0. Quay lại");
            System.out.print("Lựa chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Tên mới: ");
                    String newName = scanner.nextLine();
                    if (Validator.isValidDepartmentName(newName)) {
                        DepartmentSevice.updateDepartmentName(id, newName);
                        System.out.println("Đã cập nhật tên.");
                    } else {
                        System.out.println("Tên không hợp lệ (10-100 ký tự).");
                    }
                    break;
                case 2:
                    System.out.print("Mô tả mới: ");
                    String newDesc = scanner.nextLine();
                    DepartmentSevice.updateDepartmentDescription(id, newDesc);
                    System.out.println("Đã cập nhật mô tả.");
                    break;
                case 3:
                    System.out.print("Trạng thái mới (ACTIVE/INACTIVE): ");
                    String statusStr = scanner.nextLine().toUpperCase();
                    try {
                        DepartmentStatus status = DepartmentStatus.valueOf(statusStr);
                        DepartmentSevice.updateDepartmentStatus(id, status);
                        System.out.println("Đã cập nhật trạng thái.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Trạng thái không hợp lệ.");
                    }
                    break;
                case 0:
                    System.out.println("Quay lại.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void deleteDepartment() {
        System.out.print("Nhập ID phòng ban cần xóa: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        DepartmentSevice.deleteDepartment(id);

    }

    private static void searchDepartmentByName() {
        System.out.print("Nhập tên phòng ban cần tìm: ");
        String name = scanner.nextLine();

        List<Department> list = DepartmentSevice.getDepartmentByName(name);
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy phòng ban nào.");
        } else {
            list.forEach(System.out::println);
        }
    }
}
