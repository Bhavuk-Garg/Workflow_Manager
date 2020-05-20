package com.mysqlExample.kuroko.controller;

import com.mysqlExample.kuroko.database.model.Status;
import com.mysqlExample.kuroko.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;

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
