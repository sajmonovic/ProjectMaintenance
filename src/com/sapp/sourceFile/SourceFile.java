package com.sapp.sourceFile;

import java.nio.file.Path;

public class SourceFile {

    private Path filePath = null;

    protected void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }
}
