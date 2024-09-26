package com.francescomalagrino.webflux.Webflux_demo.controller;


import com.francescomalagrino.webflux.Webflux_demo.dto.EmployeeDto;
import com.francescomalagrino.webflux.Webflux_demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    //Build Reactive Save Employee REST API
    //Mono Single Value Flux multiple value so in this case will be Mono
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.save(employeeDto);
    }
}
