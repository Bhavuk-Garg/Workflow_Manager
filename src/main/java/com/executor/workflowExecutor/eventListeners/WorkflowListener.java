package com.executor.workflowExecutor.eventListeners;

import com.executor.workflowExecutor.eventListeners.event.WorkflowCreateEvent;
import com.executor.workflowExecutor.eventListeners.event.WorkflowStatusChangeEvent;
import com.executor.workflowExecutor.service.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WorkflowListener
{
    @Autowired  SseService sseService;

        @EventListener(WorkflowStatusChangeEvent.class)
        public void workflowUpdated(WorkflowStatusChangeEvent event)
        {
            List<SseEmitter> deadEmitters = new ArrayList<>();
            List<SseEmitter> emitters=sseService.getEmitters();
            emitters.forEach(emitter -> {
                try {
                    Map<String,Object> workflowLimited=new HashMap<>();
                    workflowLimited.put("name",event.getWorkflow().getName());
                    workflowLimited.put("status",event.getWorkflow().getStatus());
                    emitter.send(SseEmitter.event().name("workflowStatusChange").data(workflowLimited));
                } catch (Exception e) {
                    deadEmitters.add(emitter);
                }
            });
            emitters.remove(deadEmitters);
        }

        @EventListener(WorkflowCreateEvent.class)
        public void workflowCreated(WorkflowCreateEvent event)
        {
            List<SseEmitter> deadEmitters = new ArrayList<>();
            List<SseEmitter> emitters=sseService.getEmitters();

            emitters.forEach(emitter -> {
                try {
                    emitter.send(SseEmitter.event().name("workflowCreated").data(event.getMessage()));
                } catch (Exception e) {
                    deadEmitters.add(emitter);
                }
            });
            emitters.remove(deadEmitters);
        }
}
