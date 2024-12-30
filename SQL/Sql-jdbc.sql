CREATE DATABASE employee_jdbc;
USE employee_jdbc;
CREATE TABLE employee (
    empcode INT PRIMARY KEY,
    empname VARCHAR(50),
    empage INT,
    esalary DOUBLE
);
DESCRIBE employee;
SELECT * FROM employee;
