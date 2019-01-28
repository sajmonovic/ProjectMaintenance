package com.sapp.tasks;

import com.sapp.drawings.Drawing;

import java.util.List;

public interface Task {

    TaskResult execute();
    FilePathTrace getPrimaryPathTrace();
    FilePathTrace getSecondaryPathTrace();
    Drawing getParentDrawing();
}
