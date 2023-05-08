package com.cmpe202.HealthClubManagement.Dao;

import com.cmpe202.HealthClubManagement.Model.MemberSchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberScheduleDao extends CrudRepository<MemberSchedule, Integer> {

    List<MemberSchedule> findAllByMember_Username(String username);
}
