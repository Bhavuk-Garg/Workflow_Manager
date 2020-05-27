package com.xor.taskExecutor.controller;

import com.xor.taskExecutor.database.model.Workflow;
import com.xor.taskExecutor.service.WorkflowService;
import com.xor.taskExecutor.util.OutputFormatter;
import com.xor.taskExecutor.util.Status;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WorkflowController {
    @Autowired
    WorkflowService workflowService;

    @GetMapping("/workflow/{name}")
    public String workflowDetails(@PathVariable String name,
                                  Model model)
    {
        Workflow inspectedWorkflow=workflowService.findByName(name);
        model.addAttribute("workflow",inspectedWorkflow);

        if(inspectedWorkflow.getStatus()!= Status.Waiting) {
            List<Triple<LocalDateTime, Integer,String>> formattedResult = OutputFormatter.formatResult(inspectedWorkflow.getResult());
            model.addAttribute("formattedResult", formattedResult);
        }

        return "workflowDetails";
    }


}
