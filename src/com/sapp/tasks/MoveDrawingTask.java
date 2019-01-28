package com.sapp.tasks;

import com.sapp.drawings.Drawing;
import com.sapp.utils.FileUtility;
import com.sapp.utils.PathHandler;
import com.sapp.utils.SuffixGenerator;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MoveDrawingTask implements Task {

    private final Path destinationDir;
    private Path destinationPath = null;
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
    public TaskResult execute() {

        TaskResult result = new TaskResult();

        MoveFileTask primary = new MoveFileTask(parentDrawing.getPrimaryFile(), destinationDir, sg);
        MoveFileResult primaryResults = primary.execute();
        primaryTrace = primaryResults.getFilePathTrace();
        result.getPrimaryResults().addAll(primaryResults.getTaskResultEnums());

        MoveFileTask secondary = new MoveFileTask(parentDrawing.getSecondaryFile(), destinationDir, sg);
        MoveFileResult secondaryResults = secondary.execute();
        secondaryTrace = secondaryResults.getFilePathTrace();
        result.getSecondaryResults().addAll(secondaryResults.getTaskResultEnums());

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
