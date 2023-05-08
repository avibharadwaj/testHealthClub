package com.cmpe202.HealthClubManagement.Controller;

import com.cmpe202.HealthClubManagement.Dto.MemberDto;
import com.cmpe202.HealthClubManagement.Dto.MemberScheduleDto;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Model.MemberSchedule;
import com.cmpe202.HealthClubManagement.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/getUser")
    private ResponseEntity<MemberDto> getMember(@RequestParam("username") String username) {
        System.out.println("username: "+ username);
        Member member = memberService.getMember(username);
        if (member != null) {
            System.out.println("got response back at controller");
            MemberDto memberDto = new MemberDto(member);
            return new ResponseEntity<>(memberDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/mySchedule")
    private ResponseEntity<List<MemberScheduleDto>> getSchedule(@RequestParam("username") String username) {
        List<MemberSchedule> memberScheduleList = memberService.getMemberSchedule(username);
        if (memberScheduleList.size() != 0) {
            List<MemberScheduleDto> memberScheduleDtos = new ArrayList<>();
            for (MemberSchedule memberSchedule: memberScheduleList) {
                memberScheduleDtos.add(covertToMemScheduleDto(memberSchedule));
            }
            return new ResponseEntity<>(memberScheduleDtos, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    private MemberScheduleDto covertToMemScheduleDto(MemberSchedule memberSchedule) {
        MemberScheduleDto memberScheduleDto = new MemberScheduleDto();
        memberScheduleDto.date = memberSchedule.getDate();
        memberScheduleDto.fromTime = memberSchedule.getFromTime();
        memberScheduleDto.toTime = memberSchedule.getToTime();
        memberScheduleDto.classType = memberSchedule.getClassType();
        memberScheduleDto.instructor = memberSchedule.getInstructor().getEmployeeName();
        return memberScheduleDto;
    }

    private MemberSchedule convertToMemScheduleEntity(MemberScheduleDto memberScheduleDto) {
        MemberSchedule memberSchedule = new MemberSchedule();
        memberSchedule.setDate(memberScheduleDto.date);
        memberSchedule.setFromTime(memberScheduleDto.fromTime);
        memberSchedule.setToTime(memberScheduleDto.toTime);
        memberSchedule.setClassType(memberScheduleDto.classType);
        return memberSchedule;
    }
}
