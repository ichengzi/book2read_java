package com.chengzi.book2read.config;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import java.util.Collections;

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

    @Bean(name = "memCache")
    Cache getMemCache() {
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            return cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
            log.error("getMemCache error", e);
            return null;
        }
    }
}
