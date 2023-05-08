package com.cmpe202.HealthClubManagement.Controller;

import com.cmpe202.HealthClubManagement.Dto.ClassScheduleDto;
import com.cmpe202.HealthClubManagement.Dto.HealthClubDto;
import com.cmpe202.HealthClubManagement.Dto.MembershipDetailsDto;
import com.cmpe202.HealthClubManagement.Model.ClassSchedule;
import com.cmpe202.HealthClubManagement.Model.HealthClub;
import com.cmpe202.HealthClubManagement.Model.Membership;
import com.cmpe202.HealthClubManagement.Service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/healthClub/info")
    private ResponseEntity<HealthClubDto> getClubInfoByLocation(@RequestParam String locationName) {
        HealthClub healthClub = homeService.getClubInfo(locationName);
        if (healthClub != null) {
            HealthClubDto healthClubDto = convertToHealthClubDto(healthClub);
            return new ResponseEntity<>(healthClubDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/schedule")
    private ResponseEntity<List<ClassScheduleDto>> getScheduleByLocation(@RequestParam String locationName) {
        List<ClassSchedule> classSchedules = homeService.getSchedule(locationName);
        if (classSchedules.size() != 0) {
            List<ClassScheduleDto> classScheduleDtos = new ArrayList<>();
            for (ClassSchedule classSchedule: classSchedules) {
                ClassScheduleDto classScheduleDto = new ClassScheduleDto(classSchedule);
                classScheduleDtos.add(classScheduleDto);
            }
            return new ResponseEntity<>(classScheduleDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private HealthClubDto convertToHealthClubDto(HealthClub healthClub) {
        HealthClubDto healthClubDto = new HealthClubDto();
        healthClubDto.clubId = healthClub.getClubId();
        healthClubDto.locationName = healthClub.getLocationName();
        healthClubDto.employeeCount = healthClub.getEmployees().size();
        healthClubDto.memberEnrollments = healthClub.getMembers().size();
        healthClubDto.scheduledClassCount = (int) healthClub.getClassSchedules().stream().filter(
                classSchedule -> classSchedule.getFromTime().after(Date.from(Instant.now()))
        ).count();
        healthClubDto.membershipDetailsDto = convertToMembershipDto(healthClub.getMembershipInfo());
        return healthClubDto;
    }

    private List<MembershipDetailsDto> convertToMembershipDto(List<Membership> membershipInfo) {
        List<MembershipDetailsDto> membershipDetailsDtos = new ArrayList<>();
        for (Membership membership: membershipInfo) {
            MembershipDetailsDto membershipDetailsDto = new MembershipDetailsDto();
            membershipDetailsDto.membershipId = membership.getMembershipTypeId();
            membershipDetailsDto.membershipDuration = membership.getDuration();
            membershipDetailsDto.price = membership.getPrice();
            membershipDetailsDtos.add(membershipDetailsDto);
        }
        return membershipDetailsDtos;
    }
}
