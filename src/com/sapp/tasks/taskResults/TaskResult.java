package com.sapp.tasks.taskResults;

import com.sapp.tasks.Task;

public class TaskResult {

    private Task task;
    private ResultList result;

    public TaskResult(Task task, ResultList result) {
        this.task = task;
        this.result = result;
    }

    public Task getTask() { return task; }
    public ResultList getResults() { return result; }
}
