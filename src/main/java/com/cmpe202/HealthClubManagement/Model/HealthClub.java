package com.cmpe202.HealthClubManagement.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import jakarta.persistence.*;
import javax.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "clubId")
public class HealthClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clubId;
    private String locationName;
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

    public HealthClub(int clubId, String locationName) {
        this.clubId = clubId;
        this.locationName = locationName;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getClubId() {
        return clubId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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
