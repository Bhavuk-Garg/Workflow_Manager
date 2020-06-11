package com.executor.workflowExecutor.components.dependencyGraph.recovery;

import com.executor.workflowExecutor.components.dependencyGraph.DependencyGraph;
import com.executor.workflowExecutor.components.utility.ExecutionHelper;
import com.executor.workflowExecutor.components.utility.Status;
import com.executor.workflowExecutor.database.model.Workflow;
import com.executor.workflowExecutor.database.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskRecoveryImpl implements TaskRecovery {
    int maxAttempts=2;
    static final int EXPONENT_COFFICIENT=2;

    @Autowired ExecutionHelper executionHelper;
    @Autowired WorkflowRepository workflowRepository;
    @Override
    public void recover(int id,Workflow workflow, DependencyGraph dependencyGraph) {
        //Give it one more shot
        int prevFailures=countPreviousFailedTasks(workflow);
        if( prevFailures < maxAttempts)
        {
            exponentialWaiting(prevFailures);
           executionHelper.execute(id,workflow,dependencyGraph);
        }
        else {
            workflow.setStatus(Status.FAILED);
            workflowRepository.save(workflow);
        }
    }

    private void exponentialWaiting(int n) {
        long waitTime=(long)Math.pow(EXPONENT_COFFICIENT,n)*1000;
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int countPreviousFailedTasks(Workflow inputWorkflow) {
        if(inputWorkflow.getResult()==null || inputWorkflow.getResult().length()==0)    return 0;
        String resultUnit[]=inputWorkflow.getResult().split(";");
        int count=0;
        for(int i=resultUnit.length-1;i>=0;i--)
        {
            // format of splittedResult will be [date,id,name,type,output]
            String curOutput=resultUnit[i].split(",",-1)[4];
            if(curOutput.length()==0)   count++;
            else    break;
        }
        return count;
    }

}
