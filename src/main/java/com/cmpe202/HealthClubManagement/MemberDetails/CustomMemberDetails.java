package com.cmpe202.HealthClubManagement.MemberDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
import com.cmpe202.HealthClubManagement.Model.Member;
//Spring Security invokes Methods in this class during the authentication process

public class CustomMemberDetails implements UserDetails {
    private Member member;

    public CustomMemberDetails(Member member){
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
 
    @Override
    public String getPassword() {
        return member.getPassword();
    }
 
    @Override
    public String getUsername() {
        return member.getUsername();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
     
    public String getFullName() {
        return member.getName();
    }
    
}
