package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EmployeeController {

    @Autowired
    private RestTemplate rest;
    private EmployeeService service;
    private static final String API1 = "http://localhost:8080/person/employeeId/{id}";
    private static final String API2 = "http://localhost:8080/person/";

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return StreamSupport.stream(
                        this.service.getAll().spliterator(), false
                )
                .map(employee -> {
                    rest.exchange(
                            "http://localhost:8080/person/employeeId/" + employee.getId(),
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                            }
                    ).getBody().forEach(employee::addPerson);
                    return employee;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee rsl = service.save(employee);
        for (Person p : rsl.getAccounts()) {
            p.setEmployeeId(rsl.getId());
            rest.postForObject(API2, p, Person.class);
        }
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        Employee rsl = service.save(employee);
        for (Person p : rsl.getAccounts()) {
            p.setEmployeeId(rsl.getId());
            rest.postForObject(API2, p, Person.class);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        rest.delete(API1, id);
        return ResponseEntity.ok().build();
    }
}
