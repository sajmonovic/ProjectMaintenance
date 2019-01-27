package com.sapp.utils;

import com.sapp.drawings.DecryptedPath;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathHandler {

    private Path input;

    public PathHandler(Path input) {
        this.input = input;
    }

    public String getFileExtension() {
        String fileName = input.getFileName().toString();
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    public String getFileNameWithoutExtension() {
        String fileName = input.getFileName().toString();
        return fileName.substring(0,fileName.lastIndexOf("."));
    }

    public DecryptedPath decrypt(String regex, int GROUP_ID, int GROUP_TITLE, int GROUP_REVISION) {

        String id = null;
        String revision = null;
        String title = null;

        // simpleFileName - file name without extension
        String simpleFileName = this.getFileNameWithoutExtension();

        Pattern idPattern = Pattern.compile(regex);
        Matcher mId = idPattern.matcher(simpleFileName);

        if (mId.find()) {
            id = mId.group(GROUP_ID);
            title = mId.group(GROUP_TITLE);
            revision = mId.group(GROUP_REVISION);
        }

        return new DecryptedPath(id, revision, title);
    }

    public Path addSuffix(String suffixString) {

        String pathString = input.toString();
        StringBuffer sb = new StringBuffer(pathString.substring(0, pathString.lastIndexOf(".")));
        sb.append(suffixString);
        sb.append(pathString.substring(pathString.lastIndexOf(".")));

        return Paths.get(sb.toString());
    }
}
