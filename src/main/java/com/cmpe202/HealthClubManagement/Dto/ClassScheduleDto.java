package com.cmpe202.HealthClubManagement.Dto;

import com.cmpe202.HealthClubManagement.Model.ClassSchedule;

public class ClassScheduleDto {

    public ClassScheduleDto(ClassSchedule classSchedule) {
        this.classID = classSchedule.getClassScheduleId();
        this.classType = classSchedule.getClassType();
        this.allotedSeats = classSchedule.getCapacity();
        this.currentEnrollementCount = classSchedule.getCapacity() - classSchedule.getAvailableSeats();
        this.freeSeats = classSchedule.getAvailableSeats();
        this.instructor = classSchedule.getInstructor().getEmployeeName();
        this.classStatus = classSchedule.getClassStatus();
    }

    public int classID;
    public String classType;
    public int allotedSeats;
    public int currentEnrollementCount;
    public int freeSeats;
    public String instructor;
    public String classStatus;

}
