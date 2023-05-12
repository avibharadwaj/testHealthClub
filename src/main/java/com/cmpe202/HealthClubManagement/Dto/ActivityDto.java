package com.cmpe202.HealthClubManagement.Dto;

import com.cmpe202.HealthClubManagement.Model.Activity;

import java.sql.Date;
import java.sql.Time;

public class ActivityDto {

    public ActivityDto(Activity activity) {
        this.date = activity.getDate();
        this.fromTime = activity.getFromTime();
        this.toTime = activity.getToTime();
        this.classType = activity.getClassType();
        this.instructor = activity.getInstructor().getEmployeeName();
        this.checkInTime = activity.getCheckInTime();
        this.checkOutTime = activity.getCheckOutTime();
        this.onSchedule = activity.isOnSchedule();
        this.loggedMinutes = activity.getLoggedMinutes();
        this.activityId = activity.getActivityId();
    }

    public Date date;
    public Time fromTime;
    public Time toTime;
    public String classType;
    public String instructor;
    public Time checkInTime;
    public Time checkOutTime;
    public boolean onSchedule;
    public long loggedMinutes;
    public int activityId;
}
