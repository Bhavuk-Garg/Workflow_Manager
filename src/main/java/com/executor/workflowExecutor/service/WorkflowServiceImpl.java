package com.executor.workflowExecutor.service;

import com.executor.workflowExecutor.components.dependencyGraph.DependencyGraph;
import com.executor.workflowExecutor.database.repository.WorkflowRepository;
import com.executor.workflowExecutor.database.model.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    @Autowired
    WorkflowRepository workflowRepository;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ExecutionService executionService;

    @Override
    public void saveWorkflow(Workflow inputWorkflow){
            workflowRepository.save(inputWorkflow);
    }

    @Override
    public Iterable<Workflow> findAll() {
        return workflowRepository.findAll(Sort.by("creationDate").descending());
    }

    @Override
    @Async
    public void executeWorkflow(Workflow inputWorkflow)  {
        DependencyGraph dependencyGraph=applicationContext.getBean("dependencyGraph",DependencyGraph.class);
        String res=executionService.execute(1,inputWorkflow,dependencyGraph);
        System.out.println("returned from execution service with :");
        System.out.println(res);
    }

    @Override
    public List<Workflow> findByNameLike(String name) {
        return workflowRepository.findByNameLikeOrderByCreationDateDesc("%"+name+"%");
    }

    @Override
    public Workflow findByName(String name) {
        return workflowRepository.findByName(name);
    }
}
