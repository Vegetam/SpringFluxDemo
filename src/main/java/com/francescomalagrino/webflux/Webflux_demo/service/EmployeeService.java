package com.francescomalagrino.webflux.Webflux_demo.service;

import com.francescomalagrino.webflux.Webflux_demo.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<EmployeeDto> save(EmployeeDto employeeDto);
}
