package com.cmpe202.HealthClubManagement.Service.Impl;

import com.cmpe202.HealthClubManagement.Dao.MemberDao;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member enroll(Member member) {
        try {
            memberDao.save(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }
}
