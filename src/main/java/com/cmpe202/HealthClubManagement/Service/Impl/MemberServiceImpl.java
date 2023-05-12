package com.cmpe202.HealthClubManagement.Service.Impl;

import com.cmpe202.HealthClubManagement.Dao.ActivityDao;
import com.cmpe202.HealthClubManagement.Dao.ClassScheduleDao;
import com.cmpe202.HealthClubManagement.Dao.MemberDao;
import com.cmpe202.HealthClubManagement.Dao.MemberScheduleDao;
import com.cmpe202.HealthClubManagement.Dto.LogHoursDto;
import com.cmpe202.HealthClubManagement.Model.Activity;
import com.cmpe202.HealthClubManagement.Model.ClassSchedule;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Model.MemberSchedule;
import com.cmpe202.HealthClubManagement.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberScheduleDao memberScheduleDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ClassScheduleDao classScheduleDao;
    @Override
    public Member getMember(String userName) {
        try {
            Optional<Member> member = memberDao.findByUsername(userName);
            System.out.println(member);
            return member.orElse(null);
        } catch (Exception e) {
            System.out.println("Error getting member detail: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<MemberSchedule> getMemberSchedule(String username) {
        try {
            Optional<Member> member = memberDao.findByUsername(username);
            System.out.println("Member found?: " + member.isPresent());
            if (member.isPresent()) {
                return memberScheduleDao.findAllByMember_Username(username);
            }
        } catch (Exception e) {
            System.out.println("Error getting member schedule: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Activity> getMemberActivity(String username) {
        try {
            Optional<Member> member = memberDao.findByUsername(username);
            System.out.println("Member found?: " + member.isPresent());
            if (member.isPresent()) {
                List<MemberSchedule> memberSchedules = member.get().getMySchedule();
                for (MemberSchedule memberSchedule: memberSchedules) {
                    if (memberSchedule.getToTime().toLocalTime().isBefore(LocalTime.now())) {
                        if (memberSchedule.getActivity() == null) {
                            Activity activity = new Activity(memberSchedule);
                            memberSchedule.setActivity(activity);
                            activity.setMember(member.get());
                            activity.setMySchedule(memberSchedule);
                            List<Activity> activities = member.get().getActivities();
                            activities.add(activity);
                            member.get().setActivities(activities);
                            activityDao.save(activity);
                            memberScheduleDao.save(memberSchedule);
                            List<MemberSchedule> memberSchedules1 = member.get().getMySchedule();
                            memberSchedules1.add(memberSchedule);
                            member.get().setMySchedule(memberSchedules1);
                            memberDao.save(member.get());
                        }
                    }
                }
                return activityDao.findAllByMember_Username(username);
            }
        } catch (Exception e) {
            System.out.println("Error getting member activity: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String registerClass(String username, int classId) {
        // fail - if class is full, already registered in the class, clashing with other class
        try {
            Optional<Member> member = memberDao.findByUsername(username);
            Optional<ClassSchedule> classSchedule = classScheduleDao.findById(classId);
            if (member.isEmpty()) {
                System.out.println("Member not found");
                return "Member not found";
            }
            if (classSchedule.isEmpty()) {
                System.out.println("Class not found");
                return "Class not found";
            }

            System.out.println("Member and class found");
            if (classSchedule.get().getAvailableSeats() == 0) {
                System.out.println("Class is already full");
                return "Class is already full.";
            }
            if (member.get().getMySchedule().stream().anyMatch(
                    memberSchedule -> memberSchedule.getClassSchedule() != null &&
                            memberSchedule.getClassSchedule().getClassScheduleId() == classId)
                    ) {
                System.out.println("Member already registered for this class.");
                return "Member already registered for this class.";
            }

            Date classDate = classSchedule.get().getDate();
            Time classFromTime = classSchedule.get().getFromTime();
            Time classToTime = classSchedule.get().getToTime();
            if (member.get().getMySchedule().stream().anyMatch(
                    memberSchedule -> memberSchedule.getDate().equals(classDate) &&
                            (classFromTime.before(memberSchedule.getToTime()) && classFromTime.after(memberSchedule.getFromTime()) ||
                                    classToTime.after(memberSchedule.getFromTime()) && classToTime.before(memberSchedule.getToTime())) ||
                            (classFromTime.equals(memberSchedule.getFromTime()) || classToTime.equals(memberSchedule.getToTime())))
            ) {
                System.out.println("Clashing with other registered class.");
                return "Clashing with other registered class.";
            }

            System.out.println("Check complete");
            // register for class
            classSchedule.get().setAvailableSeats(classSchedule.get().getAvailableSeats() - 1);
            if (classSchedule.get().getAvailableSeats() == 0) {
                classSchedule.get().setClassStatus("Filled");
            }
            classScheduleDao.save(classSchedule.get());
            MemberSchedule memberSchedule = new MemberSchedule(true, classSchedule.get());
            memberSchedule.setMember(member.get());
            List<MemberSchedule> memberSchedules = member.get().getMySchedule();
            memberSchedules.add(memberSchedule);
            memberScheduleDao.save(memberSchedule);
            memberDao.save(member.get());
            System.out.println("Registered");
            return "Registered for class successfully.";
        } catch (Exception e) {
            System.out.println("Error registering for class: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String logHours(LogHoursDto logHoursDto) {
        try {
            Optional<Member> member = memberDao.findByUsername(logHoursDto.username);
            if (member.isEmpty()) {
                return "Member not found";
            }

            Optional<Activity> activityOptional = activityDao.findById(logHoursDto.activityId);

            if (activityOptional.isEmpty()) {
                return "Activity not found";
            }
            Activity activity = activityOptional.get();
            MemberSchedule memberSchedule = activity.getMySchedule();

            String result = "";
            long loggedMinutes = activity.getLoggedMinutes();
            long toLogMinutes = logHoursDto.minutes;
            if (loggedMinutes != 0) {
                System.out.println("Already logged hours for this class. Overwriting it.");
                result = result.concat("Already logged hours for this class. Overwriting it.");
                toLogMinutes = logHoursDto.minutes;
            }
            long classTime = Math.abs(memberSchedule.getFromTime().getTime() - memberSchedule.getToTime().getTime());
            long classMinutes = TimeUnit.MILLISECONDS.toMinutes(classTime);
            if (classMinutes < logHoursDto.minutes) {
                System.out.println("Trying to log more hours than class duration. Logging hours for the total class duration.");
                result = "Trying to log more hours than class duration. Logging hours for the total class duration.";
                toLogMinutes = classMinutes;
            }

            activity.setLogged(true);
            activity.setLoggedMinutes(toLogMinutes);
            activityDao.save(activity);
            memberScheduleDao.save(memberSchedule);
            memberDao.save(member.get());
            System.out.println("Logged hours successfully");
            return result.concat("Logged hours successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Member register(Member member){
        try {
            System.out.println("---Registering a new member---");
            System.out.println(memberDao);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encodedPassword);
            memberDao.save(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }
}
