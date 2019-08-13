package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeFile;
import com.codegym.repository.DepartmentRepository;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/department")
    public ModelAndView viewListdepartment(@PageableDefault(size = 2) Pageable pageable) {
        Page<Department> departments = departmentService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/department/list");
        modelAndView.addObject("departments", departments);
        return modelAndView;
    }

    @GetMapping("/create-department")
    public ModelAndView createdepartmentFrom() {
        ModelAndView modelAndView = new ModelAndView("/department/create");
        modelAndView.addObject("department", new Department());
        return modelAndView;
    }

    @PostMapping("/save-department")
    public ModelAndView savedepartment(@ModelAttribute("department") Department department) {
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("/department/create");
        modelAndView.addObject("department", new Department());
        modelAndView.addObject("message", "Create New Department success");
        return modelAndView;
    }

    @GetMapping("/view-department/{id}")
    public ModelAndView viewDepartment(@PathVariable("id") Long id,Pageable pageable) {
        Department department = departmentService.findById(id);
        Page<Employee> employees = employeeService.findAllByDepartment(department,pageable);
        ModelAndView modelAndView = new ModelAndView("/department/view");
        modelAndView.addObject("department", department);
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping("/edit-department/{id}")
    public ModelAndView editDepartmentForm(@PathVariable("id") Long id) {
        Department department = departmentService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/department/edit");
        modelAndView.addObject("department", department);
        return modelAndView;
    }

    @PostMapping("/edit-department")
    public ModelAndView editDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("/department/edit");
        modelAndView.addObject("department", new Department());
        modelAndView.addObject("message", "Edit Department success");
        return modelAndView;
    }

    @GetMapping("/delete-department/{id}")
    public ModelAndView deleteDepartmentForm(@PathVariable("id") Long id) {
        Department department = departmentService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/department/delete");
        modelAndView.addObject("department", department);
        return modelAndView;
    }

    @PostMapping("/delete-department")
    public ModelAndView deleteDepartment(@ModelAttribute Department department, Pageable pageable) {
        departmentService.delete(department.getId());
        Page<Department> departments = departmentService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/department/list");
        modelAndView.addObject("departments", departments);
        modelAndView.addObject("message", "Delete Department success");
        return modelAndView;
    }

    @PostMapping("/Search-NameDepartment")
    public ModelAndView searchNameDepartment(@RequestParam("searchName") Optional<String> searchName, @PageableDefault(size = 2) Pageable pageable) {
        Page<Department> departments = departmentService.findAllByNameStartsWith(searchName.get(), pageable);
        ModelAndView modelAndView = new ModelAndView("/department/list");
        modelAndView.addObject("departments", departments);
        return modelAndView;
    }

}




































