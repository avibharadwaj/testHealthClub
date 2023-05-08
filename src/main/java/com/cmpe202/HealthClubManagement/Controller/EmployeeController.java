package com.cmpe202.HealthClubManagement.Controller;

import com.cmpe202.HealthClubManagement.Dto.MemberDto;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Service.EmployeeService;
import com.cmpe202.HealthClubManagement.Service.Impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/enroll")
    private ResponseEntity<MemberDto> enrollMember(@RequestBody MemberDto memberDto) {
        Member member = convertToEntity(memberDto);
        Member response = employeeService.enroll(member);
        if (response != null) {
            MemberDto responseDto = new MemberDto(response);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private Member convertToEntity(MemberDto memberDto) {
        Member member = new Member();
        member.setUsername(memberDto.email);
        member.setPhone(memberDto.phone);
        member.setName(memberDto.name);
        return member;
    }
}
