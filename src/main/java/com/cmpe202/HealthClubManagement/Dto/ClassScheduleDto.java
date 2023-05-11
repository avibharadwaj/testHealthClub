package com.cmpe202.HealthClubManagement.Dto;

import com.cmpe202.HealthClubManagement.Model.ClassSchedule;

import java.sql.Time;
import java.util.Date;

public class ClassScheduleDto {

    public ClassScheduleDto(ClassSchedule classSchedule) {
        this.classID = classSchedule.getClassScheduleId();
        this.classType = classSchedule.getClassType();
        this.allotedSeats = classSchedule.getCapacity();
        this.currentEnrollementCount = classSchedule.getCapacity() - classSchedule.getAvailableSeats();
        this.freeSeats = classSchedule.getAvailableSeats();
        this.instructor = classSchedule.getInstructor().getEmployeeName();
        this.classStatus = classSchedule.getClassStatus();
        this.date = classSchedule.getDate();
        this.fromTime = classSchedule.getFromTime();
        this.toTime = classSchedule.getToTime();
        this.enrollmentCount = classSchedule.getCapacity() - classSchedule.getAvailableSeats();
    }

    public int classID;
    public String classType;
    public int allotedSeats;
    public int currentEnrollementCount;
    public int freeSeats;
    public String instructor;
    public String classStatus;
    public Date date;
    public Time fromTime;
    public Time toTime;
    public int enrollmentCount;

}
