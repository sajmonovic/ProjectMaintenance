package com.sapp.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskResult {

    private List<TaskResultEnum> primaryResults = new ArrayList<>();
    private List<TaskResultEnum> secondaryResults = new ArrayList<>();

    public List<TaskResultEnum> getPrimaryResults() {
        return primaryResults;
    }

    public List<TaskResultEnum> getSecondaryResults() {
        return secondaryResults;
    }
}
