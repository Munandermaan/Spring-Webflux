package com.spring.couchbase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.spring.couchbase"})
public class MyCouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${spring.couchbase.bucket.username}")
    private String bucketUsername;
    @Value("${spring.couchbase.bucket.password}")
    private String bucketUserPassword;
    @Value("${spring.couchbase.bucket.name}")
    private String bucketName;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList("localhost");
    }

    @Override
    protected String getBucketName() {
         return bucketName;
    }

    @Override
    protected String getBucketPassword() {
        return bucketUserPassword;
    }

    @Override
    protected String getUsername() {
        return bucketUsername;
    }
}
