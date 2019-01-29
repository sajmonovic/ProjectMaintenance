package com.sapp.tasks;

import com.sapp.drawings.Drawing;
import com.sapp.tasks.taskResults.ResultList;

public interface Task {

    ResultList execute();
    FilePathTrace getPrimaryPathTrace();
    FilePathTrace getSecondaryPathTrace();
    Drawing getParentDrawing();
}
