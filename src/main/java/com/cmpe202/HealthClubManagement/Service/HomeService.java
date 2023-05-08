package com.cmpe202.HealthClubManagement.Service;

import com.cmpe202.HealthClubManagement.Model.ClassSchedule;
import com.cmpe202.HealthClubManagement.Model.HealthClub;

import java.util.List;

public interface HomeService {

    HealthClub getClubInfo(String locationName);
    List<ClassSchedule> getSchedule(String locationName);
}
