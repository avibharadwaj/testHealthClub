package com.cmpe202.HealthClubManagement.Controller;

import com.cmpe202.HealthClubManagement.Dto.MemberDto;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Service.EmployeeService;
import com.cmpe202.HealthClubManagement.Service.Impl.EmployeeServiceImpl;
import com.cmpe202.HealthClubManagement.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/getUser")
    private ResponseEntity<Member> getMember(@RequestParam("userName") String username) {
        System.out.println("username: "+ username);
        Member member = memberService.getMember(username);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
