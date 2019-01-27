package com.sapp.drawings;

import com.sapp.tasks.EmptyTask;
import com.sapp.tasks.MoveFileTask;
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
    private final DecryptedPath info;
    private final FileSource sourceFiles;

    public Drawing(Path mainPath, FileSource fileSource){
        this.info = (new PathHandler(mainPath)).decrypt(
                INIFileParser.getParams().get(("REGEX")),
                Integer.parseInt(INIFileParser.getParams().get("GROUP_ID")),
                Integer.parseInt(INIFileParser.getParams().get("GROUP_TITLE")),
                Integer.parseInt(INIFileParser.getParams().get("GROUP_REV"))
        );
        this.sourceFiles = fileSource;
    }

    public boolean hasSecondary() { return sourceFiles.hasSecondary(); }
    public String getId() { return info.getId(); }
    public String getRevision() { return info.getRevision(); }
    public String getTitle() { return info.getTitle(); }

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

        if (sourceFiles.getPrimaryPath() != null) {
            result.add(new MoveFileTask(this, sourceFiles.getPrimaryPath(), destinationDir, sg));
        }

        if (sourceFiles.getSecondaryPath() != null) {
            result.add(new MoveFileTask(this, sourceFiles.getSecondaryPath(), destinationDir, sg));
        }

        return result;
    }

    public TaskList getEmptyTask() {

        TaskList result = new TaskList();

        if (sourceFiles.getPrimaryPath() != null) {
            result.add(new EmptyTask(this, sourceFiles.getPrimaryPath()));
        }
        if (sourceFiles.getSecondaryPath() != null) {
            result.add(new EmptyTask(this, sourceFiles.getSecondaryPath()));
        }
        return result;
    }
}
