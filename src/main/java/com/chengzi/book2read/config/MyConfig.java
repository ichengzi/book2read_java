package com.chengzi.book2read.config;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Configuration
@Slf4j
public class MyConfig {
    @Bean(name = "defaultDataStore")
    DatastoreService getDatastore() {
        return DatastoreServiceFactory.getDatastoreService();
    }
}
