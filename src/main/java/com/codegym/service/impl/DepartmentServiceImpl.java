package com.codegym.service.impl;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeFile;
import com.codegym.repository.DepartmentRepository;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.findOne(id);
    }

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void delete(Long id , Pageable pageable) {
       Department department = departmentRepository.findOne(id);
       Page<Employee> employees = employeeRepository.findAllByDepartment(department,pageable);
       for (Employee employee : employees){
           employee.setDepartment(null);
           employeeRepository.save(employee);
       }
        departmentRepository.delete(id);
    }

    @Override
    public Page<Department> findAllByNameStartsWith(String name, Pageable pageable) {
        return departmentRepository.findAllByNameStartsWith(name,pageable);
    }


}
