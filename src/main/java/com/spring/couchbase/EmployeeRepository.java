package com.spring.couchbase;

import com.spring.model.EmployeeInfo;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@ViewIndexed(designDoc = "employeeInfo", viewName = "id")
public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeInfo, String> {

//    @Override
//    //@Query("select * from Employee;")
//    Flux<EmployeeInfo> findAll();
    @Query("Select Array e In phone where name = $name")
    Mono<EmployeeInfo> getEmployeeDetailViaName(@PathVariable("name") String name);
}
