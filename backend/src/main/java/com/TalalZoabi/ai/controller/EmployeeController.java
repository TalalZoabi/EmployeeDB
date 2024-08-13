package com.TalalZoabi.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TalalZoabi.ai.model.Employee;
import com.TalalZoabi.ai.service.EmployeeService;
import com.TalalZoabi.ai.service.ConversationStartersService;

import com.TalalZoabi.ai.exception.EmployeeNotFoundException;
import com.TalalZoabi.ai.exception.EmailAlreadyExistsException;
import com.TalalZoabi.ai.exception.WikiDataNotFoundException;


import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    private final EmployeeService employeeService;
    private final ConversationStartersService conversationStartersService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ConversationStartersService conversationStartersService) {
        this.employeeService = employeeService;
        this.conversationStartersService = conversationStartersService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployee(id);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get_wiki_data")
    public ResponseEntity<?> fetchWikiData(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployee(id);
            String wikiData = conversationStartersService.getWikiData(employee);
            return ResponseEntity.ok(wikiData);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (WikiDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching wiki data." + e.getMessage());
        }
    }

    @GetMapping("/{id}/get_conversation_starters")
    public ResponseEntity<?> getConversationStarters(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployee(id);
            List<String> conversationStarters = conversationStartersService.getConversationStarters(employee);
            return ResponseEntity.ok(conversationStarters);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (WikiDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching conversation starters." + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> registerNewEmployee(@Valid @RequestBody Employee employee) {
        try {
            Employee newEmployee = employeeService.addNewEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering the employee.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the employee.");
        }
    }
}
