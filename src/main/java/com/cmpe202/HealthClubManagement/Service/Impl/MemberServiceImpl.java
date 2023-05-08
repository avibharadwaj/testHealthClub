package com.cmpe202.HealthClubManagement.Service.Impl;

import com.cmpe202.HealthClubManagement.Dao.MemberDao;
import com.cmpe202.HealthClubManagement.Dao.MemberScheduleDao;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Model.MemberSchedule;
import com.cmpe202.HealthClubManagement.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberScheduleDao memberScheduleDao;
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

    @Override
    public List<MemberSchedule> getMemberSchedule(String username) {
        try {
            Optional<Member> member = memberDao.findByUsername(username);
            System.out.println("Member found?: " + member.isPresent());
            if (member.isPresent()) {
                return memberScheduleDao.findAllByMember_Username(username);
            }
        } catch (Exception e) {
            System.out.println("Error getting member schedule: " + e.getMessage());
        }
        return null;
    }
}
