package com.cmpe202.HealthClubManagement.Service;

import com.cmpe202.HealthClubManagement.Dto.LogHoursDto;
import com.cmpe202.HealthClubManagement.Model.Activity;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Model.MemberSchedule;

import java.util.List;

public interface MemberService {
    Member getMember(String userName);
    List<MemberSchedule> getMemberSchedule(String username);
    List<Activity> getMemberActivity(String username);

    String registerClass(String username, int classId);

    String logHours(LogHoursDto logHoursDto);

    Member register(Member member);
}
