package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.repository.InMemoryEmployeeRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    private final InMemoryEmployeeRepository repo;

    public EmployeeService(InMemoryEmployeeRepository repo) {
        this.repo = repo;
    }

    public List<Employee> getAll() {
        return repo.findAll();
    }

    public Employee getById(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "uuid is required");
        }
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    // helper to check for duplicate emails
    public Optional<Employee> getByEmail(String email) {
        return repo.findByEmail(email);
    }

    public Employee create(Employee employee) {
        // error handling to check fields are properly inputted

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }

        if (employee.getFirstName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "firstName is required");
        }
        if (employee.getLastName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lastName is required");
        }
        if (employee.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is required");
        }

        if (!looksLikeEmail(employee.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email must be valid");
        }

        Integer salary = employee.getSalary();
        if (salary != null && salary < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "salary must be >= 0");
        }
        Integer age = employee.getAge();
        if (age != null && age < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "age must be >= 0");
        }

        if (employee.getContractHireDate() == null) {
            employee.setContractHireDate(Instant.now());
        }

        if (employee.getContractTerminationDate() != null
                && employee.getContractHireDate() != null
                && employee.getContractTerminationDate().isBefore(employee.getContractHireDate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "terminationDate cannot be before hireDate");
        }

        repo.findByEmail(employee.getEmail()).ifPresent(e -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        });

        employee.setFullName(null);

        return repo.save(employee);
    }

    private boolean looksLikeEmail(String s) {
        String v = s.trim();
        int at = v.indexOf('@');
        int dot = v.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < v.length() - 1;
    }
}
