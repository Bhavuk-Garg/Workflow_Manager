package com.xor.taskExecutor.controller;

import com.xor.taskExecutor.dto.TaskDependencyDTO;
import com.xor.taskExecutor.service.TaskDependencyService;
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

    /*
    *   TaskDependencyDTO is used to take dependency input
     */
    @ModelAttribute
    public void addCommonAttributes(Model model)
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
        //If DTO object is not valid return same page
        if(bindingResult.hasErrors()) {
            model.addAttribute("dependencies", taskDependencyService.getAllDependencies());
            return "taskDependenciesList";
        }
        taskDependencyService.addTaskDependency(taskDependencyDTO);
        return "redirect:/allDependencies";
    }


    @GetMapping("/editDependency/{id}")
    public String getEditDependencyPage(){
        return "editDependencyForm";
    }

    @PostMapping("/editDependency/{id}")
    public String editDependency(@PathVariable int id,@Valid TaskDependencyDTO inputDTO,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "editDependencyForm";
        }

        taskDependencyService.editTask(id,inputDTO);
        return "redirect:/allDependencies";
    }

}
