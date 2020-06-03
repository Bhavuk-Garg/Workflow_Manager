package com.executor.workflowExecutor.components.utility;

import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.service.TaskInfoService;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class OutputFormatter {
    @Autowired
    TaskInfoService taskInfoService;

    public List<Triple<LocalDateTime , TaskInfo,String>> formatResult(String res) {
        List<Triple<LocalDateTime, TaskInfo, String>> formattedResult=new ArrayList<>();
        if(res==null || res.length()==0)   return formattedResult;
        String []executedTaskStatusList=res.split(";");

        for(String curExecutedTaskStatus: executedTaskStatusList)
        {
            /*
            *  curExecutedTaskStatus have values in format [completionTime,id,output]
            */

            String []info=curExecutedTaskStatus.split(",");
            String outputGenerated="";
            if(info.length == 2)
            {
                /*
                *  for output [date,id, ] blank field represents default ouput
                *  split function will ignore the last field , so we are handling the case
                */
                outputGenerated="Default Output";
            }
            else
                outputGenerated=info[2];

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                TaskInfo taskInfo = taskInfoService.findById(Integer.parseInt(info[1]));
                formattedResult.add(Triple.of(LocalDateTime.parse(info[0],dtf), taskInfo,outputGenerated));
        }
        return formattedResult;
    }

}
