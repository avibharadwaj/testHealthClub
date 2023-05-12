package com.cmpe202.HealthClubManagement.Controller;

import com.cmpe202.HealthClubManagement.Config.LocalDateDeserializer;
import com.cmpe202.HealthClubManagement.Config.LocalDateSerializer;
import com.cmpe202.HealthClubManagement.Dto.ClassScheduleDto;
import com.cmpe202.HealthClubManagement.Model.ClassSchedule;
import com.cmpe202.HealthClubManagement.Service.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("enrollmentStatus")
    private ResponseEntity<Map<?, ?>> getEnrollmentStatus(
            @RequestParam String locationName,
            @RequestParam String filter) {
        Map<?, ?> dataMap = dashboardService.getEnrollmentStatus(locationName, filter);
        return new ResponseEntity<>(dataMap, HttpStatus.OK);
    }

    @GetMapping("totalHoursSpent")
    private ResponseEntity<Map<?, ?>> getTotalHoursSpentOnGymByFilter(
            @RequestParam String locationName,
            @RequestParam String filter
    ) {
        Map<?, ?> dataMap = dashboardService.getTotalHoursSpent(locationName, filter);
        System.out.println(dataMap);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(module);
        return new ResponseEntity<>(dataMap, HttpStatus.OK);
    }

    @GetMapping("hourlyOccupancy")
    private ResponseEntity<Map<?, ?>> getHourlyOccupancyOnGymByFilter(
            @RequestParam String locationName,
            @RequestParam String filter
    ) {
        Map<?, ?> dataMap = dashboardService.getHourlyOccupancy(locationName, filter);
        System.out.println(dataMap);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(module);
        return new ResponseEntity<>(dataMap, HttpStatus.OK);
    }
}
