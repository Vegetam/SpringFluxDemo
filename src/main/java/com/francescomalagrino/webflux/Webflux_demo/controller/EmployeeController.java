package com.francescomalagrino.webflux.Webflux_demo.controller;


import com.francescomalagrino.webflux.Webflux_demo.dto.EmployeeDto;
import com.francescomalagrino.webflux.Webflux_demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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


    //Build Reactive Get Employee rest API
    @GetMapping("{employeeId}")
    public Mono<EmployeeDto> getEmployeeById(@PathVariable String employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    //Build Reactive Update Employee REST API
    @PutMapping("{employeeId}")
    public Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable("employeeId") String employeeId) {
        return employeeService.updateEmployee(employeeDto, employeeId);
    }

    //Build Delete Employee REST API
    @DeleteMapping("{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("employeeId") String employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
