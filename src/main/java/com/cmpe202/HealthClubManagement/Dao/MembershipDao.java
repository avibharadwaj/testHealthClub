package com.cmpe202.HealthClubManagement.Dao;

import com.cmpe202.HealthClubManagement.Model.Membership;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipDao extends CrudRepository<Membership, Integer> {
}
