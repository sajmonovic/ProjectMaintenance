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

public class MoveFileTask implements Task {

    private final Path sourcePath;
    private final Path destinationDir;
    private Path destinationPath = null;
    private SuffixGenerator sg;
    private Drawing parentDrawing;

    public MoveFileTask(Drawing parentDrawing, Path sourcePath, Path destinationDir, SuffixGenerator sg) {

        this.parentDrawing = parentDrawing;
        this.sourcePath = sourcePath;
        this.destinationDir = destinationDir;
        this.sg = sg;
    }

    public String getSourcePathString() { return sourcePath.toString(); }
    public String getDestinationPathString() { return destinationPath.toString(); }

    @Override
    public Drawing getParentDrawing() {
        return parentDrawing;
    }

    @Override
    public List<TaskResultEnum> execute() {

        List<TaskResultEnum> result = new ArrayList<>();

        if (Files.isDirectory(destinationDir, LinkOption.NOFOLLOW_LINKS)) {

            if (sourcePath != null) {
                destinationPath = destinationDir.resolve(sourcePath.getFileName());

                if (Files.exists(destinationPath)) {
                    // things to do if file already exists
                    result.add(TaskResultEnum.FILE_EXISTS);

                    destinationPath = (new PathHandler(destinationPath)).addSuffix(sg.getSuffixString());
                }

                try {
                    Files.copy(sourcePath, destinationPath);
                    if (Files.exists(destinationPath)) {
                        if (FileUtility.isTheSame(sourcePath, destinationPath)) {
                            Files.delete(sourcePath);
                            if (!Files.exists(sourcePath)) result.add(TaskResultEnum.SUCCESSFUL);
                            else result.add(TaskResultEnum.CANT_DELETE);
                        }
                    } else result.add(TaskResultEnum.FAILED);
                } catch (Exception e) { throw new RuntimeException(e); }

            } else result.add(TaskResultEnum.NULL_PATH);
        }
        return result;
    }

}
