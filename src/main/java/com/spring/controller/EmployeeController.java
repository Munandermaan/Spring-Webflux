package com.spring.controller;

import com.spring.model.EmployeeInfo;
import com.spring.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        return employeeService.deleteEmployee(id);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Object> add(@RequestBody EmployeeInfo employeeInfo) {
        return employeeService.addEmployee(employeeInfo);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getEmployee() {
        return employeeService.getEmployee();
    }

    @PostMapping(value = "/employee")
    public Mono<ResponseEntity> addEmployee(@RequestBody EmployeeInfo employeeInfo) {
        return employeeService.addEmployeeViaFlux(employeeInfo);
    }

    @GetMapping(value = "/employee")
    public Flux<EmployeeInfo> getEmployeeViaFlux() {
        return employeeService.getEmployeeViaFlux();
    }

    @DeleteMapping(value = "/employee/{id}")
    public Mono<String> deleteViaFlux(@PathVariable("id") String id) {
        return employeeService.deleteEmployeeViaFlux(id);
    }

    @GetMapping(value = "/employee/{id}")
    public Mono<EmployeeInfo> getEmployee(@PathVariable("id") String id) {
        return employeeService.getEmployeeViaId(id);
    }

    @PostMapping(value = "/couchbase")
    public Mono<Object> addEmployeeViaCouchbase(@RequestBody EmployeeInfo employeeInfo) {
        return employeeService.addEmployeeViaCouchbase(employeeInfo);
    }

    @GetMapping(value = "/couchbase")
    public Flux<EmployeeInfo> getEmployeeViaCouchbase() {
        return employeeService.getEmployeeViaCouchbase();
    }

    @DeleteMapping(value = "/couchbase/{id}")
    public Mono<Object> deleteViaCouchbase(@PathVariable("id") String id) {
        return employeeService.deleteEmployeeViaCouchbase(id);
    }

    @GetMapping(value = "/couchbase/{id}")
    public Mono<EmployeeInfo> getEmployeeViaCouchbase(@PathVariable("id") String id) {
        return employeeService.getEmployeeViaIdCouchBase(id);
    }
    @GetMapping(value = "/couchbase/name/{name}")
    public Mono<EmployeeInfo> getEmployeeDetailViaCouchbase(@PathVariable("name") String name) {
        return employeeService.getEmployeeViaNAme(name);
    }
}