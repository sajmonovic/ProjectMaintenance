package com.sapp.tasks;

import java.util.ArrayList;
import java.util.List;

public class MoveFileResult {

    private List<TaskResultEnum> taskResultEnums;
    private FilePathTrace filePathTrace;

    public List<TaskResultEnum> getTaskResultEnums() {
        return taskResultEnums;
    }

    public void setTaskResultEnums(List<TaskResultEnum> taskResultEnums) {
        this.taskResultEnums = taskResultEnums;
    }

    public FilePathTrace getFilePathTrace() {
        return filePathTrace;
    }

    public void setFilePathTrace(FilePathTrace filePathTrace) {
        this.filePathTrace = filePathTrace;
    }
}
