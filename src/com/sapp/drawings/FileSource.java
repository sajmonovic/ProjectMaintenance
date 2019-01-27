package com.sapp.drawings;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileSource{

    DecryptedPath drawingInfo;
    private Path primaryPath;
    private Path secondaryPath = null;

    public FileSource(Path primaryPath, String secondaryExtension) {

        this.primaryPath = primaryPath;
        catchSecondary(secondaryExtension);
    }

    private void catchSecondary(String secondaryExtension) {

        String s = primaryPath.getFileName().toString();
        String dwgFileName = s.substring(0, s.lastIndexOf("."))+ "." + secondaryExtension;
        Path assumedPath = primaryPath.resolveSibling(dwgFileName);

        if (Files.exists(assumedPath) && (Files.isRegularFile(assumedPath))) secondaryPath = assumedPath;
    }

    public boolean hasSecondary() { return secondaryPath !=null; }

    public Path getPrimaryPath() { return primaryPath; }
    public Path getSecondaryPath() { return secondaryPath; }
}
