package com.cmpe202.HealthClubManagement.Service;

import com.cmpe202.HealthClubManagement.Dto.CheckInOutDto;
import com.cmpe202.HealthClubManagement.Dto.MemberDto;
import com.cmpe202.HealthClubManagement.Model.Member;

public interface EmployeeService {

    Member enroll(Member member);
    String checkInMember(String username);

    String checkOutMember(String username);

    Member signUpTrialMembership(MemberDto memberDto);

    
}
