package com.sapp.tasks;

public class TaskReport {

    private Task task;
    private TaskResult result;

    public TaskReport(Task task, TaskResult result) {
        this.task = task;
        this.result = result;
    }

    public Task getTask() { return task; }
    public TaskResult getResults() { return result; }
}
