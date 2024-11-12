package com.paymedia.employeemanagementsystem.service;

import com.paymedia.employeemanagementsystem.models.Department;
import com.paymedia.employeemanagementsystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findDepartmentById(departmentId).orElse(null);
    }

    public Department updateDepartment(Department department) {
        Department existingDepartment = departmentRepository.findDepartmentById(department.getId()).orElse(null);
        if (existingDepartment != null) {
            existingDepartment.setName(department.getName());
            existingDepartment.setEmployees(department.getEmployees());
            return departmentRepository.save(existingDepartment);
        }
        return null;
    }

    public boolean deleteDepartmentById(Long departmentId) {
        Department existingDepartment = departmentRepository.findDepartmentById(departmentId).orElse(null);
        if (existingDepartment != null) {
            departmentRepository.delete(existingDepartment);
            return true;
        }
        return false;
    }

    public Page<Department> getDepartmentsWithSearchAndPagination(String searchName, Pageable pageable) {
        return departmentRepository.findByNameContaining(searchName, pageable);
    }
}
