package com.cmpe202.HealthClubManagement.Service.Impl;

import com.cmpe202.HealthClubManagement.Dao.ActivityDao;
import com.cmpe202.HealthClubManagement.Dao.HealthClubDao;
import com.cmpe202.HealthClubManagement.Dao.MemberDao;
import com.cmpe202.HealthClubManagement.Dao.MemberScheduleDao;
import com.cmpe202.HealthClubManagement.Dto.CheckInOutDto;
import com.cmpe202.HealthClubManagement.Dto.MemberDto;
import com.cmpe202.HealthClubManagement.Dto.MemberScheduleDto;
import com.cmpe202.HealthClubManagement.Model.*;
import com.cmpe202.HealthClubManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberScheduleDao memberScheduleDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private HealthClubDao healthClubDao;

    @Override
    public Member enroll(Member member) {
        try {
        	Optional<Member> m = memberDao.findByUsername(member.getUsername());
            Member optM = m.get();
            optM.setEnrollmentStatus("enrolled");
            memberDao.save(optM);
            return optM;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    @Override
    public String checkInMember(String username) {
        try {
            System.out.println("checkInMember");
        	Optional<Member> member = memberDao.findByUsername(username);
            if (member.isEmpty()) {
                System.out.println("Member not found");
                return "Member not found";
            }

            List<Activity> currOpenActivityList = member.get().getActivities();
            Optional<Activity> act = currOpenActivityList.stream().filter(
                activity -> activity.getCheckOutTime() == null
            ).findAny();

            if (act.isEmpty()) {
                System.out.println("No null time found");
            }

            if (act.isPresent()) {
                return "Please check out from previous session";
            }

            Activity activity = new Activity();
            activity.setMember(member.get());
            activity.setCheckInTime(Time.valueOf(LocalTime.now()));
            activity.setDate(Date.valueOf(LocalDate.now()));
            List<Activity> activityList = new ArrayList<>();
            activityList.add(activity);
            member.get().setActivities(activityList);
            activityDao.save(activity);
            memberDao.save(member.get());
            return "Successfully Checked In";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String checkOutMember(String username) {
        try {
            System.out.println(username);
        	Optional<Member> member = memberDao.findByUsername(username);
            if (member.isEmpty()) {
                System.out.println("Member not found");
                return "Member not found";
            }

            List<Activity> currOpenActivityList = member.get().getActivities();
            Optional<Activity> act = currOpenActivityList.stream().filter(
                activity -> activity.getCheckOutTime() == null
            ).findAny();

            if (act.isEmpty()) {
                return "Please check in first to check out";
            }

            Activity activity = act.get();
            activity.setCheckOutTime(Time.valueOf(LocalTime.now()));
            activityDao.save(activity);
            System.out.println("Successfully Checked Out");
            return "Successfully Checked Out";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Member signUpTrialMembership(MemberDto memberDto) {
        try {
            Member member = new Member(memberDto);
            HealthClub healthClub = healthClubDao.getByLocationName(memberDto.locationName);
            Optional<Membership> membership = healthClub.getMembershipInfo().stream().filter(
                    membership1 -> membership1.getDuration() == memberDto.membershipDuration
            ).findFirst();
            member.setHealthClub(healthClub);
            member.setMembershipType(membership.get());
            member.setEnrollmentStatus(memberDto.enrollmentStatus);
            memberDao.save(member);
            return member;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
