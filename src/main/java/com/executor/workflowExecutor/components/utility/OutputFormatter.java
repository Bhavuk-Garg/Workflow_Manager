package com.executor.workflowExecutor.components.utility;

import org.apache.commons.lang3.tuple.Triple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OutputFormatter {

    public static List<Triple<LocalDateTime , Integer,String>> formatResult(String res) {
        List<Triple<LocalDateTime, Integer, String>> formatResult=new ArrayList<>();
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
                formatResult.add(Triple.of(LocalDateTime.parse(info[0],dtf),Integer.parseInt(info[1]),outputGenerated));
        }
        return formatResult;
    }

}
