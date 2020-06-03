package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.database.model.Workflow;
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

@Controller
public class WorkflowController {
    @Autowired
    WorkflowService workflowService;

    @Autowired
    OutputFormatter outputFormatter;

    @GetMapping("/workflow/{name}")
    public String getWorkflowDetail(@PathVariable("name") String workflowName,
                                  Model model)
    {
        Workflow inspectedWorkflow=workflowService.findByName(workflowName);
        model.addAttribute("workflow",inspectedWorkflow);
        model.addAttribute("workflowName",workflowName);

        List<Triple<LocalDateTime, TaskInfo,String>> formattedResult = outputFormatter.formatResult(inspectedWorkflow.getResult());
        model.addAttribute("formattedResult", formattedResult);

        return "workflowDetails";
    }


}
