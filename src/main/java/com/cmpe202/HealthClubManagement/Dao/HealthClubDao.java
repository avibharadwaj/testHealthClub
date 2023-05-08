package com.cmpe202.HealthClubManagement.Dao;

import com.cmpe202.HealthClubManagement.Model.HealthClub;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthClubDao extends CrudRepository<HealthClub, Integer> {
    HealthClub getByLocationName(String locationName);
}
