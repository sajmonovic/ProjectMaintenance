package com.sapp.tasks;

import com.sapp.drawings.Drawing;
import com.sapp.sourceFile.SourceFile;
import com.sapp.utils.FileUtility;
import com.sapp.utils.PathHandler;
import com.sapp.utils.SuffixGenerator;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MoveFileTask{

    private final Path sourcePath;
    private final Path destinationDir;
    private Path destinationPath = null;
    private SuffixGenerator sg;

    public MoveFileTask(SourceFile file, Path destinationDir, SuffixGenerator sg) {

        this.sourcePath = file.getFilePath();
        this.destinationDir = destinationDir;
        this.sg = sg;
    }

    public MoveFileResult execute() {

        MoveFileResult result = new MoveFileResult();

        List<TaskResultEnum> resultEnums = new ArrayList<>();

        if (Files.isDirectory(destinationDir, LinkOption.NOFOLLOW_LINKS)) {

            if (sourcePath != null) {
                destinationPath = destinationDir.resolve(sourcePath.getFileName());

                if (Files.exists(destinationPath)) {
                    // things to do if file already exists
                    resultEnums.add(TaskResultEnum.FILE_EXISTS);

                    destinationPath = (new PathHandler(destinationPath)).addSuffix(sg.getSuffixString());
                }

                try {
                    Files.copy(sourcePath, destinationPath);
                    if (Files.exists(destinationPath)) {
                        if (FileUtility.isTheSame(sourcePath, destinationPath)) {
                            Files.delete(sourcePath);
                            if (!Files.exists(sourcePath)) resultEnums.add(TaskResultEnum.SUCCESSFUL);
                            else resultEnums.add(TaskResultEnum.CANT_DELETE);
                        }
                    } else resultEnums.add(TaskResultEnum.FAILED);
                } catch (Exception e) { throw new RuntimeException(e); }

            } else resultEnums.add(TaskResultEnum.NULL_PATH);
        }

        result.setTaskResultEnums(resultEnums);
        result.setFilePathTrace(new FilePathTrace(sourcePath, destinationPath));

        return result;
    }
}
