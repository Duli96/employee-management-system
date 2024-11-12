package com.paymedia.employeemanagementsystem.controllers;

import com.paymedia.employeemanagementsystem.models.Employee;
import com.paymedia.employeemanagementsystem.service.EmployeeService;
import com.paymedia.employeemanagementsystem.utils.InputValidationUtil;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {

        try {
            InputValidationUtil.isEmailValid(employee.getEmail());
            InputValidationUtil.isNotBlank(employee.getFirstName(), "firstName");
            InputValidationUtil.isNotBlank(employee.getLastName(), "lastName");
            InputValidationUtil.isNotBlank(employee.getPhone(), "phone");

            Employee savedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathParam("employeeId") Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        try {
            InputValidationUtil.isEmailValid(employee.getEmail());
            InputValidationUtil.isNotBlank(employee.getFirstName(), "firstName");
            InputValidationUtil.isNotBlank(employee.getLastName(), "lastName");
            InputValidationUtil.isNotBlank(employee.getPhone(), "phone");
            Employee updateEmployee = employeeService.updateEmployee(employee);
            return ResponseEntity.ok(updateEmployee);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathParam("employeeId") long employeeId) {
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
