package com.sapp.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends ArrayList<Task>{

    public TaskList() {

        super();
    }

    public List<TaskReport> executeTasks() {

        List<TaskReport> result = new ArrayList<>();

        for (Task t : this) {
            result.add(new TaskReport(t,t.execute()));
        }
        this.clear();

        return result;
    }

}
