package ru.demo.vaadindemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.demo.vaadindemo.domain.Employee;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeRepository.
 *
 * @author Pavel_Zhitlov
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByNameStartsWithIgnoreCase(String name);

    Optional<Employee> findByPhone(String phone);
}
