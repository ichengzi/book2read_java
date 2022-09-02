package com.chengzi.book2read.config;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

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
    public DatastoreService getDatastore() {
        return DatastoreServiceFactory.getDatastoreService();
    }

    @Bean(name = "memCache")
    public Cache getMemCache() {
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            return cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
            log.error("getMemCache error", e);
            return null;
        }
    }

    @Bean(name = "freeMarkerConfig")
    @SneakyThrows
    public FreeMarkerConfigurer getFreeMarkerConfiguration() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/ftl/");
        return freeMarkerConfigurer;
    }
}
