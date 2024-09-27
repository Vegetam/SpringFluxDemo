package com.francescomalagrino.webflux.Webflux_demo;

import com.francescomalagrino.webflux.Webflux_demo.dto.EmployeeDto;
import com.francescomalagrino.webflux.Webflux_demo.repository.EmployeeRepository;
import com.francescomalagrino.webflux.Webflux_demo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private EmployeeRepository employeeRepository;


    @BeforeEach
    public void before() {
        System.out.println("Before each test");
        employeeRepository.deleteAll().subscribe();
    }


    @Test
    public void testSaveEmployee() {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Francesco");
        employeeDto.setLastName("Malagrino");
        employeeDto.setEmail("email@email.com");

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }


    @Test
    public void testGetEmployeeById() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Francesco");
        employeeDto.setLastName("Malagrino");
        employeeDto.setEmail("email@email.com");

        EmployeeDto savedEmployee = employeeService.save(employeeDto).block();

        webTestClient.get().uri("/api/employees/{employeeId}", Collections.singletonMap("employeeId",savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }

    @Test
    public void testGetAllEmployees() {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Francesco");
        employeeDto.setLastName("Malagrino");
        employeeDto.setEmail("email@email.com");

        employeeService.save(employeeDto).block();

        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setFirstName("Francesco");
        employeeDto2.setLastName("Malagrino2");
        employeeDto2.setEmail("email2@email.com");

        employeeService.save(employeeDto2).block();

        EmployeeDto employeeDto3 = new EmployeeDto();
        employeeDto3.setFirstName("Francesco");
        employeeDto3.setLastName("Malagrino3");
        employeeDto3.setEmail("email3@email.com");

        employeeService.save(employeeDto3).block();


        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);

    }

    @Test
    public void testUpdateEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Francesco");
        employeeDto.setLastName("Malagrino");
        employeeDto.setEmail("email@email.com");

       EmployeeDto saveEmployee = employeeService.save(employeeDto).block();

        EmployeeDto updateEmployee = new EmployeeDto();
        updateEmployee.setFirstName("Francesco2");
        updateEmployee.setLastName("Malagrino2");
        updateEmployee.setEmail("email2@email.com");

        webTestClient.put().uri("/api/employees/{employeeId}", Collections.singletonMap("employeeId",saveEmployee.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateEmployee), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updateEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updateEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updateEmployee.getEmail());

}

            @Test
            public void testDeleteEmployee() {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setFirstName("Francesco");
                employeeDto.setLastName("Malagrino");
                employeeDto.setEmail("email@email.com");

                EmployeeDto saveEmployee = employeeService.save(employeeDto).block();


                webTestClient.delete().uri("/api/employees/{employeeId}", Collections.singletonMap("employeeId",saveEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
            }


}
