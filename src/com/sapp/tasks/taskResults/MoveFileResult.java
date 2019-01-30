package com.sapp.tasks.taskResults;

import com.sapp.tasks.FilePathTrace;

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
