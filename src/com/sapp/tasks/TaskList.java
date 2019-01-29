package com.sapp.tasks;

import com.sapp.tasks.taskResults.ReportMap;
import com.sapp.tasks.taskResults.TaskResult;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>{

    public TaskList() {

        super();
    }

    public ReportMap executeTasks() {

        ReportMap result = new ReportMap();

        for (Task t : this) {
            result.put(t.getParentDrawing(), new TaskResult(t,t.execute()));
        }
        this.clear();

        return result;
    }

}
