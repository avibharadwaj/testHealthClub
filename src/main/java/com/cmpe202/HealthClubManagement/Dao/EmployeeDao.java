package com.cmpe202.HealthClubManagement.Dao;

import com.cmpe202.HealthClubManagement.Model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {
}
