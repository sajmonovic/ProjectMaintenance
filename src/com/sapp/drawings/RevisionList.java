package com.sapp.drawings;

import com.sapp.tasks.TaskList;
import com.sapp.utils.SuffixGenerator;

import java.nio.file.Path;
import java.util.ArrayList;

public class RevisionList extends ArrayList<Drawing> {

    public TaskList getTaskList(Path destinationDir, SuffixGenerator sg) {

        TaskList result = new TaskList();
        Drawing currentDrawing;
        TaskList currentList;

        // move task for old file versions
        for (int i = 0 ; i < this.size()-1; i++){
            currentDrawing = this.get(i);
            currentList = currentDrawing.getMoveTaskList(destinationDir, sg);

            result.addAll(currentList);
        }

        // empty task for latest file versions
        currentDrawing = this.get(this.size()-1);
        result.addAll(currentDrawing.getEmptyTask());

        return result;
    }

    public TaskList getEmptyTasks() {

        TaskList result = new TaskList();
        Drawing currentDrawing;

        for (int i = 0 ; i < this.size(); i++){
            currentDrawing = this.get(i);
            result.addAll(currentDrawing.getEmptyTask());
        }
        return result;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (Drawing d : this){

            sb.append(d.getId());
            sb.append(" -> ");
            sb.append(d.getTitle());
            sb.append(" -> ");
            sb.append(d.getRevision());
            sb.append("\n");
        }
        return sb.toString();
    }

}
