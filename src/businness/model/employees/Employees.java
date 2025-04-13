package businness.model.employees;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class Employees {
private String employeeId;
private String employeeName;
private String email;
private String phoneNumber;
private EmployeesSex sex;
private int salaryGrade;
private double salary;
private LocalDate birthDate;
private String address;
private EmployeesStatus Status;
private int DepartmentId;

public Employees (){}


    public Employees(String employeeId, String employeeName, String email, String phoneNumber, EmployeesSex sex, int salaryGrade, double salary, LocalDate birthDate, String address, EmployeesStatus status, int departmentId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.salaryGrade = salaryGrade;
        this.salary = salary;
        this.birthDate = birthDate;
        this.address = address;
        Status = status;
        DepartmentId = departmentId;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeID) {
        this.employeeId = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeesSex getSex() {
        return sex;
    }

    public void setSex(EmployeesSex sex) {
        this.sex = sex;
    }

    public int getSalaryGrade() {
        return salaryGrade;
    }

    public void setSalaryGrade(int salaryGrade) {
        this.salaryGrade = salaryGrade;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EmployeesStatus getStatus() {
        return Status;
    }

    public void setStatus(EmployeesStatus status) {
        Status = status;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "employeeID='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sex=" + sex +
                ", salaryGrade=" + salaryGrade +
                ", salary=" + salary +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", Status=" + Status +
                ", DepartmentId='" + DepartmentId + '\'' +
                '}';
    }
}
