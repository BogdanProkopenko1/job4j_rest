package ru.job4j.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.auth.domain.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("select p from Person p where p.employeeId = :id")
    List<Person> findPersonByEmployeeId(@Param("id") int id);

    @Query("delete from Person where employeeId = :id")
    void deletePersonByEmployeeId(@Param("id") int id);
}
