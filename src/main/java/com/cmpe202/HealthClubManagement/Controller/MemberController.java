package com.cmpe202.HealthClubManagement.Controller;

import com.cmpe202.HealthClubManagement.Dto.ActivityDto;
import com.cmpe202.HealthClubManagement.Dto.LogHoursDto;
import com.cmpe202.HealthClubManagement.Dto.MemberDto;
import com.cmpe202.HealthClubManagement.Dto.MemberScheduleDto;
import com.cmpe202.HealthClubManagement.Dto.RegisterDto;
import com.cmpe202.HealthClubManagement.Model.Activity;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Model.MemberSchedule;
import com.cmpe202.HealthClubManagement.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Access-Control-Allow-Origin")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/home")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/employeeTest")
    public String viewEmployeeTest(){
        return "employee";
    }

    @GetMapping(value="/login")
    public String loginMember(Model model){
        model.addAttribute("user", new Member());
        return "login";
    }

    @GetMapping("/registerUser")
    public String registerUser(){
        return "register";
    }
    	
    @GetMapping("/analytics")
    public String viewAnalytics() {
    	return "analytics";
    }
    
    @GetMapping("/memberInfo")
    public String getMemberInfo(){
        return "member";
    }

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

    @PostMapping("/register")
    private ResponseEntity<RegisterDto> registerMember(@RequestBody RegisterDto registerDto) {
        Member member = convertToEntity(registerDto);
        Member response = memberService.register(member);
        if (response != null) {
            RegisterDto responseDto = new RegisterDto(response);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private Member convertToEntity(RegisterDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.name);
        member.setUsername(memberDto.username);
        member.setPassword(memberDto.password);
        member.setPhone(memberDto.phone);
        return member;
    }

    @GetMapping("member/mySchedule")
    private ResponseEntity<List<MemberScheduleDto>> getSchedule(@RequestParam("username") String username) {
        List<MemberSchedule> memberScheduleList = memberService.getMemberSchedule(username);
        if (memberScheduleList.size() != 0) {
            List<MemberScheduleDto> memberScheduleDtos = new ArrayList<>();
            for (MemberSchedule memberSchedule: memberScheduleList) {
                memberScheduleDtos.add(new MemberScheduleDto(memberSchedule));
            }
            return new ResponseEntity<>(memberScheduleDtos, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/member/activity")
    private ResponseEntity<List<ActivityDto>> getMemberActivity(@RequestParam String username, @RequestParam String duration) {
        List<Activity> activities = memberService.getMemberActivity(username);
        if (activities.size() != 0) {
            List<Activity> activitiesByFilter = getActivitiesByFilter(duration, activities);
            List<ActivityDto> activityDtos = new ArrayList<>();
            for (Activity activity: activitiesByFilter) {
                activityDtos.add(new ActivityDto(activity));
            }
            return new ResponseEntity<>(activityDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    private List<Activity> getActivitiesByFilter(String duration, List<Activity> activities) {
        LocalDate today = LocalDate.now();
        LocalDate fromDate;
        if (duration.equalsIgnoreCase("week")) {
            fromDate = today.minusWeeks(1);
        } else if (duration.equalsIgnoreCase("month")) {
            fromDate = today.minusMonths(1);
        } else {
            fromDate = today.minusMonths(3);
        }
        Date fd = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date td = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return activities.stream().filter(
                activity -> activity.getDate().after(fd) &&
                        activity.getDate().before(td)
        ).toList();
    }

    @PostMapping("/member/classRegister")
    private ResponseEntity<String> registerClass(@RequestParam String username, @RequestParam int classId) {
        String response = memberService.registerClass(username, classId);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/member/logHours")
    private ResponseEntity<String> logHours(@RequestBody LogHoursDto logHoursDto) {
        String response = memberService.logHours(logHoursDto);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
