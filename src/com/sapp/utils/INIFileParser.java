package com.sapp.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class INIFileParser {

    private String filePath;
    private static Map<String,String> params  = new HashMap<>();

    public INIFileParser(String filePath) {

        this.filePath = filePath;
    }

    public void parseFile() {

        String line, key, value;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){


            line = bufferedReader.readLine();
            while ( line != null){

                System.out.println(line);
                if ((!line.substring(0,1).equals("#"))) {
                    key = (line.substring(0,line.indexOf("="))).trim();
                    value = (line.substring(line.indexOf("=")+1)).trim();
                    params.put(key, value);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        return params.toString();
    }

    public static Map<String, String> getParams() {
        return params;
    }
}
