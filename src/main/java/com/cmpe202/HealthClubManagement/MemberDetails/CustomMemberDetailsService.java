package com.cmpe202.HealthClubManagement.MemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Service.MemberService;

public class CustomMemberDetailsService implements UserDetailsService{
    @Autowired
    private MemberService memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member  = memberRepo.getMember(username);
        if(member == null) {
            throw new UsernameNotFoundException("Member not found");
        }

        return new CustomMemberDetails(member);
    }
}
