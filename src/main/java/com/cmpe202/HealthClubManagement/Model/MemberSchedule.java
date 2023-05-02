package com.cmpe202.HealthClubManagement.Model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
public class MemberSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberScheduleId;
    private boolean onClassSchedule;
    @OneToOne
    private ClassSchedule classSchedule;

    private Date date;
    private Time fromTime;
    private Time toTime;
    private String classType;
    @OneToOne
    private Employee instructor;

    public MemberSchedule() {
    }

    public MemberSchedule(boolean onClassSchedule, ClassSchedule classSchedule) {
        this.onClassSchedule = onClassSchedule;
        this.classSchedule = classSchedule;
    }

    public MemberSchedule(Date date, Time fromTime, Time toTime, String classType, Employee instructor) {
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.classType = classType;
        this.instructor = instructor;
    }

    public boolean isOnClassSchedule() {
        return onClassSchedule;
    }

    public void setOnClassSchedule(boolean onClassSchedule) {
        this.onClassSchedule = onClassSchedule;
    }

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(ClassSchedule classSchedule) {
        this.classSchedule = classSchedule;
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

