package ru.demo.vaadindemo.mapper;

import org.mapstruct.Mapper;
import ru.demo.vaadindemo.domain.Employee;
import ru.demo.vaadindemo.dto.EmployeeDto;

/**
 * EmployeeMapper.
 *
 * @author Pavel_Zhitlov
 */
@Mapper
public interface EmployeeMapper {

    EmployeeDto mapToDto(Employee entity);

    Employee mapToEntity(EmployeeDto dto);
}
