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

    @ModelAttribute("availableTaskNames")
    public List<String> getAvailableTaskList(){
        /*
         *   list of predefined taskNames is loaded in every model
         */
        return taskNamesProvider.getAllowedTaskNames();
    }

    @GetMapping(value="/allTaskInfo")
    public String getAllTaskInfo(Model model){
        model.addAttribute("inputTaskInfo",new TaskInfo());
        model.addAttribute("allTaskInfo", taskInfoService.getAll());
        return "allTaskInfoList";
    }

    @PostMapping("/allTaskInfo")
    public String addTaskInfo(TaskInfo taskInfo){
       taskInfoService.save(taskInfo);
       return "redirect:/allTaskInfo";
    }

    @GetMapping("/editTaskInfo/{taskId}")
    public String getEditTaskNameForm(Model model){
        model.addAttribute("inputTaskInfo",new TaskInfo());
        return "editTaskInfoForm";
    }

    @PostMapping("/editTaskInfo/{taskId}")
    public String setTaskName(@PathVariable("taskId") Integer id,
                               TaskInfo taskInfo){
        /*
            Saving task with same id will override the previous value in database
         */
        taskInfoService.edit(id,taskInfo);
        return "redirect:/allTaskInfo";
    }

}
