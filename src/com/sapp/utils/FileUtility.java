package com.sapp.utils;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileUtility {

    public static boolean isTheSame(Path f1, Path f2) {

        // assume result is false
        boolean result = false;

        try {
            // if files are the exact same size
            if (Files.size(f1) == Files.size(f2))
            {
                // create readers
                FileReader fr1 = null;
                FileReader fr2 = null;

                // assume result is true
                result = true;

                fr1 = new FileReader(f1.toString());
                fr2 = new FileReader(f2.toString());

                int cr1;
                int cr2;

                cr1 = fr1.read();
                cr2 = fr2.read();

                while (cr1 != -1) {

                    if (cr1 != cr2) {
                        return false;
                        // if chars are not equal, return false and quit
                    }

                    cr1 = fr1.read();
                    cr2 = fr2.read();
                }

                // close readers
                fr1.close();
                fr2.close();
            }

        } catch (Exception e ) { throw new RuntimeException(e); }

        return result;
    }
}
