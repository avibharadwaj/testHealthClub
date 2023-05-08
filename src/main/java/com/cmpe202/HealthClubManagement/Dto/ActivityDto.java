package com.cmpe202.HealthClubManagement.Dto;

import java.sql.Date;
import java.sql.Time;

public class ActivityDto {

    public Date date;
    public Time fromTime;
    public Time toTime;
    public String classType;
    public String instructor;
    public Time checkInTime;
    public Time checkOutTime;
    public boolean onSchedule;
}
