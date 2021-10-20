package ru.job4j.auth.service;

import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Iterable<Employee> getAll() {
        return repository.findAll();
    }

    public Employee save(Employee e) {
        return repository.save(e);
    }

    public void delete(int id) {
        Employee e = new Employee();
        e.setId(id);
        repository.delete(e);
    }
}
