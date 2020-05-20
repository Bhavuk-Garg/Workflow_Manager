package com.xor.taskExecutor.controller;

import com.xor.taskExecutor.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WorkflowController {
    @Autowired
    WorkflowService workflowService;

    @GetMapping("/workflow/{name}")
    public String workflowDetails(@PathVariable String name,
                                  Model model)
    {
        model.addAttribute("workflow",workflowService.findByName(name));
        return "workflowDetails";
    }
}
