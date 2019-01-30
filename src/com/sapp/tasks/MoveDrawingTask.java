package com.sapp.tasks;

import com.sapp.drawings.Drawing;
import com.sapp.tasks.taskResults.MoveFileResult;
import com.sapp.tasks.taskResults.ResultList;
import com.sapp.utils.SuffixGenerator;

import java.nio.file.Path;

public class MoveDrawingTask implements Task {

    private final Path destinationDir;
    private SuffixGenerator sg;
    private Drawing parentDrawing;
    private FilePathTrace primaryTrace, secondaryTrace;

    public MoveDrawingTask(Drawing parentDrawing, Path destinationDir, SuffixGenerator sg) {

        this.parentDrawing = parentDrawing;
        this.destinationDir = destinationDir;
        this.sg = sg;
        primaryTrace = null;
        secondaryTrace = null;
    }

    @Override
    public Drawing getParentDrawing() {
        return parentDrawing;
    }

    @Override
    public ResultList execute() {

        ResultList result = new ResultList();

        MoveFileTask primary = new MoveFileTask(parentDrawing.getPrimaryFile(), destinationDir, sg);
        MoveFileResult primaryResults = primary.execute();
        primaryTrace = primaryResults.getFilePathTrace();
        result.getPrimaryResults().addAll(primaryResults.getResultEnums());

        MoveFileTask secondary = new MoveFileTask(parentDrawing.getSecondaryFile(), destinationDir, sg);
        MoveFileResult secondaryResults = secondary.execute();
        secondaryTrace = secondaryResults.getFilePathTrace();
        result.getSecondaryResults().addAll(secondaryResults.getResultEnums());

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

}
