package com.francescomalagrino.webflux.Webflux_demo.service.impl;

import com.francescomalagrino.webflux.Webflux_demo.dto.EmployeeDto;
import com.francescomalagrino.webflux.Webflux_demo.entity.Employee;
import com.francescomalagrino.webflux.Webflux_demo.mapper.EmployeeMapper;
import com.francescomalagrino.webflux.Webflux_demo.repository.EmployeeRepository;
import com.francescomalagrino.webflux.Webflux_demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
        return saveEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Mono<EmployeeDto> getEmployeeById(String employeeId) {
       Mono<Employee> savedEmployee =  employeeRepository.findById(employeeId);
        return savedEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {
        Flux<Employee> employeeFlux = employeeRepository.findAll();
        return employeeFlux
                .map(EmployeeMapper::mapToEmployeeDto)
                .switchIfEmpty(Flux.empty());

    }
}
