package com.cmpe202.HealthClubManagement.Model;

//import jakarta.persistence.*;
import javax.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeePassword;
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    private String employeeRole;
    @OneToOne
    private HealthClub employeeClub;

    public Employee() {
    }

    public Employee(String employeeName, String employeeRole, HealthClub employeeClub) {
        this.employeeName = employeeName;
        this.employeeRole = employeeRole;
        this.employeeClub = employeeClub;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public HealthClub getEmployeeClub() {
        return employeeClub;
    }

    public void setEmployeeClub(HealthClub employeeClub) {
        this.employeeClub = employeeClub;
    }
}
