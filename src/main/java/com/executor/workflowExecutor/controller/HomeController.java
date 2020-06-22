package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.service.ExecutionService;
import com.executor.workflowExecutor.service.SseService;
import com.executor.workflowExecutor.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class HomeController {
    @Autowired ExecutionService executionService;
    @Autowired WorkflowService workflowService;
    @Autowired SseService sseService;

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

    @CrossOrigin
    @ResponseBody
    @GetMapping(value="/new_connection",consumes = MediaType.ALL_VALUE)
    public SseEmitter handle()
    {
        SseEmitter emitter=new SseEmitter(-1L);
        sseService.addEmitter(emitter);
        return emitter;
    }


}