package com.sapp.tasks;

import com.sapp.drawings.Drawing;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmptyTask implements Task {

    private final Path sourcePath;
    private Drawing parentDrawing;

    public EmptyTask(Drawing parentDrawing, Path sourcePath) {
        this.sourcePath = sourcePath;
        this.parentDrawing = parentDrawing;
    }

    @Override
    public List<TaskResultEnum> execute() {
        return new ArrayList<TaskResultEnum>(Arrays.asList(TaskResultEnum.NO_ACTION));
    }

    @Override
    public String getSourcePathString() {
        return sourcePath.toString();
    }

    @Override
    public String getDestinationPathString() {
        return null;
    }

    @Override
    public Drawing getParentDrawing() {
        return parentDrawing;
    }
}
