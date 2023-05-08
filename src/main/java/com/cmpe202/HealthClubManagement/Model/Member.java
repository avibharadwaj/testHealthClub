package com.cmpe202.HealthClubManagement.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "memberId")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;
    private String name;
    private String username;
    private String password;
    private long phone;
    private String address;

    @OneToOne
    private HealthClub healthClub;
    @OneToOne
    private Membership membershipType;
    @OneToMany(targetEntity = Activity.class)
    private List<Activity> activities;
    @OneToMany(targetEntity = MemberSchedule.class)
    private List<MemberSchedule> mySchedule;

    private String enrollmentStatus;

    public Member() {
    }

    public Member(String name, String username, String password, long phone, String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public Member(String name, String username, String password, long phone, String address, Membership membershipType) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.membershipType = membershipType;
    }

    public HealthClub getHealthClub() {
        return healthClub;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setHealthClub(HealthClub healthClub) {
        this.healthClub = healthClub;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Membership getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(Membership membershipType) {
        this.membershipType = membershipType;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<MemberSchedule> getMySchedule() {
        return mySchedule;
    }

    public void setMySchedule(List<MemberSchedule> mySchedule) {
        this.mySchedule = mySchedule;
    }
}

