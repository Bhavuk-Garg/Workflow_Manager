package com.xor.taskExecutor.controller;

import com.xor.taskExecutor.database.model.Workflow;
import com.xor.taskExecutor.service.WorkflowService;
import com.xor.taskExecutor.util.Status;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class WorkflowController {
    @Autowired
    WorkflowService workflowService;

    @GetMapping("/workflow/{name}")
    public String workflowDetails(@PathVariable String name,
                                  Model model)
    {
        Workflow workflowInstance=workflowService.findByName(name);
        model.addAttribute("workflow",workflowInstance);

        if(workflowInstance.getStatus()!= Status.Waiting) {
            List<Pair<Integer, String>> formattedResult = workflowService.getFormattedResult(workflowInstance.getResult());
            System.out.println(formattedResult);
            model.addAttribute("formattedResult", formattedResult);
        }

        return "workflowDetails";
    }


}
