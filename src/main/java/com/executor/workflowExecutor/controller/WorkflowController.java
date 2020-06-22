package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.components.utility.ExecutionHelper;
import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.service.ExecutionService;
import com.executor.workflowExecutor.service.WorkflowService;
import com.executor.workflowExecutor.components.utility.OutputFormatter;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class WorkflowController {
    @Autowired  WorkflowService workflowService;

    @Autowired  OutputFormatter outputFormatter;

    @Autowired  ExecutionService executionService;

    @GetMapping("/workflow/{name}")
    public String getWorkflowDetail(@PathVariable("name") String workflowName,
                                  Model model)
    {
        Workflow inspectedWorkflow=workflowService.findByName(workflowName);
        /*
            inspected workflow contains status and name of workflow which are used for front end rendering
         */
        model.addAttribute("workflow",inspectedWorkflow);

        List<Triple<LocalDateTime, TaskInfo,String>> formattedResult = outputFormatter.formatResult(inspectedWorkflow.getResult());
        Map<Integer,LocalDateTime> triggerTimeMap=outputFormatter.createTriggerTimeMap(inspectedWorkflow.getTriggers());
        model.addAttribute("formattedResult", formattedResult);
        model.addAttribute("triggerTimeMap",triggerTimeMap);
        return "workflowDetails";
    }


    @GetMapping("/triggerExecute/{name}")
    public String resumeTriggerWait(@PathVariable("name") String workflowName) throws InterruptedException {
        executionService.resumeExecution(workflowName);
        return "redirect:/workflow/"+workflowName;
    }


}
