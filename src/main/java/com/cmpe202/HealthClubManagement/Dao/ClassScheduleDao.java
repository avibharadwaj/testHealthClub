package com.cmpe202.HealthClubManagement.Dao;

import com.cmpe202.HealthClubManagement.Model.ClassSchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassScheduleDao extends CrudRepository<ClassSchedule, Integer> {
}
