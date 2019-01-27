package com.sapp.tasks;

import java.util.List;

public class TaskReport {

    private Task task;
    private List<TaskResultEnum> result;

    public TaskReport(Task task, List<TaskResultEnum> result) {
        this.task = task;
        this.result = result;
    }

    public Task getTask() { return task; }
    public List<TaskResultEnum> getResults() { return result; }
}
