package com.cmpe202.HealthClubManagement.Service;

import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Model.MemberSchedule;

import java.util.List;

public interface MemberService {
    Member getMember(String userName);
    List<MemberSchedule> getMemberSchedule(String username);
}
