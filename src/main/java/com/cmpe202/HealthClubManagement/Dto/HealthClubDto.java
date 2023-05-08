package com.cmpe202.HealthClubManagement.Dto;

import java.util.List;

public class HealthClubDto {
    public int clubId;
    public String locationName;
    public int memberEnrollments;
    public int employeeCount;
    public int scheduledClassCount;
    public List<MembershipDetailsDto> membershipDetailsDto;
}
