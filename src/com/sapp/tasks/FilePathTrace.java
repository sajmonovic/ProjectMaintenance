package com.sapp.tasks;

import java.nio.file.Path;

public class FilePathTrace {

    private Path sourcePath;
    private Path destinationPath;

    public FilePathTrace(Path sourcePath, Path destinationPath) {
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public Path getDestinationPath() {
        return destinationPath;
    }
}
