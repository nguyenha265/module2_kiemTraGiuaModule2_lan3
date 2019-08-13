package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {
    Page<Department> findAll(Pageable pageable);

    Department findById(Long id);

    void save(Department department);

    void delete(Long id ,Pageable pageable);

    Page<Department>findAllByNameStartsWith(String name, Pageable pageable);



}
