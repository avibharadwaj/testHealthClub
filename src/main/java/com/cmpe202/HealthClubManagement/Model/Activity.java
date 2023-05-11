package com.cmpe202.HealthClubManagement.Model;

//import jakarta.persistence.*;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityId;
    private boolean onSchedule;
    @OneToOne
    private MemberSchedule mySchedule;
    @OneToOne
    private Member member;

    private Date date;
    private Time fromTime;
    private Time toTime;
    private String classType;
    @OneToOne
    private Employee instructor;

    private Time checkInTime;
    private Time checkOutTime;
    private boolean isLogged;
    private long loggedMinutes;

    public Activity() {
    }

    public Activity(boolean onSchedule, MemberSchedule mySchedule) {
        this.onSchedule = onSchedule;
        this.mySchedule = mySchedule;
    }

    public Activity(Date date, Time fromTime, Time toTime, String classType, Employee instructor) {
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.classType = classType;
        this.instructor = instructor;
    }

    public Activity(MemberSchedule memberSchedule) {
        this.onSchedule = true;
        this.mySchedule = memberSchedule;
        this.member = memberSchedule.getMember();
        this.date = memberSchedule.getDate();
        this.fromTime = memberSchedule.getFromTime();
        this.toTime = memberSchedule.getToTime();
        this.classType = memberSchedule.getClassType();
        this.instructor = memberSchedule.getInstructor();
    }

    public boolean isLogged() {
        return isLogged;
    }

    public long getLoggedMinutes() {
        return loggedMinutes;
    }

    public void setLoggedMinutes(long loggedMinutes) {
        this.loggedMinutes = loggedMinutes;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Time getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Time checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public boolean isOnSchedule() {
        return onSchedule;
    }

    public void setOnSchedule(boolean onSchedule) {
        this.onSchedule = onSchedule;
    }

    public MemberSchedule getMySchedule() {
        return mySchedule;
    }

    public void setMySchedule(MemberSchedule mySchedule) {
        this.mySchedule = mySchedule;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getFromTime() {
        return fromTime;
    }

    public void setFromTime(Time fromTime) {
        this.fromTime = fromTime;
    }

    public Time getToTime() {
        return toTime;
    }

    public void setToTime(Time toTime) {
        this.toTime = toTime;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Employee getInstructor() {
        return instructor;
    }

    public void setInstructor(Employee instructor) {
        this.instructor = instructor;
    }
}
