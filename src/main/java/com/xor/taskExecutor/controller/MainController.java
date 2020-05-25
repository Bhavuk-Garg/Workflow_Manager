package com.xor.taskExecutor.controller;

import com.xor.taskExecutor.database.model.Workflow;
import com.xor.taskExecutor.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
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
    public String postWorkflow(@RequestParam("name") String inputWorkflowName) throws IllegalAccessException {
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