package com.challenge.api.repository;

import com.challenge.api.model.Employee;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

// Implemented using a Java map for storage, but in a full implementation extending jpaRepository would be a good option
// a lot of necessary functionality is automatically implemented in jpa and allows easy integration of a sql database

@Repository
public class InMemoryEmployeeRepository {

    // session "database" where all information is stored. UUID serves as a key and value is an Employee object
    private final Map<UUID, Employee> store = new ConcurrentHashMap<>();

    public List<Employee> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Employee> findById(UUID id) {
        return Optional.ofNullable(store.get(id)); // auto handles the case where id doesn't exist in the storage
    }

    public Optional<Employee> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return store.values().stream()
                .filter(e -> email.equalsIgnoreCase(e.getEmail()))
                .findFirst();
    }

    public Employee save(Employee employee) {
        if (employee.getUuid() == null) {
            employee.setUuid(UUID.randomUUID());
        }
        store.put(employee.getUuid(), employee);
        return employee;
    }
}
