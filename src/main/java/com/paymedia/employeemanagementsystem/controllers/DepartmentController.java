package com.paymedia.employeemanagementsystem.controllers;

import com.paymedia.employeemanagementsystem.models.Department;
import com.paymedia.employeemanagementsystem.models.Employee;
import com.paymedia.employeemanagementsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<Department> getDepartmentById(@RequestParam("departmentId") long departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        if (department == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(department);
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.updateDepartment(department);

        if (savedDepartment != null) {
            return ResponseEntity.ok(savedDepartment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping(path = "/{departmentId}")
    public ResponseEntity<Department> deleteDepartmentById(@PathVariable("departmentId") long departmentId) {
        boolean isDeleted = departmentService.deleteDepartmentById(departmentId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Department>> getAllDepartments(
            @RequestParam(required = false, defaultValue = "") String searchName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Department> departments = departmentService.getDepartmentsWithSearchAndPagination(searchName, pageable);

        return ResponseEntity.ok(departments);
    }
}
