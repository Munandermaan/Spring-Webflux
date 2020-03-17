package com.spring.service;

import com.spring.couchbase.EmployeeRepository;
import com.spring.exception.NotFoundException;
import com.spring.model.EmployeeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private static Map<String, EmployeeInfo> employeeRepo = new HashMap<>();
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity deleteEmployee(String id) {
        if (!employeeRepo.containsKey(id)) {
            throw new NotFoundException("Employee is not found");
        } else {
            employeeRepo.remove(id);
            LOGGER.info("Employee deleted for id " + id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity addEmployee(EmployeeInfo employeeInfo) {
        LOGGER.info("Adding employee for request " + employeeInfo);
        employeeRepo.put(employeeInfo.getId(), employeeInfo);

        return new ResponseEntity<>("Employee is added successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity getEmployee() {
        LOGGER.info("Getting the Employees");
        return new ResponseEntity<>(employeeRepo.values(), HttpStatus.OK);
    }

    @Override
    public Mono<ResponseEntity> addEmployeeViaFlux(EmployeeInfo employeeInfo) {
        LOGGER.info("Adding employee for request " + employeeInfo);
        employeeRepo.put(employeeInfo.getId(), employeeInfo);
        return Mono.just(new ResponseEntity<>("Employee is added successfully", HttpStatus.CREATED));
    }

    @Override
    public Flux<EmployeeInfo> getEmployeeViaFlux() {
        LOGGER.info("Getting the details of employees via flux");
        return Flux.fromIterable(employeeRepo.values());
    }

    @Override
    public Mono<String> deleteEmployeeViaFlux(String id) {
        if (!employeeRepo.containsKey(id)) {
            return Mono.just("Employee is not found");
        } else {
            employeeRepo.remove(id);
            LOGGER.info("Employee deleted for id " + id);
            return Mono.just("Employee deleted successfully");
        }
    }

    @Override
    public Mono<EmployeeInfo> getEmployeeViaId(String id) {
        LOGGER.info("Getting the details of employees via flux");
        return Mono.just(employeeRepo.get(id)).onErrorStop();
    }

    @Override
    public Mono<Object> addEmployeeViaCouchbase(EmployeeInfo employeeInfo) {
        LOGGER.info("Adding employee via couchbase");
        return employeeRepository.existsById(employeeInfo.getId()).map(result -> {
            if (result) {
                return (new ResponseEntity<>("Employee is already exist", HttpStatus.CONFLICT));
            } else {
                return employeeRepository.save(employeeInfo).doOnError(throwable -> throwable.getMessage())
                        .thenReturn(new ResponseEntity<>("Employee is added successfully", HttpStatus.CREATED));
            }
        });

    }

    @Override
    public Flux<EmployeeInfo> getEmployeeViaCouchbase() {
        LOGGER.info("Getting the details of employees via flux");
        return employeeRepository.findAll().doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<Object> deleteEmployeeViaCouchbase(String id) {
        LOGGER.info("Adding employee via couchbase");
        return employeeRepository.existsById(id).map(result -> {
            if (!result) {
                return (new ResponseEntity<>("Employee is not found", HttpStatus.NOT_FOUND));
            } else {
                return employeeRepository.deleteById(id).doOnError(throwable -> throwable.getMessage())
                        .thenReturn(new ResponseEntity<>("Employee is deleted successfully", HttpStatus.OK));
            }
        });
    }

    @Override
    public Mono<EmployeeInfo> getEmployeeViaIdCouchBase(String id) {
        LOGGER.info("Getting the details of employees via Couchbase");
        return employeeRepository.findById(id);

    }

    @Override
    public Mono<EmployeeInfo> getEmployeeViaNAme(String name) {
       return employeeRepository.getEmployeeDetailViaName(name);
    }
}
