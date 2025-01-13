package com.dbperu.dbinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DbinventoryApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DbinventoryApplication.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DbinventoryApplication.class);
    }
}
