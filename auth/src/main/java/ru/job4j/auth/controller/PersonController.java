package ru.job4j.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.PersonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    public PersonController(final PersonService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.service.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> person = this.service.findById(id);
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<Person>(
                this.service.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.service.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.service.delete(person);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employeeId/{id}")
    public List<Person> findAllByEmployeeId(@PathVariable int id) {
        return StreamSupport.stream(
                this.service.findAllByEmployeeId(id).spliterator(), false
        ).collect(Collectors.toList());
    }

    @DeleteMapping("/employeeId/{id}")
    public ResponseEntity<Void> deleteEmplPerson(@PathVariable int id) {
        service.deleteAllByEmployeeId(id);
        return ResponseEntity.ok().build();
    }
}
