package com.paymedia.employeemanagementsystem.service;

import com.paymedia.employeemanagementsystem.models.Employee;
import com.paymedia.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findEmployeeById(employeeId).orElse(null);
    }

    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setDepartment(employee.getDepartment());
            return employeeRepository.save(existingEmployee);
        }
        return null;
    }

    public boolean deleteEmployeeById(long employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId).orElse(null);
        employeeRepository.delete(existingEmployee);
        return false;
    }

    public Page<Employee> getEmployeesByDepartmentWithSearchAndPagination(
            String departmentName, String searchName, Pageable pageable) {
        return employeeRepository.findByDepartmentName(departmentName, searchName, pageable);
    }
}
