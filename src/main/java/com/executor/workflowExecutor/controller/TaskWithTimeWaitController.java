package com.executor.workflowExecutor.controller;

import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.TaskInfo;
import com.executor.workflowExecutor.database.repository.TaskInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskWithTimeWaitController {

    @Autowired
    TaskInfoRepository taskInfoRepository;
    @GetMapping("/allTaskWithTimeWait")
    public String getAllTaskWithTimeWait(Model model){
        model.addAttribute("obj",taskInfoRepository.findByType(Status.TIME_WAIT));
        return "timeWaitTask";
    }
    @PostMapping("changeTime/{id}")
    public String changeTime(@PathVariable int id,@RequestParam int time)
    {
        TaskInfo info=taskInfoRepository.findById(id).orElse(null);
        if(info==null) throw new RuntimeException("Invalid ID");
        info.setTime(time);
        taskInfoRepository.save(info);
        return "redirect:/allTaskWithTimeWait";
    }
}
