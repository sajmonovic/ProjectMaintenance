package com.sapp.tasks;

import com.sapp.drawings.Drawing;

import java.util.List;

public interface Task {

    List<TaskResultEnum> execute();
    String getSourcePathString();
    String getDestinationPathString();
    Drawing getParentDrawing();
}
