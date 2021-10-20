package ru.job4j.auth.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String secondName;
    private long taxNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Person> accounts;
    private LocalDate hired;

    public static Employee of(String firstName, String secondName, long taxNumber) {
        Employee e = new Employee();
        e.firstName = firstName;
        e.secondName = secondName;
        e.taxNumber = taxNumber;
        e.accounts = new ArrayList<>();
        e.hired = LocalDate.now();
        return e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public long getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(long taxNumber) {
        this.taxNumber = taxNumber;
    }

    public List<Person> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public void setAccounts(List<Person> accounts) {
        this.accounts = accounts;
    }

    public LocalDate getHired() {
        return hired;
    }

    public void setHired(LocalDate hired) {
        this.hired = hired;
    }

    public void addPerson(Person person) {
        accounts.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
