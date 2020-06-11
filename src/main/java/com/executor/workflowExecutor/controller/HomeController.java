package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.service.ExecutionService;
import com.executor.workflowExecutor.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired ExecutionService executionService;
    @Autowired WorkflowService workflowService;

    @GetMapping(value={"/","/home"})
    public String getHomepage(Model model,@RequestParam(defaultValue = "") String searchWorkflow){

       model.addAttribute("savedWorkflows",workflowService.findByNameLike(searchWorkflow));
        return "homePage";
    }

    @PostMapping(value={"/","/home"})
    public String postWorkflow(@RequestParam("name") String inputWorkflowName)  {
       executionService.startExecution(inputWorkflowName);
        return "redirect:/home";
    }

}