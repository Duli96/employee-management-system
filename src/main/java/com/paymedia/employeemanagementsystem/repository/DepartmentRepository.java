package com.paymedia.employeemanagementsystem.repository;

import com.paymedia.employeemanagementsystem.models.Department;
import com.paymedia.employeemanagementsystem.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findDepartmentById(Long id);

    @Query("SELECT d FROM Department d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :searchName, '%'))")
    Page<Department> findByNameContaining(@Param("searchName") String searchName, Pageable pageable);

}
