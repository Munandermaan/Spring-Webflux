package com.spring.service;

import com.spring.model.EmployeeInfo;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    ResponseEntity deleteEmployee(String id);

    ResponseEntity addEmployee(EmployeeInfo employeeInfo);

    ResponseEntity getEmployee();

    Mono<ResponseEntity> addEmployeeViaFlux(EmployeeInfo employeeInfo);

    Flux<EmployeeInfo> getEmployeeViaFlux();

    Mono<String> deleteEmployeeViaFlux(String id);

    Mono<EmployeeInfo> getEmployeeViaId(String id);

    Mono<Object> addEmployeeViaCouchbase(EmployeeInfo employeeInfo);

    Flux<EmployeeInfo> getEmployeeViaCouchbase();

    Mono<Object> deleteEmployeeViaCouchbase(String id);

    Mono<EmployeeInfo> getEmployeeViaIdCouchBase(String id);

    Mono<EmployeeInfo> getEmployeeViaNAme(String name);
}
