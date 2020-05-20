package com.xor.taskExecutor.config.beanConfig;

import com.mysqlExample.kuroko.Task.*;
import com.mysqlExample.taskExecutor.Task.*;
import com.xor.taskExecutor.Task.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskBean {

    @Bean("printTask")
    public Task getPrintTask(){
        return new PrintTask();
    }

    @Bean("dataTask")
    public Task getDataTask(){
        return new DatabaseTask();
    }

    @Bean("conditionalTask")
    public Task getConditionalTask(){
        return new ConditionalTask();
    }
    @Bean("syncTask")
    public Task getSyncTask(){
        return new SyncTask();
    }
    @Bean("threadedTask")
    public Task getThreadedTask(){
        return new ThreadedTask();
    }

}
