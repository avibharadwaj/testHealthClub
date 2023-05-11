package com.cmpe202.HealthClubManagement.Dto;

import com.cmpe202.HealthClubManagement.Model.MemberSchedule;

import java.sql.Date;
import java.sql.Time;

public class MemberScheduleDto {
    public MemberScheduleDto(MemberSchedule memberSchedule) {
        this.date = memberSchedule.getDate();
        this.fromTime = memberSchedule.getFromTime();
        this.toTime = memberSchedule.getToTime();
        this.classType = memberSchedule.getClassType();
        this.instructor = memberSchedule.getInstructor().getEmployeeName();
        this.myScheduleId = memberSchedule.getMemberScheduleId();
    }

    public Date date;
    public Time fromTime;
    public Time toTime;
    public String classType;
    public String instructor;
    public int myScheduleId;
}
