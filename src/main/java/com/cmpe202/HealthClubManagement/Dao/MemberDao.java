package com.cmpe202.HealthClubManagement.Dao;

import com.cmpe202.HealthClubManagement.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberDao extends CrudRepository<Member, Integer> {

    Optional<Member> findByUsername(String userName);
    List<Member> findAllByHealthClubClubId(int clubId);
}
