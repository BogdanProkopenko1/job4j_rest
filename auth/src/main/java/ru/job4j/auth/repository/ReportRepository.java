package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Report;

public interface ReportRepository extends CrudRepository<Report, Integer> {
}
