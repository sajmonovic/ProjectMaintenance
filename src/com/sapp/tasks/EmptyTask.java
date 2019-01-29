package com.sapp.tasks;

import com.sapp.drawings.Drawing;
import com.sapp.tasks.taskResults.ResultEnum;
import com.sapp.tasks.taskResults.ResultList;

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
    public ResultList execute() {

        ResultList result = new ResultList();

        result.getPrimaryResults().add(ResultEnum.NO_ACTION);
        result.getSecondaryResults().add(ResultEnum.NO_ACTION);

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
