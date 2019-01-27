package com.sapp.drawings;

import com.sapp.utils.PathHandler;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;

public class DrawingScanner {

    private Path root;
    private DrawingList outputList;
    private final String primaryExtension;
    private final String secondaryExtension;

    public DrawingScanner(String rootPath, DrawingList outputList, String primaryExtension, String secondaryExtension) {
        this.root = Paths.get(rootPath);
        this.outputList = outputList;
        this.primaryExtension = primaryExtension;
        this.secondaryExtension = secondaryExtension;
    }

    public void scanDirectory() {

        try {
            Files.walkFileTree(root,new HashSet<FileVisitOption>(), 1, new DrawingVisitor()) ;
        } catch (Exception e) {
            System.err.println("Error: " + e.getCause().getClass().getSimpleName() + " : " + e.getCause().getMessage());
        }
    }

    /**
     *      DrawingVisitor inner class
     */
    private class DrawingVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attr){
            if (attr.isRegularFile()) {
               try {
                   if ((new PathHandler(file)).getFileExtension().equalsIgnoreCase(primaryExtension)) {
                       FileSource source = new FileSource(file, secondaryExtension);
                       outputList.add(new Drawing(file, source));
                   }

               } catch (DuplicateRevisionException e) {
                    throw new RuntimeException(e);
                }
            }
            return FileVisitResult.CONTINUE;
        }
    }

}
