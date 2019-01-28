package com.sapp.outputhandlers;

import com.sapp.tasks.TaskReport;

import java.util.List;

public class ConsoleOutput implements OutputHandler {

    private List<TaskReport> taskReportList;

    public ConsoleOutput(List<TaskReport> taskReportList) {
        this.taskReportList = taskReportList;
    }

    @Override
    public void outputReport() {

        StringBuilder sb = new StringBuilder();

        for (TaskReport t : taskReportList) {

            //t.getTask().getSourcePathString();

        }
    }
}
