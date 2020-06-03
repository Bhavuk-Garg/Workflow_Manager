package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.service.TaskInfoService;
import com.executor.workflowExecutor.components.utility.TaskNamesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskInfoController {
    @Autowired
    TaskNamesProvider taskNamesProvider;

    @Autowired
    TaskInfoService taskInfoService;

    @ModelAttribute("allowedTaskNames")
    public List<String> getAvailableTaskList(){
        /*
         *   list of predefined taskNames is loaded in every model
         */
        return taskNamesProvider.getAllowedTaskNames();
    }

    @GetMapping(value="/allNames")
    public String getTaskNames(Model model){
        model.addAttribute("availableTaskNames", taskInfoService.getAll());
        return "taskNamesList";
    }

    @PostMapping("/allNames")
    public String addTaskName(TaskInfo taskInfo){
       taskInfoService.save(taskInfo);
       return "redirect:/allNames";
    }

    @GetMapping("/editTaskName/{taskId}")
    public String getEditTaskNameForm(Model model){
        return "editNameForm";
    }

    @PostMapping("/editTaskName/{taskId}")
    public String setTaskName(@PathVariable("taskId") Integer id,
                               TaskInfo taskInfo){
        taskInfo.setId(id);
        taskInfoService.save(taskInfo);
        return "redirect:/allNames";
    }

}
