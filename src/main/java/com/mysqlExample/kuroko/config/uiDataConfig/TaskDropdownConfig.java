package com.mysqlExample.kuroko.config.uiDataConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.List;

@Configuration
@PropertySource("classpath:dropdownTasks.properties")
public class TaskDropdownConfig {
    @Value("#{'${dropdownvalue.availableTaskNames}'.split(',')}")
    private List<String> allowedTaskNames;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public List<String> getAllowedTaskNames() {
        return allowedTaskNames;
    }
}
