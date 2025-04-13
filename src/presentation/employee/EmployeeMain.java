package presentation.employee;

import businness.dao.employee.EmployeeDao;
import businness.dao.employee.EmployeeDaoImp;
import businness.model.employees.Employees;
import businness.model.employees.EmployeesSex;
import businness.model.employees.EmployeesStatus;
import validate.Validator;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeMain {
    private static final Scanner sc = new Scanner(System.in);
    private static final EmployeeDao employeeDao = new EmployeeDaoImp();

    public static void run() {
        while (true) {
            System.out.println("\n========== MENU QUẢN LÝ NHÂN VIÊN ==========");
            System.out.println("1. Hiển thị danh sách nhân viên theo trang");
            System.out.println("2. Thêm nhân viên mới");
            System.out.println("3. Cập nhật thông tin nhân viên");
            System.out.println("4. Xóa nhân viên (chuyển trạng thái INACTIVE)");
            System.out.println("5. Tìm kiếm nhân viên theo tên");
            System.out.println("6. Tìm kiếm nhân viên theo tuổi");
            System.out.println("7. Sắp xếp theo lương giảm dần");
            System.out.println("8. Sắp xếp theo tên tăng dần");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> showEmployeesByPage();
                case 2 -> addEmployee();
                case 3 -> updateEmployee();
                case 4 -> deleteEmployee();
                case 5 -> searchByName();
                case 6 -> searchByAge();
                case 7 -> sortBySalaryDesc();
                case 8 -> sortByNameAsc();
                case 0 -> {
                    System.out.println("Thoát khỏi quản lý nhân viên.");
                    return;
                }
                default -> System.out.println("Chức năng không hợp lệ!");
            }
        }
    }

    private static void showEmployeesByPage() {
        int totalPage = employeeDao.getPageEmployees();
        System.out.println("Có " + totalPage + " trang nhân viên.");

        System.out.print("Nhập số trang: ");
        int page = Integer.parseInt(sc.nextLine());
        if (page < 1 || page > totalPage) {
            System.out.println("Trang không hợp lệ!");
            return;
        }

        List<Employees> list = employeeDao.getEmployeesByPage(page);
        list.forEach(System.out::println);
    }

    private static void addEmployee() {
        String id, name, email, phone, address;
        EmployeesSex sex = null;
        int grade = 0, departmentId = 0;
        double salary = 0;
        LocalDate birthDate = null;

        // Mã nhân viên
        while (true) {
            System.out.print("Mã nhân viên (ví dụ: E0001): ");
            id = sc.nextLine();
            if (Validator.isValidEmployeeId(id)) break;
            System.out.println(" ID không hợp lệ! (Ví dụ: E0001)");
        }

        // Tên nhân viên
        while (true) {
            System.out.print("Tên nhân viên: ");
            name = sc.nextLine();
            if (Validator.isValidEmployeeName(name)) break;
            System.out.println(" Tên không hợp lệ! (15-100 ký tự)");
        }

        // Email
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine();
            if (Validator.isValidEmail(email)) break;
            System.out.println(" Email không hợp lệ!");
        }

        // Số điện thoại
        while (true) {
            System.out.print("Số điện thoại: ");
            phone = sc.nextLine();
            if (Validator.isValidPhoneNumber(phone)) break;
            System.out.println(" Số điện thoại không hợp lệ!");
        }

        // Giới tính
        while (true) {
            try {
                System.out.print("Giới tính (MALE/FEMALE/OTHER): ");
                sex = EmployeesSex.valueOf(sc.nextLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(" Giới tính không hợp lệ! Chỉ nhận MALE, FEMALE hoặc OTHER.");
            }
        }

        // Bậc lương
        while (true) {
            try {
                System.out.print("Bậc lương: ");
                grade = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(" Bậc lương phải là số nguyên!");
            }
        }

        // Lương
        while (true) {
            try {
                System.out.print("Lương: ");
                salary = Double.parseDouble(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(" Lương phải là số!");
            }
        }

        // Ngày sinh
        while (true) {
            try {
                System.out.print("Ngày sinh (yyyy-mm-dd): ");
                String input = sc.nextLine();
                if (input.isEmpty()) {
                    System.out.println("Ngày sinh không được để trống!");
                    continue;
                }
                birthDate = LocalDate.parse(input);
                break;
            } catch (Exception e) {
                System.out.println("Ngày sinh không đúng định dạng. Vui lòng nhập lại.");
            }
        }

        // Địa chỉ
        System.out.print("Địa chỉ: ");
        address = sc.nextLine();

        // ID phòng ban
        while (true) {
            try {
                System.out.print("ID phòng ban: ");
                departmentId = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("ID phòng ban phải là số nguyên!");
            }
        }

        // Tạo đối tượng và thêm
        Employees emp = new Employees(
                        id, name, email, phone, sex, grade, salary,
                Date.valueOf(birthDate).toLocalDate(), address, EmployeesStatus.ACTIVE, departmentId
                );

        employeeDao.addEmployee(emp);

    }


    private static void updateEmployee() {
        System.out.print("Nhập ID nhân viên cần cập nhật: ");
        String id = sc.nextLine();
        Employees e = employeeDao.getEmployeeById(id);
        if (e == null) {
            System.out.println("Không tìm thấy nhân viên!");
            return;
        }

        System.out.println("1. Tên | 2. Email | 3. SĐT | 4. Giới tính | 5. Bậc lương | 6. Lương | 7. Ngày sinh | 8. Địa chỉ | 9. Trạng thái | 10. Phòng ban");
        System.out.print("Chọn thuộc tính cần sửa: ");
        int opt = Integer.parseInt(sc.nextLine());

        try {
            switch (opt) {
                case 1 -> {
                    System.out.print("Tên mới: ");
                    String name = sc.nextLine();
                    employeeDao.updateEmployeeName(id, name);
                }
                case 2 -> {
                    System.out.print("Email mới: ");
                    String email = sc.nextLine();
                    employeeDao.updateEmployeeEmail(id, email);
                }
                case 3 -> {
                    System.out.print("SĐT mới: ");
                    String phone = sc.nextLine();
                    employeeDao.updateEmployeePhoneNumber(id, phone);
                }
                case 4 -> {
                    System.out.print("Giới tính mới: ");
                    EmployeesSex sex = EmployeesSex.valueOf(sc.nextLine().toUpperCase());
                    employeeDao.updateEmployeeSex(id, sex);
                }
                case 5 -> {
                    System.out.print("Bậc lương mới: ");
                    int grade = Integer.parseInt(sc.nextLine());
                    employeeDao.updateEmployeeSalaryGrade(id, grade);
                }
                case 6 -> {
                    System.out.print("Lương mới: ");
                    BigDecimal salary = new BigDecimal(sc.nextLine());
                    employeeDao.updateEmployeeSalary(id, salary);
                }
                case 7 -> {
                    System.out.print("Ngày sinh mới (yyyy-mm-dd): ");
                    Date birth = Date.valueOf(sc.nextLine());
                    employeeDao.updateEmployeeBirthDate(id, birth);
                }
                case 8 -> {
                    System.out.print("Địa chỉ mới: ");
                    String address = sc.nextLine();
                    employeeDao.updateEmployeeAddress(id, address);
                }
                case 9 -> {
                    System.out.print("Trạng thái (ACTIVE/INACTIVE/ONLEAVE/POLICYLEAVE): ");
                    EmployeesStatus status = EmployeesStatus.valueOf(sc.nextLine().toUpperCase());
                    employeeDao.updateEmployeeStatus(id, status);
                }
                case 10 -> {
                    System.out.print("ID phòng ban mới: ");
                    int deptId = Integer.parseInt(sc.nextLine());
                    employeeDao.updateEmployeeDepartmentId(id, deptId);
                }
                default -> System.out.println("Không hợp lệ!");
            }
            System.out.println("Cập nhật thành công!");
        } catch (Exception ex) {
            System.out.println("Lỗi: " + ex.getMessage());
        }
    }

    private static void deleteEmployee() {
        System.out.print("Nhập ID nhân viên cần xóa: ");
        String id = sc.nextLine();
        try {
            employeeDao.deleteEmployee(id);
            System.out.println("Xóa thành công (đã chuyển trạng thái INACTIVE)");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private static void searchByName() {
        System.out.print("Nhập tên cần tìm: ");
        String name = sc.nextLine();
        List<Employees> list = employeeDao.searchEmployeeByName(name);
        list.forEach(System.out::println);
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy nhân viên nào!");
        }
    }

    private static void searchByAge() {
        System.out.print("Nhập tuổi cần tìm: ");
        int age = Integer.parseInt(sc.nextLine());
        List<Employees> list = employeeDao.searchEmployeeByAge(age);
        list.forEach(System.out::println);
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy nhân viên nào!");
        }
    }

    private static void sortBySalaryDesc() {
        List<Employees> list = employeeDao.sortEmployeesBySalaryDesc();
        list.forEach(System.out::println);
        if (list.isEmpty()) {
            System.out.println("Không có nhân viên nào!");
        }
    }

    private static void sortByNameAsc() {
        List<Employees> list = employeeDao.sortEmployeesByNameAsc();
        list.forEach(System.out::println);
        if (list.isEmpty()) {
            System.out.println("Không có nhân viên nào!");
        }
    }
}
