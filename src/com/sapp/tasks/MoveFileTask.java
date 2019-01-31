package com.sapp.tasks;

import com.sapp.sourceFile.SourceFile;
import com.sapp.tasks.taskResults.MoveFileResult;
import com.sapp.tasks.taskResults.ResultEnum;
import com.sapp.utils.FileUtility;
import com.sapp.utils.PathHandler;
import com.sapp.utils.SuffixGenerator;

import java.nio.file.FileSystemException;
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

        List<ResultEnum> resultEnums = new ArrayList<>();

        if (Files.isDirectory(destinationDir, LinkOption.NOFOLLOW_LINKS)) {

            if (sourcePath != null) {
                destinationPath = destinationDir.resolve(sourcePath.getFileName());

                if (Files.exists(destinationPath)) {
                    // things to do if file already exists
                    resultEnums.add(ResultEnum.FILE_EXISTS);

                    destinationPath = (new PathHandler(destinationPath)).addSuffix(sg.getSuffixString());
                }

                boolean copied = false;
                try {
                    Files.copy(sourcePath, destinationPath);
                    if (Files.exists(destinationPath)) {
                        if (FileUtility.isTheSame(sourcePath, destinationPath)) {
                            copied = true;
                            Files.delete(sourcePath);
                            if (!Files.exists(sourcePath)) resultEnums.add(ResultEnum.SUCCESSFUL);
                            else resultEnums.add(ResultEnum.CANT_DELETE);
                        }
                    } else resultEnums.add(ResultEnum.FAILED);
                } catch (Exception e) {
                    if (copied) {
                        System.err.println(e.getMessage());
                        resultEnums.add(ResultEnum.CANT_DELETE);
                        System.err.println("File appears to be copied, but it cannot be deleted... \n" +
                                "Setting CANT_DELETE flag and continuing...");
                    } else
                    {
                        System.err.println(e.getMessage());
                        resultEnums.add(ResultEnum.FAILED);
                        System.err.println("Something went wrong... \n" +
                                "Setting FAILED flag and continuing...");
                    }
                }
            } else resultEnums.add(ResultEnum.NULL_PATH);
        }

        result.setResultEnums(resultEnums);
        result.setFilePathTrace(new FilePathTrace(sourcePath, destinationPath));

        return result;
    }
}
