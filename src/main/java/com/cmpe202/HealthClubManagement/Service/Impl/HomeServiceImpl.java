package com.cmpe202.HealthClubManagement.Service.Impl;

import com.cmpe202.HealthClubManagement.Dao.ClassScheduleDao;
import com.cmpe202.HealthClubManagement.Dao.HealthClubDao;
import com.cmpe202.HealthClubManagement.Model.ClassSchedule;
import com.cmpe202.HealthClubManagement.Model.HealthClub;
import com.cmpe202.HealthClubManagement.Service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    HealthClubDao healthClubDao;

    @Autowired
    ClassScheduleDao classScheduleDao;

    @Override
    public HealthClub getClubInfo(String locationName) {
        try {
            return healthClubDao.getByLocationName(locationName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ClassSchedule> getSchedule(String locationName) {
        try {
            HealthClub healthClub = healthClubDao.getByLocationName(locationName);
            List<ClassSchedule> classSchedules = healthClub.getClassSchedules();
            return classSchedules.stream().filter(
                    classSchedule -> !classSchedule.getClassStatus().equalsIgnoreCase("Completed")
                    ).toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
