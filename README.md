# Workflow Manager
## Problem Statement


ASFT as an org has orchestrator systems which interacts with multiple other services(hosted on different platforms, EC2, Lambda, ECS etc.). An orchestrator or a workflow system is one which coordinates which multiple other services to achieve desired result for the customer. 


**As an orchestrator:**
```text
1. I should be able to define dependencies among tasks. eg. execute task B should be executed post task A.
2. I should be able to execute multiple task in parallel.
3. I should be call a task B only when output from task A meets certain criteria.
        say output of tasks A is a field X.
        if X == 'doB' then executeTaskB
        if X == 'doC' then executeTaskC

4. I should able to define failure handling task for each of those tasks.
5. I should be able to search execution of tasks in a workflow by a tagged id
6. I should be able to support multiple type of tasks(Lambda, ECS etc.)
```
<img align="right" width="300" src="assets/graphImage.jpg" alt="graph Image" />

### This represents a directed acyclic graph

1. Dummy Task Implementations are used in the project as follows:
    * printTask
    * dataTask
    * syncTask ...
    
2. It have three types of tasks :
    1. Normal : After normal Execution generates an output
    2. Trigger Wait : Halts Execution until a trigger is pressed
    3. Time Wait : Sole purpose is to add delay between execution of two tasks 

## Technology Stack
* Back End
    * Java
    * Spring Boot Web (MVC Framework)
* Front End
    * Bootstrap
    * Thymeleaf Template Engine
* Database
    * Mysql
    
##Database Model of Project

<img align="center" width="300" src="assets/tablesImage.jpg" alt="table Image" />
