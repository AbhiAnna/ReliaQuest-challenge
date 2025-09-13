package com.challenge.api.controller;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeClass;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Fill in the missing aspects of this Spring Web REST Controller. Don't forget to add a Service layer.
 */
@RestController
@RequestMapping("/api/v1/employee")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee models as necessary.
     * @return One or more Employees.
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAll();
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee model as necessary.
     * @param uuid Employee UUID
     * @return Requested Employee if exists
     */
    @GetMapping("/{uuid}")
    public Employee getEmployeeByUuid(@PathVariable UUID uuid) {
        return service.getById(uuid);
    }

    /**x
     * @implNote Need not be concerned with an actual persistence layer.
     * @param body Employee payload
     * @return Newly created Employee
     */
    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeClass body) {
        return service.create(body);
    }
}
