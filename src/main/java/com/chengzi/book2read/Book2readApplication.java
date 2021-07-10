package com.chengzi.book2read;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author chengzi
 */
@SpringBootApplication
public class Book2readApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Book2readApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Book2readApplication.class, args);
    }
}
