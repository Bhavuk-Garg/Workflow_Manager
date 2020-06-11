package com.executor.workflowExecutor.components.utility;

import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.service.TaskInfoService;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OutputFormatter {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public List<Triple<LocalDateTime , TaskInfo,String>> formatResult(String res) {
        List<Triple<LocalDateTime, TaskInfo, String>> formattedResult=new ArrayList<>();
        if(res==null || res.length()==0)   return formattedResult;
        String []executedTaskStatusList=res.split(";");

        for(String curExecutedTaskStatus: executedTaskStatusList)
        {
            /*
                [time,id,name,type,output]
            *  curExecutedTaskStatus have values in format [completionTime,id,output]
            */

            String []info=curExecutedTaskStatus.split(",",-1);

            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setId(Integer.parseInt(info[1]));
            taskInfo.setName(info[2]);
            taskInfo.setType( Status.valueOf(info[3]));

            formattedResult.add(Triple.of(LocalDateTime.parse(info[0],dtf), taskInfo,info[4]));
        }
        return formattedResult;
    }

    public Map<Integer, LocalDateTime> createTriggerTimeMap(String triggers) {
        Map<Integer,LocalDateTime> map=new HashMap<>();
        if(triggers==null || triggers.length()==0)  return map;
        String []triggerList=triggers.split(";");
        for(String trigger: triggerList){
            if(trigger.length()>0)
            {
                int id=Integer.parseInt(trigger.split(",")[0]);
                LocalDateTime time=LocalDateTime.parse(trigger.split(",")[1],dtf);
                map.put(id,time);
            }
        }
        return map;
    }
}
