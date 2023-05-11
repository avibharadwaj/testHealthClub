package com.cmpe202.HealthClubManagement.Dto;

import com.cmpe202.HealthClubManagement.Model.Member;

public class MemberDto {

    public MemberDto() {
        super();
    }

    public MemberDto(Member member) {
        this.name = member.getName();
        this.username = member.getUsername();
        this.phone = member.getPhone();
        this.enrollmentStatus = member.getEnrollmentStatus();
        //this.locationName = member.getHealthClub().getLocationName();
        //this.membershipDuration = member.getMembershipType().getDuration();
    }

    public String name;
    public String username;
    public long phone;
    public String enrollmentStatus;
    public String locationName;
    public int membershipDuration;
}
