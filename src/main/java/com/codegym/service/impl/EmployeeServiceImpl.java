package com.codegym.service.impl;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeFile;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.delete(id);
    }

    @Override
    public Page<Employee> findAllByNameStartsWith(String name, Pageable pageable) {
        return employeeRepository.findAllByNameStartsWith(name,pageable);
    }

    @Override
    public Page<Employee> findByOrderBySalaryAsc(Pageable pageable) {
        return employeeRepository.findByOrderBySalaryAsc(pageable);
    }

    @Override
    public Page<Employee> findAllByDepartment(Department department,Pageable pageable) {
        return employeeRepository.findAllByDepartment(department,pageable);
    }

}
