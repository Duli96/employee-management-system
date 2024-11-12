package com.paymedia.employeemanagementsystem.controllers;

import com.paymedia.employeemanagementsystem.models.Employee;
import com.paymedia.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam("employeeId") long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updateEmployee = employeeService.updateEmployee(employee);

        if (updateEmployee != null) {
            return ResponseEntity.ok(updateEmployee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("employeeId") long employeeId) {
        boolean isDeleted = employeeService.deleteEmployeeById(employeeId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getEmployeesByDepartmentWithSearchAndPagination(
            @RequestParam String departmentName,
            @RequestParam(required = false, defaultValue = "") String searchName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employee> employees = employeeService.getEmployeesByDepartmentWithSearchAndPagination(
                departmentName, searchName, pageable);

        return ResponseEntity.ok(employees);
    }
}
