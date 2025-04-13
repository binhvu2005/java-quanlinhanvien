drop database validator;
create database validator;
use  validator;

create table Pesion (
    id int auto_increment primary key,
    Username varchar(50) not null,
    Password varchar(20) not null,
    status enum ('ACTIVE', 'INACTIVE') not null default ('ACTIVE')
);
create table Department (
    id int auto_increment primary key,
    DepartmentName varchar(100) not null unique check ( CHAR_LENGTH(DepartmentName) BETWEEN 10 AND 100),
    descrition varchar(255) ,
    status enum ('ACTIVE', 'INACTIVE') not null
);
create table Employees (
    EmployeesId varchar(5) primary key check ( EmployeesId REGEXP '^E[0-9]{4}$'),
    EmployeesName varchar(150) not null check ( CHAR_LENGTH(EmployeesName) BETWEEN 15 AND 150),
    Email varchar(50) not null unique  CHECK (email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$'),
    phoneNumber varchar(10) not null unique CHECK (phoneNumber REGEXP '^(0[3|5|7|8|9])[0-9]{8}$') ,
    sex enum('MALE', 'FEMALE', 'OTHER') not null,
    salaryGrade int not null check ( salaryGrade>0 ),
    salary decimal(10,2) not null check ( salary>0 ),
    birthDate date not null,
    address varchar(100) not null,
    status enum ('ACTIVE', 'INACTIVE', 'ONLEAVE', 'POLICYLEAVE') not null default('ACTIVE'),
    DepartmentId int not null,
    FOREIGN KEY (DepartmentId) REFERENCES Department(id)
);

insert into Pesion (Username, Password) values ('admin', 'admin');
insert into Department (DepartmentName, descrition, status) values ('Phòng Kế Toán', 'Phòng kế toán của công ty', 'ACTIVE');
insert into Employees (EmployeesId, EmployeesName, Email, phoneNumber, sex, salaryGrade, salary, birthDate, address, status, DepartmentId) VALUES ('E0001','VuThanhBinhhahaha','vubinh2005mt@gmail.com','0847069548','MALE',3, 10000000,'2005-10-10','Ha Noi','ACTIVE',1);

# quản lí tài khoản đang nhập
DELIMITER //

CREATE PROCEDURE login(
    IN p_Username VARCHAR(50),
    IN p_Password VARCHAR(20),
    OUT loginResult INT
)
BEGIN
    DECLARE userStatus ENUM('ACTIVE', 'INACTIVE');

    SELECT status INTO userStatus
    FROM Pesion
    WHERE Username = p_Username AND Password = p_Password
    LIMIT 1;

    IF userStatus IS NOT NULL THEN
        IF userStatus = 'ACTIVE' THEN
            SET loginResult = 1; -- Thành công
        ELSE
            SET loginResult = 2; -- Tài khoản bị khóa
        END IF;
    ELSE
        SET loginResult = 0; -- Sai username/password
    END IF;
END //

DELIMITER ;
# quaản lí các thông tin của phòng ban
DELIMITER //

-- Lấy tổng số trang phòng ban (tức là số dòng)
CREATE PROCEDURE getPageDepartment()
BEGIN
    SELECT CEIL(COUNT(Department.id) / 5.0) AS totalPage
    FROM Department;
END //

-- Lấy danh sách phòng ban theo từng trang
CREATE PROCEDURE getDepartmentByPage(
    IN p_page INT
)
BEGIN
    DECLARE pageSize INT DEFAULT 5;
    DECLARE offset INT;

    SET offset = (p_page - 1) * pageSize;

    SELECT *
    FROM Department
    LIMIT pageSize OFFSET offset;
END //

DELIMITER ;
# thêm phòng ban mới
DELIMITER //
CREATE PROCEDURE addDepartment(
    IN p_DepartmentName VARCHAR(100),
    IN p_descrition VARCHAR(255)
)
BEGIN
    DECLARE duplicateCount INT;

    -- Kiểm tra xem tên phòng ban đã tồn tại chưa
    SELECT COUNT(*) INTO duplicateCount
    FROM Department
    WHERE DepartmentName = p_DepartmentName;
    IF duplicateCount > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Tên phòng ban đã tồn tại';
    ELSE
        INSERT INTO Department (DepartmentName, descrition)
        VALUES (p_DepartmentName, p_descrition);
    END IF;
END //
DELIMITER ;
# sửa thông tin phòng ban
DELIMITER //
CREATE PROCEDURE getDepartmentById(
    IN p_id INT
)
BEGIN
    SELECT *
    FROM Department
    WHERE id = p_id;
END //
CREATE PROCEDURE updeteDepartmentbyName(
    IN p_id INT,
    IN p_DepartmentName VARCHAR(100)
)
BEGIN
    DECLARE duplicateCount INT;

    -- Kiểm tra xem tên phòng ban đã tồn tại chưa
    SELECT COUNT(*) INTO duplicateCount
    FROM Department
    WHERE DepartmentName = p_DepartmentName;
    IF duplicateCount > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Tên phòng ban đã tồn tại';
    ELSE
        UPDATE Department
        SET DepartmentName = p_DepartmentName
        WHERE id = p_id;
    END IF;
END //
CREATE PROCEDURE updeteDepartmentbyDescrition(
    IN p_id INT,
    IN p_descrition VARCHAR(255)
)
BEGIN
    UPDATE Department
    SET descrition = p_descrition
    WHERE id = p_id;
END //
CREATE PROCEDURE updeteDepartmentbyStatus(
    IN p_id INT,
    IN p_status ENUM('ACTIVE', 'INACTIVE')
)
BEGIN
    UPDATE Department
    SET status = p_status
    WHERE id = p_id;
END //
DELIMITER ;
# xóa phòng ban  (chỉ xóa được phòng ban chưa có nhân viên)
DELIMITER //
CREATE PROCEDURE deleteDepartment(
    IN p_id INT
)
BEGIN
    DECLARE employeeCount INT;

    -- Kiểm tra xem phòng ban có nhân viên không
    SELECT COUNT(*) INTO employeeCount
    FROM Employees
    WHERE DepartmentId = p_id;

    IF employeeCount > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Không thể xóa phòng ban có nhân viên';
    ELSE
        DELETE FROM Department
        WHERE id = p_id;
    END IF;
END //
DELIMITER ;
# Tìm kiếm phòng ban theo tên
DELIMITER //
CREATE PROCEDURE searchDepartmentByName(
    IN p_DepartmentName VARCHAR(100)
)
BEGIN
    SELECT *
    FROM Department
    WHERE DepartmentName LIKE CONCAT('%', p_DepartmentName, '%');
END //
DELIMITER ;
# quản lí thông tin nhân viên
DELIMITER //
-- Lấy tổng số trang nhân viên (tức là số dòng)
CREATE PROCEDURE getPageEmployees()
BEGIN
    SELECT CEIL(COUNT(Employees.EmployeesId) / 10) AS totalPage
    FROM Employees;
END //
-- Lấy danh sách nhân viên theo từng trang
CREATE PROCEDURE getEmployeesByPage(
    IN p_page INT
)
BEGIN
    DECLARE pageSize INT DEFAULT 10;
    DECLARE offset INT;

    SET offset = (p_page - 1) * pageSize;

    SELECT *
    FROM Employees
    LIMIT pageSize OFFSET offset;
END //
-- Thêm nhân viên (chỉ thêm được vào phòng ban có trạng thái hoạt động)
CREATE PROCEDURE addEmployee(
    IN p_EmployeesId VARCHAR(5),
    IN p_EmployeesName VARCHAR(150),
    IN p_Email VARCHAR(50),
    IN p_phoneNumber VARCHAR(10),
    IN p_sex ENUM('MALE', 'FEMALE', 'OTHER'),
    IN p_salaryGrade INT,
    IN p_salary DECIMAL(10,2),
    IN p_birthDate DATE,
    IN p_address VARCHAR(100),
    IN p_DepartmentId INT
)
BEGIN
    DECLARE departmentStatus ENUM('ACTIVE', 'INACTIVE');

    -- Kiểm tra trạng thái của phòng ban
    SELECT status INTO departmentStatus
    FROM Department
    WHERE id = p_DepartmentId;

    IF departmentStatus = 'ACTIVE' THEN
        -- Thêm nhân viên nếu phòng ban đang hoạt động
        INSERT INTO Employees (EmployeesId, EmployeesName, Email, phoneNumber, sex, salaryGrade, salary, birthDate, address, DepartmentId)
        VALUES (p_EmployeesId, p_EmployeesName, p_Email, p_phoneNumber, p_sex, p_salaryGrade, p_salary, p_birthDate, p_address, p_DepartmentId);
    ELSE
        -- Báo lỗi nếu phòng ban không hoạt động
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Không thể thêm nhân viên vào phòng ban không hoạt động';
    END IF;
END //
-- Sửa thông tin nhân viên
CREATE PROCEDURE getEmployeeById(
    IN p_EmployeesId VARCHAR(5)
)
BEGIN
    SELECT *
    FROM Employees
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeeName(
    IN p_EmployeesId VARCHAR(5),
    IN p_EmployeesName VARCHAR(150)
)
BEGIN
    UPDATE Employees
    SET EmployeesName = p_EmployeesName
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeeEmail(
    IN p_EmployeesId VARCHAR(5),
    IN p_Email VARCHAR(50)
)
BEGIN
    UPDATE Employees
    SET Email = p_Email
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeePhoneNumber(
    IN p_EmployeesId VARCHAR(5),
    IN p_phoneNumber VARCHAR(10)
)
BEGIN
    UPDATE Employees
    SET phoneNumber = p_phoneNumber
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeeSex (
    IN p_EmployeesId VARCHAR(5),
    IN p_sex ENUM('MALE', 'FEMALE', 'OTHER')
)
BEGIN
    UPDATE Employees
    SET sex = p_sex
    WHERE EmployeesId = p_EmployeesId;
end //
CREATE PROCEDURE updateEmployeeSalaryGrade(
    IN p_EmployeesId VARCHAR(5),
    IN p_salaryGrade INT
)
BEGIN
    UPDATE Employees
    SET salaryGrade = p_salaryGrade
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeeSalary(
    IN p_EmployeesId VARCHAR(5),
    IN p_salary DECIMAL(10,2)
)
BEGIN
    UPDATE Employees
    SET salary = p_salary
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeeBirthDate(
    IN p_EmployeesId VARCHAR(5),
    IN p_birthDate DATE
)
BEGIN
    UPDATE Employees
    SET birthDate = p_birthDate
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeeAddress(
    IN p_EmployeesId VARCHAR(5),
    IN p_address VARCHAR(100)
)
BEGIN
    UPDATE Employees
    SET address = p_address
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeeStatus(
    IN p_EmployeesId VARCHAR(5),
    IN p_status ENUM('ACTIVE', 'INACTIVE', 'ONLEAVE', 'POLICYLEAVE')
)
BEGIN
    UPDATE Employees
    SET status = p_status
    WHERE EmployeesId = p_EmployeesId;
END //
CREATE PROCEDURE updateEmployeeDepartmentId(
    IN p_EmployeesId VARCHAR(5),
    IN p_DepartmentId INT
)
BEGIN
    DECLARE departmentStatus ENUM('ACTIVE', 'INACTIVE');

    -- Kiểm tra trạng thái của phòng ban
    SELECT status INTO departmentStatus
    FROM Department
    WHERE id = p_DepartmentId;

    IF departmentStatus = 'ACTIVE' THEN
        -- Cập nhật phòng ban nếu phòng ban đang hoạt động
        UPDATE Employees
        SET DepartmentId = p_DepartmentId
        WHERE EmployeesId = p_EmployeesId;
    ELSE
        -- Báo lỗi nếu phòng ban không hoạt động
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Không thể cập nhật nhân viên vào phòng ban không hoạt đ��ng';
    END IF;
END //
-- Xóa nhân viên
CREATE PROCEDURE deleteEmployee(
    IN p_EmployeesId VARCHAR(5)
)
BEGIN
    DECLARE employeeCount INT;

    -- Kiểm tra xem nhân viên có tồn tại không
    SELECT COUNT(*) INTO employeeCount
    FROM Employees
    WHERE EmployeesId = p_EmployeesId;

    IF employeeCount > 0 THEN
        UPDATE Employees
        SET status = 'INACTIVE'
        WHERE EmployeesId = p_EmployeesId;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Nhân viên không tồn tại';
    END IF;
END //
-- Tìm kiếm nhân viên theo tên
CREATE PROCEDURE searchEmployeeByName(
    IN p_EmployeesName VARCHAR(150)
)
BEGIN
    SELECT *
    FROM Employees
    WHERE EmployeesName LIKE CONCAT('%', p_EmployeesName, '%');
END //
-- Tìm kiếm nhân viên theo tuổi
CREATE PROCEDURE searchEmployeeByAge(
    IN p_age INT
)
BEGIN
    SELECT *
    FROM Employees
    WHERE YEAR(CURDATE()) - YEAR(birthDate) = p_age;
END //
-- Sắp xếp nhân viên theo:• Lương giảm dầnần• Tên nhân viên tăng dần
CREATE PROCEDURE sortEmployeesBySalaryDesc()
BEGIN
    SELECT *
    FROM Employees
    ORDER BY salary DESC;
END //
CREATE PROCEDURE sortEmployeesByNameAsc()
BEGIN
    SELECT *
    FROM Employees
    ORDER BY EmployeesName ASC;
END //
-- Các chưc năng thống kê
-- Số lượnợng nhân viên theo từng p phòng ban
CREATE PROCEDURE countEmployeesByDepartment()
BEGIN
    SELECT d.DepartmentName, COUNT(e.EmployeesId) AS EmployeeCount
    FROM Department d
    LEFT JOIN Employees e ON d.id = e.DepartmentId
    GROUP BY d.DepartmentName;
END //
-- Tổng số nhân viên của toàn bộ hệ thống
CREATE PROCEDURE countTotalEmployees()
BEGIN
    SELECT COUNT(*) AS TotalEmployees
    FROM Employees;
END //
-- Phòng ban có nhiều nhân viên nhất
CREATE PROCEDURE departmentWithMostEmployees()
BEGIN
    SELECT d.DepartmentName, COUNT(e.EmployeesId) AS EmployeeCount
    FROM Department d
    LEFT JOIN Employees e ON d.id = e.DepartmentId
    GROUP BY d.DepartmentName
    ORDER BY EmployeeCount DESC
    LIMIT 1;
END //
-- Phòng ban có lương cao nhất
CREATE PROCEDURE departmentWithHighestSalary()
BEGIN
    SELECT d.DepartmentName, AVG(e.salary) AS AverageSalary
    FROM Department d
    JOIN Employees e ON d.id = e.DepartmentId
    GROUP BY d.DepartmentName
    ORDER BY AverageSalary DESC
    LIMIT 1;
END //


DELIMITER ;
select * from Department;

