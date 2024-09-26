package com.francescomalagrino.webflux.Webflux_demo.service.impl;

import com.francescomalagrino.webflux.Webflux_demo.dto.EmployeeDto;
import com.francescomalagrino.webflux.Webflux_demo.entity.Employee;
import com.francescomalagrino.webflux.Webflux_demo.mapper.EmployeeMapper;
import com.francescomalagrino.webflux.Webflux_demo.repository.EmployeeRepository;
import com.francescomalagrino.webflux.Webflux_demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> save(EmployeeDto employeeDto) {
        //convert EmployeeDTO into Employee Entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> saveEmployee = employeeRepository.save(employee);
        return saveEmployee.map((employeeEntty) -> EmployeeMapper.mapToEmployeeDto(employeeEntty));
    }

    @Override
    public Mono<EmployeeDto> getEmployeeById(String employeeId) {
       Mono<Employee> savedEmployee =  employeeRepository.findById(employeeId);
        return savedEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }
}
