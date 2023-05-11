package com.cmpe202.HealthClubManagement.Dto;

import com.cmpe202.HealthClubManagement.Model.Member;


public class RegisterDto {
    public String name;
    public String username;
    public String password;
    public long phone;

    public RegisterDto(){
        super();
    }

    public RegisterDto(Member member) {
        this.name = member.getName();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.phone = member.getPhone();
    }


}
