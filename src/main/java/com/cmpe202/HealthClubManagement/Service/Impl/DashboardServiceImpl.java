package com.cmpe202.HealthClubManagement.Service.Impl;

import com.cmpe202.HealthClubManagement.Dao.ClassScheduleDao;
import com.cmpe202.HealthClubManagement.Dao.HealthClubDao;
import com.cmpe202.HealthClubManagement.Dao.MemberDao;
import com.cmpe202.HealthClubManagement.Model.Activity;
import com.cmpe202.HealthClubManagement.Model.ClassSchedule;
import com.cmpe202.HealthClubManagement.Model.HealthClub;
import com.cmpe202.HealthClubManagement.Model.Member;
import com.cmpe202.HealthClubManagement.Service.DashboardService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private HealthClubDao healthClubDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ClassScheduleDao classScheduleDao;
    @Override
    public Map<?, ?> getEnrollmentStatus(String locationName, String filter) {
        try {
            HealthClub healthClub = healthClubDao.getByLocationName(locationName);
            if (healthClub == null)
                return null;

            List<ClassSchedule> allClassSchedules = healthClub.getClassSchedules();

            if (filter.equalsIgnoreCase("day")) {
                return this.getEnrollmentByDay(allClassSchedules);
            }

            if (filter.equalsIgnoreCase("week")) {
                return this.getEnrollmentByWeek(allClassSchedules);
            }

            if (filter.equalsIgnoreCase("month")) {
                return this.getEnrollmentByMonth(allClassSchedules);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<?,?> getEnrollmentByWeek(List<ClassSchedule> allClassSchedules) {
        Map<LocalDate, Map<String, List<ClassSchedule>>> enrollmentsByWeekAndName = allClassSchedules.stream()
                .collect(Collectors.groupingBy(cs -> ((java.sql.Date) cs.getDate()).toLocalDate(),
                        Collectors.groupingBy(ClassSchedule::getClassType)));

        Map<String, Map<String, Object>> result = new LinkedHashMap<>();
        DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("w");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Map.Entry<LocalDate, Map<String, List<ClassSchedule>>> entry : enrollmentsByWeekAndName.entrySet()) {
            LocalDate startDate = entry.getKey().with(DayOfWeek.SUNDAY);
            LocalDate endDate = entry.getKey().with(DayOfWeek.SATURDAY);
            String weekNumber = "Week " + entry.getKey().format(weekFormatter);
            String weekStartDate = startDate.format(dateFormatter);
            String weekEndDate = endDate.format(dateFormatter);
            Map<String, Object> weekData = new LinkedHashMap<>();
            //weekData.put("weekStartDate", weekStartDate);
            //weekData.put("weekEndDate", weekEndDate);
            for (Map.Entry<String, List<ClassSchedule>> subEntry : entry.getValue().entrySet()) {
                String className = subEntry.getKey();
                List<ClassSchedule> enrollments = subEntry.getValue();
                int totalCapacity = enrollments.stream().mapToInt(ClassSchedule::getCapacity).sum();
                int availableSeats = enrollments.stream().mapToInt(ClassSchedule::getAvailableSeats).sum();
                Map<String, Object> classData = new LinkedHashMap<>();
                classData.put("className", className);
                classData.put("capacity", totalCapacity);
                classData.put("enrollmentCount", totalCapacity - availableSeats);
                weekData.put(className, classData);
            }
            result.put(weekNumber, weekData);
        }
        return result;
    }

    private Map<?,?> getEnrollmentByMonth(List<ClassSchedule> allClassSchedules) {
        Map<Date, Map<String, List<ClassSchedule>>> enrollmentsByDateAndName = allClassSchedules.stream()
                .collect(Collectors.groupingBy(ClassSchedule::getDate,
                        Collectors.groupingBy(ClassSchedule::getClassType)));

        Map<String, Map<String, Map<String, Object>>> result = new HashMap<>();
        for (Map.Entry<Date, Map<String, List<ClassSchedule>>> entry : enrollmentsByDateAndName.entrySet()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entry.getKey());
            String monthName = new SimpleDateFormat("MMMM").format(calendar.getTime());

            Map<String, Map<String, Object>> dateData = new HashMap<>();
            for (Map.Entry<String, List<ClassSchedule>> subEntry : entry.getValue().entrySet()) {
                String className = subEntry.getKey();
                List<ClassSchedule> enrollments = subEntry.getValue();
                int totalCapacity = enrollments.stream().mapToInt(ClassSchedule::getCapacity).sum();
                int availableSeats = enrollments.stream().mapToInt(ClassSchedule::getAvailableSeats).sum();
                Map<String, Object> classData = new HashMap<>();
                classData.put("className", className);
                classData.put("capacity", totalCapacity);
                classData.put("enrollmentCount", totalCapacity - availableSeats);
                dateData.put(className, classData);
            }
            result.computeIfAbsent(monthName, k -> new HashMap<>()).putAll(dateData);
        }
        return result;
    }

    private Map<?,?> getEnrollmentByDay(List<ClassSchedule> allClassSchedules) {
        Map<Date, Map<String, List<ClassSchedule>>> enrollmentsByDateAndName = allClassSchedules.stream()
                .collect(Collectors.groupingBy(ClassSchedule::getDate,
                        Collectors.groupingBy(ClassSchedule::getClassType)));

        Map<String, Map<String, Map<String, Object>>> result = new HashMap<>();
        for (Map.Entry<Date, Map<String, List<ClassSchedule>>> entry : enrollmentsByDateAndName.entrySet()) {
            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(entry.getKey());
            Map<String, Map<String, Object>> dateData = new HashMap<>();
            for (Map.Entry<String, List<ClassSchedule>> subEntry : entry.getValue().entrySet()) {
                String className = subEntry.getKey();
                List<ClassSchedule> enrollments = subEntry.getValue();
                int totalCapacity = enrollments.stream().mapToInt(ClassSchedule::getCapacity).sum();
                int availableSeats = enrollments.stream().mapToInt(ClassSchedule::getAvailableSeats).sum();
                Map<String, Object> classData = new HashMap<>();
                classData.put("className", className);
                classData.put("capacity", totalCapacity);
                classData.put("enrollmentCount", totalCapacity- availableSeats);
                dateData.put(className, classData);
            }
            result.put(dateString, dateData);
        }
        return result;
    }

    @Override
    public Map<?, ?> getTotalHoursSpent(String locationName, String filter) {
        try {
            Map<Object, Object> responseMap = new HashMap<>();
            HealthClub healthClub = healthClubDao.getByLocationName(locationName);
            if (healthClub == null) {
                responseMap.put("Error", "Health club not found");
                return responseMap;
            }

            List<Member> members = memberDao.findAllByHealthClubClubId(healthClub.getClubId());
            if (members.size() == 0) {
                responseMap.put("Error", "No members registered for given Health club");
                return responseMap;
            }

            List<Activity> activities = new ArrayList<>();
            for (Member member: members) {
                System.out.println("Member activities: "+ member.getName() + " - count: " + member.getActivities().size());
                activities.addAll(member.getActivities());
            }

            System.out.println("Activities size: "+activities.size());
            if (filter.equalsIgnoreCase("day")) {
                return this.getTotalHoursByDay(activities);
            }

            if (filter.equalsIgnoreCase("week")) {
                return this.getTotalHoursByWeek(activities);
            }

            if (filter.equalsIgnoreCase("month")) {
                return this.getActivitiesByMonth(activities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<?, ?> getHourlyOccupancy(String locationName, String filter) {
        try {
            Map<Object, Object> responseMap = new HashMap<>();
            HealthClub healthClub = healthClubDao.getByLocationName(locationName);
            if (healthClub == null) {
                responseMap.put("Error", "Health club not found");
                return responseMap;
            }

            List<Member> members = memberDao.findAllByHealthClubClubId(healthClub.getClubId());
            if (members.size() == 0) {
                responseMap.put("Error", "No members registered for given Health club");
                return responseMap;
            }

            List<Activity> activities = new ArrayList<>();
            for (Member member: members) {
                System.out.println("Member activities: "+ member.getName() + " - count: " + member.getActivities().size());
                activities.addAll(member.getActivities());
            }

            System.out.println("Activities size: "+activities.size());
            if (filter.equalsIgnoreCase("day")) {
                return this.getHourlyOccupancyByDay(activities);
            }

            if (filter.equalsIgnoreCase("weekday")) {
                return this.getHourlyOccupancyByWeekday(activities);
            }

            if (filter.equalsIgnoreCase("weekend")) {
                return this.getHourlyOccupancyByWeekend(activities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<?,?> getHourlyOccupancyByWeekend(List<Activity> activities) {
        return activities.stream()
                .filter(activity -> activity.getCheckInTime() != null &&
                        (activity.getDate().toLocalDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
                                activity.getDate().toLocalDate().getDayOfWeek() == DayOfWeek.SUNDAY))
                .collect(Collectors.groupingBy(
                        activity -> activity.getDate().toLocalDate().get(WeekFields.ISO.weekOfWeekBasedYear()),
                        Collectors.groupingBy(
                                activity -> activity.getCheckInTime().toLocalTime().getHour(),
                                Collectors.counting()
                        )
                ));
    }

    private Map<?,?> getHourlyOccupancyByWeekday(List<Activity> activities) {
        return activities.stream()
                .filter(activity -> activity.getCheckInTime() != null
                        && activity.getDate().toLocalDate().getDayOfWeek().getValue() < 6)
                .collect(Collectors.groupingBy(
                        activity -> activity.getDate().toLocalDate().atTime(activity.getCheckInTime().toLocalTime())
                                .get(WeekFields.ISO.weekOfWeekBasedYear()),
                        Collectors.groupingBy(
                                activity -> activity.getCheckInTime().toLocalTime().getHour(),
                                Collectors.counting()
                        )
                ));
    }

    private Map<?,?> getHourlyOccupancyByDay(List<Activity> activities) {
        return activities.stream()
                .filter(activity -> activity.getCheckInTime() != null)
                .collect(Collectors.groupingBy(activity -> activity.getCheckInTime().toLocalTime().atDate(activity.getDate().toLocalDate()).toLocalDate(),
                        Collectors.groupingBy(activity -> activity.getCheckInTime().toLocalTime().getHour(), Collectors.counting())));
    }

    private Map<?, ?> getTotalHoursByWeek(List<Activity> activities) {
        Map<String, Object> weeklyReport = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<LocalDate, List<Activity>> activitiesByWeek = activities.stream()
                .collect(Collectors.groupingBy(activity ->
                        activity.getDate().toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))));

        for (Map.Entry<LocalDate, List<Activity>> entry : activitiesByWeek.entrySet()) {
            LocalDate weekStartDate = entry.getKey();
            LocalDate weekEndDate = weekStartDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
            long totalMinutesOfWeek = entry.getValue().stream()
                    .mapToLong(Activity::getLoggedMinutes)
                    .sum();
            double totalHoursOfWeek = totalMinutesOfWeek / 60.0;

            Map<String, Object> weekData = new HashMap<>();
            weekData.put("weekNumber", weekStartDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
            weekData.put("weekStartDate", weekStartDate);
            weekData.put("weekEndDate", weekEndDate);
            weekData.put("totalMinutesOfWeek", totalMinutesOfWeek);
            weekData.put("totalHoursOfWeek", totalHoursOfWeek);

            weeklyReport.put(String.valueOf(weekStartDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)), weekData);
        }

        return weeklyReport;
    }

    private Map<String, Double> getTotalHoursByDay(List<Activity> activities) {
        Map<java.sql.Date, Long> minutesMap = activities.stream()
                .collect(Collectors.groupingBy(Activity::getDate,
                        Collectors.summingLong(Activity::getLoggedMinutes)));

        Map<String, Double> hoursMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Map.Entry<java.sql.Date, Long> entry : minutesMap.entrySet()) {
            java.sql.Date date = entry.getKey();
            String dateString = entry.getKey().toLocalDate().format(formatter);
            int minutes = Math.toIntExact(entry.getValue());
            double hours = (double) minutes / 60.0;
            hoursMap.put(dateString, hours);
        }
        return hoursMap;
    }

    private Map<?, ?> getActivitiesByMonth(List<Activity> activities) {
        Map<YearMonth, List<Activity>> activitiesByMonth = activities.stream()
                .collect(Collectors.groupingBy(activity ->
                        YearMonth.from(activity.getDate().toLocalDate())));

        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<YearMonth, List<Activity>> entry : activitiesByMonth.entrySet()) {
            YearMonth yearMonth = entry.getKey();
            int year = yearMonth.getYear();
            String monthName = yearMonth.getMonth().name();
            long totalMinutesOfMonth = entry.getValue().stream()
                    .mapToLong(Activity::getLoggedMinutes)
                    .sum();
            double totalHoursOfMonth = totalMinutesOfMonth / 60.0;

            Map<String, Object> monthData = new HashMap<>();
            monthData.put("monthName", monthName);
            monthData.put("year", year);
            monthData.put("totalMinutesOfMonth", totalMinutesOfMonth);
            monthData.put("totalHoursOfMonth", totalHoursOfMonth);

            result.put(yearMonth.toString(), monthData);
        }

        return result;
    }

}
