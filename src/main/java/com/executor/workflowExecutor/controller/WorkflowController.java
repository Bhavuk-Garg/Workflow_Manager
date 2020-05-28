package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.service.WorkflowService;
import com.executor.workflowExecutor.components.utility.OutputFormatter;
import com.executor.workflowExecutor.components.utility.Status;
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
    public String getWorkflowDetail(@PathVariable("name") String workflowName,
                                  Model model)
    {
        System.out.println("workflow controller");
        Workflow inspectedWorkflow=workflowService.findByName(workflowName);
        model.addAttribute("workflow",inspectedWorkflow);
        model.addAttribute("workflowName",workflowName);
        if(inspectedWorkflow.getStatus()!= Status.Waiting) {
            List<Triple<LocalDateTime, Integer,String>> formattedResult = OutputFormatter.formatResult(inspectedWorkflow.getResult());
            model.addAttribute("formattedResult", formattedResult);
        }

        return "workflowDetails";
    }


}
