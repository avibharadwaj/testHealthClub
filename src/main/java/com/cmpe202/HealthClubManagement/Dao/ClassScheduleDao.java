package com.cmpe202.HealthClubManagement.Dao;

import com.cmpe202.HealthClubManagement.Model.ClassSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassScheduleDao extends CrudRepository<ClassSchedule, Integer> {

    List<ClassSchedule> getByClassStatusIs(String classStatus);
    List<ClassSchedule> getClassScheduleByClassStatusIsNot(String classStatus);

}
