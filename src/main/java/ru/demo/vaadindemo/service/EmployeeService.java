package ru.demo.vaadindemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.demo.vaadindemo.domain.Employee;
import ru.demo.vaadindemo.dto.EmployeeDto;
import ru.demo.vaadindemo.mapper.EmployeeMapper;
import ru.demo.vaadindemo.repository.EmployeeRepository;

import java.util.List;

/**
 * EmployeeService.
 *
 * @author Pavel_Zhitlov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> getEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::mapToDto)
                .toList();
    }

    public List<EmployeeDto> getEmployeeListByName(String nameLike) {
        return employeeRepository.findAllByNameStartsWithIgnoreCase(nameLike).stream()
                .map(employeeMapper::mapToDto)
                .toList();
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        log.debug("saveEmployee() - start: employeeDto = {}", employeeDto);
        Employee employee = employeeMapper.mapToEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto savedEmployeeDto = employeeMapper.mapToDto(savedEmployee);
        log.debug("saveEmployee() - end: savedEmployeeDto = {}", savedEmployeeDto);
        return savedEmployeeDto;
    }

    public void deleteEmployee(EmployeeDto employeeDto) {
        employeeRepository.findById(employeeDto.getId())
                .ifPresent(employeeRepository::delete);
    }
}
