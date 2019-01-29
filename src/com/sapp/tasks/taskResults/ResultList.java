package com.sapp.tasks.taskResults;

import java.util.ArrayList;
import java.util.List;

public class ResultList {

    private List<ResultEnum> primaryResults = new ArrayList<>();
    private List<ResultEnum> secondaryResults = new ArrayList<>();

    public List<ResultEnum> getPrimaryResults() {
        return primaryResults;
    }

    public List<ResultEnum> getSecondaryResults() {
        return secondaryResults;
    }
}
