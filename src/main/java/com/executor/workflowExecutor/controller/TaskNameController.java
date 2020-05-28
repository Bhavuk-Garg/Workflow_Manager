package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.database.model.TaskName;
import com.executor.workflowExecutor.service.TaskNameService;
import com.executor.workflowExecutor.components.utility.TaskNamesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskNameController {
    @Autowired
    TaskNamesProvider taskNamesProvider;

    @Autowired
    TaskNameService taskNameService;

    @ModelAttribute("allowedTaskNames")
    public List<String> getAvailableTaskList(){
        /*
         *   list of predefined taskNames is loaded in every model
         */
        return taskNamesProvider.getAllowedTaskNames();
    }

    @GetMapping(value="/allNames")
    public String getTaskNames(Model model){
        model.addAttribute("availableTaskNames", taskNameService.getAll());
        return "taskNamesList";
    }

    @PostMapping("/allNames")
    public String addTaskName(TaskName taskName){
       taskNameService.save(taskName);
       return "redirect:/allNames";
    }

    @GetMapping("/editTaskName/{taskId}")
    public String getEditTaskNameForm(Model model){
        return "editNameForm";
    }

    @PostMapping("/editTaskName/{taskId}")
    public String setTaskName(@PathVariable("taskId") Integer id,
                               @RequestParam String name){
        taskNameService.edit(id,name);
        return "redirect:/allNames";
    }

}
