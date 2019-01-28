package com.sapp.tasks;

import com.sapp.drawings.Drawing;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmptyTask implements Task {

    private Drawing parentDrawing;
    private FilePathTrace primaryTrace;
    private FilePathTrace secondaryTrace;

    public EmptyTask(Drawing parentDrawing) {
        this.parentDrawing = parentDrawing;
        primaryTrace = new FilePathTrace(
                parentDrawing.getPrimaryFile().getFilePath(),
                parentDrawing.getPrimaryFile().getFilePath()
        );

        secondaryTrace = new FilePathTrace(
                parentDrawing.getSecondaryFile().getFilePath(),
                parentDrawing.getSecondaryFile().getFilePath()
        );
    }

    @Override
    public TaskResult execute() {

        TaskResult result = new TaskResult();

        result.getPrimaryResults().add(TaskResultEnum.NO_ACTION);
        result.getSecondaryResults().add(TaskResultEnum.NO_ACTION);

        return result;
    }

    @Override
    public FilePathTrace getPrimaryPathTrace() {
        return primaryTrace;
    }

    @Override
    public FilePathTrace getSecondaryPathTrace() {
        return secondaryTrace;
    }

    @Override
    public Drawing getParentDrawing() {
        return parentDrawing;
    }
}
