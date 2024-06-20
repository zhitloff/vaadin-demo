package ru.demo.vaadindemo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * EmployeeDto.
 *
 * @author Pavel_Zhitlov
 */
@Data
@Accessors(chain = true)
public class EmployeeDto {

    private Long id;

    private String name;

    private String phone;
}
