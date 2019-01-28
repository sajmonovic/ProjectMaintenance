package com.sapp.sourceFile;


import java.nio.file.Files;
import java.nio.file.Path;

public class SecondaryFile extends SourceFile {

    public SecondaryFile(PrimaryFile primaryFile, String extension) {

        super();

        Path secondaryPath = null;

        String s = primaryFile.getFilePath().getFileName().toString();
        String secondaryFileName = s.substring(0, s.lastIndexOf("."))+ "." + extension;
        Path assumedPath = primaryFile.getFilePath().resolveSibling(secondaryFileName);
        if (Files.exists(assumedPath) && (Files.isRegularFile(assumedPath))) secondaryPath = assumedPath;

        super.setFilePath(secondaryPath);
    }
}
