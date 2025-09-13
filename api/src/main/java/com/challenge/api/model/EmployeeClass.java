package com.challenge.api.model;

import java.time.Instant;
import java.util.UUID;

// implementing all get/set methods for the employee class created from employee interface

public class EmployeeClass implements Employee {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private Integer salary;
    private String email;
    private String jobTitle;
    private Integer age;
    private String fullName;
    private Instant contractHireDate;
    private Instant contractTerminationDate;

    public EmployeeClass() {}

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Integer getSalary() {
        return salary;
    }

    @Override
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        if (fullName == null) {

            String fn = this.firstName == null ? "" : this.firstName.trim();
            String ln = this.lastName == null ? "" : this.lastName.trim();

            this.fullName = (fn + " " + ln).trim();

        } else {

            this.fullName = fullName;
        }
    }

    @Override
    public Instant getContractHireDate() {
        return contractHireDate;
    }

    @Override
    public void setContractHireDate(Instant date) {
        this.contractHireDate = date;
    }

    @Override
    public Instant getContractTerminationDate() {
        return contractTerminationDate;
    }

    @Override
    public void setContractTerminationDate(Instant date) {
        this.contractTerminationDate = date;
    }
}
