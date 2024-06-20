package ru.demo.vaadindemo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.demo.vaadindemo.domain.Employee;
import ru.demo.vaadindemo.repository.EmployeeRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class VaadinDemoApplication implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(VaadinDemoApplication.class, args);
    }

    /**
     * Метод выполняется один раз при запуске приложения
     */
    @Override
    public void run(String... args) throws Exception {
        // Добавляем в БД сотрудников, если их нет
        if(employeeRepository.findByPhone("79371112233").isEmpty()) {
            employeeRepository.save(new Employee()
                    .setName("Paul")
                    .setPhone("79371112233"));
        }
        if(employeeRepository.findByPhone("79372223344").isEmpty()) {
            employeeRepository.save(new Employee()
                    .setName("Alex")
                    .setPhone("79372223344"));
        }
    }
}
