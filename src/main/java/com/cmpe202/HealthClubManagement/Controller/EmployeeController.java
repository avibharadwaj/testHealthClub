package com.cmpe202.HealthClubManagement.Controller;

import com.cmpe202.HealthClubManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

//    @Autowired
//    private EmployeeService employeeService;

    @PostMapping("enroll")
    public String enrollMember() {
        return "enroll";
    }
}
