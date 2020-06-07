package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.service.ExecutionService;
import com.executor.workflowExecutor.service.WorkflowService;
import com.executor.workflowExecutor.service.WorkflowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExecutionController {
    @Autowired
    ExecutionService executionService;

    @Autowired
    WorkflowService workflowService;
    @GetMapping("/triggerExecute/{name}")
    public String resumeTriggerWait(@PathVariable("name") String workflowName) throws InterruptedException {
        Workflow workflow=workflowService.findByName(workflowName);
        executionService.setStauts(workflow, Status.NORMAL);
        executionService.resumeExecution(workflow,0);
        return "redirect:/workflow/"+workflowName;
    }

    @GetMapping("/timedExecute/{name}")
    public String resumeTimedWait(@PathVariable("name") String workflowName,
                                  @RequestParam int time) throws InterruptedException {
        Workflow workflow=workflowService.findByName(workflowName);
        executionService.setStauts(workflow, Status.NORMAL);
        executionService.resumeExecution(workflow,time*1000);
        return "redirect:/workflow/"+workflowName;
    }
}

