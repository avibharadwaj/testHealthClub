package com.cmpe202.HealthClubManagement.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class HealthClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clubId;
    private String locationName;
    private String locationId;
    @OneToMany(targetEntity = Employee.class)
    private List<Employee> employees;
    @OneToMany(targetEntity = Member.class)
    private List<Member> members;
    @OneToMany(targetEntity = ClassSchedule.class)
    private List<ClassSchedule> classSchedules;
    @OneToMany(targetEntity = Membership.class)
    private List<Membership> membershipInfo;

    public HealthClub() {
    }

    public HealthClub(int clubId, String locationName, String locationId) {
        this.clubId = clubId;
        this.locationName = locationName;
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<ClassSchedule> getClassSchedules() {
        return classSchedules;
    }

    public void setClassSchedules(List<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
    }

    public List<Membership> getMembershipInfo() {
        return membershipInfo;
    }

    public void setMembershipInfo(List<Membership> membershipInfo) {
        this.membershipInfo = membershipInfo;
    }
}
