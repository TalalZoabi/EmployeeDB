package com.TalalZoabi.ai.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import com.TalalZoabi.ai.exception.EmployeeNotFoundException;
import com.TalalZoabi.ai.exception.EmailAlreadyExistsException;
import com.TalalZoabi.ai.model.Employee;
import com.TalalZoabi.ai.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id " + employeeId + " does not exist");
        }
        return optionalEmployee.get();
    }

    public Employee getEmployeeByEmail(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);      
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with email " + email + " does not exist");
        }
        return optionalEmployee.get();
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee addNewEmployee(Employee newEmployee) {
        boolean emailExists = employeeRepository.existsByEmail(newEmployee.getEmail());
        if (emailExists) {
            throw new EmailAlreadyExistsException("Email " + newEmployee.getEmail() + " is already in use");
        }
        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    public Employee updateEmployee(Long employeeId, Employee updatedEmployee) {
        Optional<Employee> optionalCurrentEmployee = employeeRepository.findById(employeeId);
        if (optionalCurrentEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id " + employeeId + " does not exist");
        }

        Employee currentEmployee = optionalCurrentEmployee.get();

        Optional<Employee> optionalEmployeeWithEmail = employeeRepository.findByEmail(updatedEmployee.getEmail());
        if (optionalEmployeeWithEmail.isPresent() && !optionalEmployeeWithEmail.get().getId().equals(employeeId)) {
            throw new EmailAlreadyExistsException("Email " + updatedEmployee.getEmail() + " is already in use");
        }

        currentEmployee.setFirstName(updatedEmployee.getFirstName());
        currentEmployee.setLastName(updatedEmployee.getLastName());
        currentEmployee.setEmail(updatedEmployee.getEmail());
        currentEmployee.setFavoriteWikiPage(updatedEmployee.getFavoriteWikiPage());

        employeeRepository.save(currentEmployee);
        return currentEmployee;
    }

    public void deleteEmployee(Long employeeId) {
        boolean employeeExists = employeeRepository.existsById(employeeId);
        if (!employeeExists) {
            throw new EmployeeNotFoundException("Employee with id " + employeeId + " does not exist");
        }
        employeeRepository.deleteById(employeeId);
    }
}
