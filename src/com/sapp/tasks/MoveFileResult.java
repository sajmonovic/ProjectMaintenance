package com.sapp.tasks;

import com.sapp.tasks.taskResults.ResultEnum;

import java.util.List;

public class MoveFileResult {

    private List<ResultEnum> resultEnums;
    private FilePathTrace filePathTrace;

    public List<ResultEnum> getResultEnums() {
        return resultEnums;
    }

    public void setResultEnums(List<ResultEnum> resultEnums) {
        this.resultEnums = resultEnums;
    }

    public FilePathTrace getFilePathTrace() {
        return filePathTrace;
    }

    public void setFilePathTrace(FilePathTrace filePathTrace) {
        this.filePathTrace = filePathTrace;
    }
}
