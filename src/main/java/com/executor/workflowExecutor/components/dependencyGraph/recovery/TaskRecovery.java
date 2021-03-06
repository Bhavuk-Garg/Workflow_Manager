package com.executor.workflowExecutor.components.dependencyGraph.recovery;

import com.executor.workflowExecutor.components.dependencyGraph.DependencyGraph;
import com.executor.workflowExecutor.database.model.Workflow;


public interface TaskRecovery {
    boolean recover(int id, Workflow workflow, DependencyGraph dependencyGraph);
}
