package com.paymedia.employeemanagementsystem.repository;

import com.paymedia.employeemanagementsystem.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeById(Long id);

    @Query("SELECT e FROM Employee e WHERE e.department.name = :departmentName " +
            "AND (LOWER(e.firstName) LIKE LOWER(CONCAT('%', :searchName, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :searchName, '%')))")
    Page<Employee> findByDepartmentName(
            @Param("departmentName") String departmentName,
            @Param("searchName") String searchName,
            Pageable pageable);
}
