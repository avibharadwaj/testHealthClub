package com.cmpe202.HealthClubManagement.Model;

//import jakarta.persistence.*;
import javax.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    private String employeeName;
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
