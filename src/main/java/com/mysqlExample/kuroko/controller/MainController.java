package com.mysqlExample.kuroko.controller;

import com.mysqlExample.kuroko.database.model.Workflow;
import com.mysqlExample.kuroko.database.repository.WorkflowRepository;
import com.mysqlExample.kuroko.service.TaskNameService;
import com.mysqlExample.kuroko.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    WorkflowService workflowService;

    @Autowired
    WorkflowRepository workflowRepository;

    @GetMapping(value={"/","/home"})
    public String getHomepage(Model model){
        model.addAttribute("savedWorkflows",workflowService.findAll());
        return "homePage";
    }

    @PostMapping(value={"/","/home"})
    public String postWorkflow(@RequestParam String name) throws IllegalAccessException {
        Workflow inputWorkflow=new Workflow();
        inputWorkflow.setName(name);
        //Create a default entry with Waiting Status
        workflowService.saveWorkflow(inputWorkflow);
        //Asynchronous called to execute method
        workflowService.executeWorkflow(inputWorkflow);

        return "redirect:/home";
    }

}