package com.cmpe202.HealthClubManagement.Service;

import com.cmpe202.HealthClubManagement.Dto.CheckInOutDto;
import com.cmpe202.HealthClubManagement.Dto.MemberDto;
import com.cmpe202.HealthClubManagement.Model.Member;

public interface EmployeeService {
    Member enroll(Member member);
    String checkInMember(CheckInOutDto checkInOutDto);

    String checkOutMember(CheckInOutDto checkInOutDto);

    Member signUpTrialMembership(MemberDto memberDto);
}
