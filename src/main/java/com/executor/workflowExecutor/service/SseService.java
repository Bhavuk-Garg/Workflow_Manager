package com.executor.workflowExecutor.service;

import org.springframework.cglib.core.EmitUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {
    List<SseEmitter> emitters=new CopyOnWriteArrayList<>();

    public List<SseEmitter> getEmitters() {
        return emitters;
    }
    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
    }
}
