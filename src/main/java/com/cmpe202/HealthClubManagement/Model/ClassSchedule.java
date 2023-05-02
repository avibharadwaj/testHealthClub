package com.cmpe202.HealthClubManagement.Model;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Date;

@Entity
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classScheduleId;

    @OneToOne
    private HealthClub healthClub;

    private Date date;
    private Time fromTime;
    private Time toTime;
    private String classType;
    @OneToOne
    private Employee instructor;

    public ClassSchedule() {
    }

    public ClassSchedule(HealthClub healthClub, Date date, Time fromTime, Time toTime, String classType) {
        this.healthClub = healthClub;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.classType = classType;
    }

    public HealthClub getHealthClub() {
        return healthClub;
    }

    public void setHealthClub(HealthClub healthClub) {
        this.healthClub = healthClub;
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
