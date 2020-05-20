package com.mysqlExample.kuroko.controller;

import com.mysqlExample.kuroko.dto.TaskDependencyDTO;
import com.mysqlExample.kuroko.service.TaskDependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class TaskDependencyController {

    @Autowired
    TaskDependencyService taskDependencyService;

    @ModelAttribute
    public void addAttribute(Model model)
    {
        model.addAttribute("taskDependencyDTO",new TaskDependencyDTO());
    }

    @GetMapping("/allDependencies")
    public String showDependencies(Model model){
        model.addAttribute("dependencies" ,taskDependencyService.getAllDependencies());
        return "taskDependenciesList";
    }

    @PostMapping("/allDependencies")
    public String addDependency(@Valid TaskDependencyDTO taskDependencyDTO, BindingResult bindingResult, Model model){
        //If DTO is not valid return same page
        if(bindingResult.hasErrors()) {
            model.addAttribute("dependencies", taskDependencyService.getAllDependencies());
            return "taskDependenciesList";
        }
        taskDependencyService.addTaskDependency(taskDependencyDTO);
        return "redirect:/allDependencies";
    }


    @GetMapping("/editDependency/{id}")
    public String getEditDependencyPage(Model model){
        return "editDependencyForm";
    }

    @PostMapping("/editDependency/{id}")
    public String editDependency(@PathVariable int id,@Valid TaskDependencyDTO dto,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "editDependencyForm";
        }

        taskDependencyService.editTask(id,dto);
        return "redirect:/allDependencies";
    }

}
