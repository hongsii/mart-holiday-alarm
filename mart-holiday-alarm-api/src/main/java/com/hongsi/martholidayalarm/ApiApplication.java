package com.hongsi.martholidayalarm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaAuditing
public class ApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApiApplication.class)
                .run(args);
        printPropertySources(context);
    }

    private static void printPropertySources(ConfigurableApplicationContext context) {
        context.getEnvironment()
                .getPropertySources()
                .forEach(propertySource -> log.info("{} : {}", propertySource.getName(), propertySource.getSource()));
    }
}
