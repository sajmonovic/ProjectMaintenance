package com.sapp.drawings;

import com.sapp.sourceFile.PrimaryFile;
import com.sapp.sourceFile.SecondaryFile;
import com.sapp.tasks.EmptyTask;
import com.sapp.tasks.MoveDrawingTask;
import com.sapp.tasks.TaskList;
import com.sapp.utils.INIFileParser;
import com.sapp.utils.PathHandler;
import com.sapp.utils.SuffixGenerator;

import java.nio.file.Path;
import java.util.Comparator;

/** The object contains basic PRIMARY file info with associated SECONDARY file path
 *
 */

public class Drawing{

    private PrimaryFile primaryFile;
    private SecondaryFile secondaryFile;

    private final DecryptedPath info;

    public Drawing(PrimaryFile primaryFile, SecondaryFile secondaryFile){
        info = (new PathHandler(primaryFile.getFilePath())).decrypt(
                INIFileParser.getParams().get(("REGEX")),
                Integer.parseInt(INIFileParser.getParams().get("GROUP_ID")),
                Integer.parseInt(INIFileParser.getParams().get("GROUP_TITLE")),
                Integer.parseInt(INIFileParser.getParams().get("GROUP_REV"))
        );
        this.primaryFile = primaryFile;
        this.secondaryFile = secondaryFile;
    }

    public boolean hasSecondary() { return secondaryFile.getFilePath()!=null; }

    public String getId() { return info.getId(); }
    public String getRevision() { return info.getRevision(); }
    public String getTitle() { return info.getTitle(); }

    public PrimaryFile getPrimaryFile() {
        return primaryFile;
    }

    public SecondaryFile getSecondaryFile() {
        return secondaryFile;
    }

    public static Comparator<Drawing> getComparator() {
        return new Comparator<Drawing>() {
            @Override
            public int compare(Drawing o1, Drawing o2) {
                return o1.getRevision().compareTo(o2.getRevision());
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        Drawing d = (Drawing) obj;
        return (this.getRevision().equals(d.getRevision()));
    }

    public TaskList getMoveTaskList(Path destinationDir, SuffixGenerator sg) {
        TaskList result = new TaskList();
        if (primaryFile.getFilePath() != null) {
            result.add(new MoveDrawingTask(this, destinationDir, sg));
        }
        return result;
    }

    public TaskList getEmptyTask() {
        TaskList result = new TaskList();
        if (primaryFile.getFilePath() != null) {
            result.add(new EmptyTask(this));
        }
        return result;
    }
}
