package com.cmpe202.HealthClubManagement.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;
    private String memberName;
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

    public Member() {
    }

    public Member(String memberName, String username, String password, long phone, String address) {
        this.memberName = memberName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public Member(String memberName, String username, String password, long phone, String address, HealthClub healthClub, Membership membershipType) {
        this.memberName = memberName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.healthClub = healthClub;
        this.membershipType = membershipType;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public HealthClub getHealthClub() {
        return healthClub;
    }

    public void setHealthClub(HealthClub healthClub) {
        this.healthClub = healthClub;
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

