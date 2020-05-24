package com.xor.taskExecutor.controller;

import com.xor.taskExecutor.database.model.TaskNameEntity;
import com.xor.taskExecutor.util.TaskNamesProvider;
import com.xor.taskExecutor.service.TaskNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class TaskNameController {
    @Autowired
    TaskNamesProvider taskNamesProvider;

    @Autowired
    TaskNameService taskNameService;
    /*
    *   Form have input field with allowed tasknames list
    */
    @ModelAttribute("allowedTaskNames")
    public List<String> getAvailableTaskList(){
        return taskNamesProvider.getAllowedTaskNames();
    }

    @GetMapping(value="/allNames")
    public String getTaskNames(Model model){
        model.addAttribute("availableTaskList", taskNameService.getTaskNameEntities());
        return "taskNamesView";
    }

    @PostMapping("/allNames")
    public String addTaskName(TaskNameEntity taskNameEntity){
       taskNameService.save(taskNameEntity);
       return "redirect:/allNames";
    }

    @GetMapping("/editTaskName/{taskId}")
    public String editTaskNameForm(Model model){
        return "editNameForm";
    }

    @PostMapping("/editTaskName/{taskId}")
    public String editTaskName(@PathVariable("taskId") Integer id,
                               @RequestParam String name){
        taskNameService.override(id,name);
        return "redirect:/allNames";
    }

}
