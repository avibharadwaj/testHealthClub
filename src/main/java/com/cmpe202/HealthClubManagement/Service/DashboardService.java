package com.cmpe202.HealthClubManagement.Service;

import com.cmpe202.HealthClubManagement.Model.ClassSchedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DashboardService {

    Map<?, ?> getEnrollmentStatus(String locationName, String filter);

    Map<?, ?> getTotalHoursSpent(String locationName, String filter);
}
