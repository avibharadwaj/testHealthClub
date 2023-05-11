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
            memberDao.save(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    @Override
    public String checkInMember(CheckInOutDto checkInOutDto) {
        try {
            Optional<Member> member = memberDao.findByUsername(checkInOutDto.username);
            if (member.isEmpty()) {
                System.out.println("Member not found");
                return "Member not found";
            }

            Optional<MemberSchedule> memberSchedule = memberScheduleDao.findById(checkInOutDto.myScheduleId);
            if (memberSchedule.isEmpty()) {
                System.out.println("Not on schedule. Check in rejeted.");
                return "Not on schedule. Check in rejeted.";
            }

            String result = "";
            Activity activity = memberSchedule.get().getActivity();
            if (activity == null) {
                System.out.println("Adding activity.");
                result = "Adding activity.";
                activity = new Activity(memberSchedule.get());
            }

            activity.setCheckInTime(checkInOutDto.time);
            memberSchedule.get().setActivity(activity);
            activityDao.save(activity);
            memberScheduleDao.save(memberSchedule.get());
            memberDao.save(member.get());
            System.out.println("Checked in successfully");
            return result.concat("Checked in successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String checkOutMember(CheckInOutDto checkInOutDto) {
        try {
            Optional<Member> member = memberDao.findByUsername(checkInOutDto.username);
            if (member.isEmpty()) {
                System.out.println("Member not found");
                return "Member not found";
            }

            Optional<MemberSchedule> memberSchedule = memberScheduleDao.findById(checkInOutDto.myScheduleId);
            if (memberSchedule.isEmpty()) {
                System.out.println("Not on schedule. Check out rejeted.");
                return "Not on schedule. Check out rejeted.";
            }

            Activity activity = memberSchedule.get().getActivity();
            if (activity == null) {
                System.out.println("No Check in found for this class. Check out rejected.");
                return "No Check in found for this class. Check out rejected.";
            }

            if (checkInOutDto.time.before(activity.getCheckInTime())) {
                System.out.println("Invalid check out time. Check out time should be after check in time.");
                return "Invalid check out time. Check out time should be after check in time.";
            }

            activity.setCheckOutTime(checkInOutDto.time);
            memberSchedule.get().setActivity(activity);
            activityDao.save(activity);
            memberScheduleDao.save(memberSchedule.get());
            memberDao.save(member.get());
            System.out.println("Checked out successfully");
            return "Checked out successfully";
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
