package com.francescomalagrino.webflux.Webflux_demo.repository;
import com.francescomalagrino.webflux.Webflux_demo.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String>{

}
