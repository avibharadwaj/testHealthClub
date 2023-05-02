package com.cmpe202.HealthClubManagement.Service.Impl;

import com.cmpe202.HealthClubManagement.Dao.MemberDao;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Override
    public Member getMember(String userName) {
        try {
            Optional<Member> member = memberDao.findByUsername(userName);
            System.out.println(member);
            return member.orElse(null);
        } catch (Exception e) {
            System.out.println("Error getting member detail: " + e.getMessage());
        }
        return null;
    }
}
