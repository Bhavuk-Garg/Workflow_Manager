package com.executor.workflowExecutor.components.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:dropdownTasks.properties")
public class TaskNamesProvider {
    @Value("#{'${dropdownvalue.availableTaskNames}'.split(',')}")
    private List<String> allowedTaskNames;

    public List<String> getAllowedTaskNames() {
        return allowedTaskNames;
    }
}
