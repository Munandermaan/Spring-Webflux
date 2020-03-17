package com.spring.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.List;

@Document
@EqualsAndHashCode
@Getter
@JsonDeserialize
@NoArgsConstructor
public final class EmployeeInfo {
    @Id
    private String id;
    @Field
    private String name;
    @Field
    private List<String> phone;
}
