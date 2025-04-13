package validate;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidEmployeeId(String id) {
        return id != null && id.matches("^E\\d{4}$");
    }

    public static boolean isValidEmployeeName(String name) {
        return name != null && name.length() >= 15 && name.length() <= 150;
    }


    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && Pattern.matches(emailRegex, email);
    }


    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^(0[3|5|7|8|9])[0-9]{8}$";
        return phone != null && Pattern.matches(phoneRegex, phone);
    }

    public static boolean isValidSalaryGrade(int grade) {
        return grade > 0;
    }


    public static boolean isValidSalary(double salary) {
        return salary > 0;
    }


    public static boolean isValidBirthDate(LocalDate birthDate) {
        return birthDate != null && birthDate.isBefore(LocalDate.now());
    }

    public static boolean isValidAddress(String address) {
        return address != null && address.length() > 0 && address.length() <= 100;
    }


    public static boolean isValidDepartmentName(String name) {
        return name != null && name.length() >= 10 && name.length() <= 100;
    }
}
