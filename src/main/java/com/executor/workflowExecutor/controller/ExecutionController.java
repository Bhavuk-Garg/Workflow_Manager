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

    @GetMapping("/triggerExecute/{name}")
    public String resumeTriggerWait(@PathVariable("name") String workflowName) throws InterruptedException {
        executionService.resumeExecution(workflowName);
        return "redirect:/workflow/"+workflowName;
    }
}

