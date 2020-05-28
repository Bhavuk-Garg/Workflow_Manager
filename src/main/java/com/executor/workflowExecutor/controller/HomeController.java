package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    WorkflowService workflowService;
    @Autowired
    ApplicationContext ctx;

    @GetMapping(value={"/","/home"})
    public String getHomepage(Model model,@RequestParam(defaultValue = "") String searchWorkflow){

       model.addAttribute("savedWorkflows",workflowService.findByNameLike(searchWorkflow));
        return "homePage";
    }

    @PostMapping(value={"/","/home"})
    public String postWorkflow(@RequestParam("name") String inputWorkflowName)  {
        Workflow inputWorkflow=new Workflow();
        inputWorkflow.setName(inputWorkflowName);
        /*
        *   Create a default entry with Waiting Status
         */
        workflowService.saveWorkflow(inputWorkflow);
        /*
        *   Asynchronous call to execute method
         */

        workflowService.executeWorkflow(inputWorkflow);
        return "redirect:/home";
    }

}