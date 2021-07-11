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
        /*// Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.22) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_22);

        // Specify the source where the template files come from. Here I set a
        // plain directory for it, but non-file-system sources are possible too:
        cfg.setDirectoryForTemplateLoading(new File("/WEB-INF/views/ftl/"));

        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;*/

        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/ftl/");
        return freeMarkerConfigurer;
    }
}
