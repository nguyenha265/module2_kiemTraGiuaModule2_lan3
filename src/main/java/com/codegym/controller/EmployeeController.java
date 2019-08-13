package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeFile;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@PropertySource("classpath:upload.properties")
public class EmployeeController {
    @Autowired
    Environment env;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("departments")
    public Page<Department> findAll(Pageable pageable) {
        return departmentService.findAll(pageable);
    }

    @GetMapping("/employee")
    public ModelAndView viewListEmployee(@PageableDefault(size = 2,sort = "salary",direction = Sort.Direction.DESC) Pageable pageable ) {
        Page<Employee> employees = employeeService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping("/create-employee")
    public ModelAndView createEmployeeFrom() {
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee", new Employee());
        return modelAndView;
    }

    @PostMapping("/save-employee")
    public ModelAndView createEmployee(@ModelAttribute("employee") EmployeeFile employeeFile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/employee/create");
            modelAndView.addObject("errorMessage", bindingResult.getAllErrors());
            return modelAndView;
        }
        MultipartFile multipartFile = employeeFile.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(employeeFile.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Employee employeeObject = new Employee(employeeFile.getName(), employeeFile.getBirthDate(), employeeFile.getAddress(), fileName, employeeFile.getSalary(),employeeFile.getDepartment());
        employeeService.save(employeeObject);
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee", new Employee());
        modelAndView.addObject("message", "Create New success");
        return modelAndView;
    }

    @GetMapping("/view-employee/{id}")
    public ModelAndView viewEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/employee/view");
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    @GetMapping("/edit-employee/{id}")
    public ModelAndView editEmployeeFrom(@PathVariable("id") Long id) {
        Employee employee = employeeService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    @PostMapping("/edit-employee")
    public ModelAndView editEmployee(@ModelAttribute("employee") EmployeeFile employeeFile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("errorMessage", bindingResult.getAllErrors());
            return modelAndView;
        }
        MultipartFile multipartFile = employeeFile.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(employeeFile.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Employee employeeObject = new Employee(employeeFile.getName(), employeeFile.getBirthDate(), employeeFile.getAddress(), fileName, employeeFile.getSalary(),employeeFile.getDepartment());

        employeeService.save(employeeObject);
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("employee", new Employee());
        modelAndView.addObject("message", "Update Employee Success");
        return modelAndView;
    }

    @GetMapping("/delete-employee/{id}")
    public ModelAndView deleteFromEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/employee/delete");
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    @PostMapping("/delete-employee")
    public ModelAndView deleteEmployee(@ModelAttribute("employee") Employee employee, BindingResult bindingResult, Pageable pageable) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("errorMessage", bindingResult.getAllErrors());
            return modelAndView;
        }
        employeeService.delete(employee.getId());
        Page<Employee> employees = employeeService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees", employees);
        modelAndView.addObject("message", "Delete Employee Success");
        return modelAndView;
    }

    @PostMapping("/employees-searchName")
    public ModelAndView searchByNameEimployee(@RequestParam("searchName") Optional<String> searchName, @PageableDefault(size = 2) Pageable pageable) {
       Page<Employee> employees = employeeService.findAllByNameStartsWith(searchName.get(),pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }
    @GetMapping("/gradualWageArrangement")
    public ModelAndView gradualWageArrangement(@PageableDefault(size = 2) Pageable pageable){
        Page<Employee> employees = employeeService.findByOrderBySalaryAsc(pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }
}
