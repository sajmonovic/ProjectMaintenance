package com.sapp;

import com.sapp.drawings.DrawingList;
import com.sapp.drawings.DrawingScanner;
import com.sapp.drawings.RevisionList;
import com.sapp.outputhandlers.HTMLOutput;
import com.sapp.tasks.TaskList;
import com.sapp.tasks.taskResults.ReportMap;
import com.sapp.utils.INIFileParser;
import com.sapp.utils.PathHandler;
import com.sapp.utils.SuffixGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {

    private static String workingDIR;
    private static INIFileParser iniFile;

    public static OutputContainer cleanupOperation() {

        System.out.println("");
        System.out.println("Entering cleanup mode in " + workingDIR +" ...");

        System.out.println("     primary file extension : " + INIFileParser.getParams().get("PRIMARY"));
        System.out.println("   secondary file extension : " + INIFileParser.getParams().get("SECONDARY")+"\n");
        // new drawing list
        DrawingList DL = new DrawingList();

        // new scanner
        DrawingScanner DS = new DrawingScanner(workingDIR, DL,
                INIFileParser.getParams().get("PRIMARY"),
                INIFileParser.getParams().get("SECONDARY")
        );

        System.out.println("Scanning directory ... ");
        // scan working directory
        DS.scanDirectory();
        System.out.println("Finished ... \n");

        SuffixGenerator sg = new SuffixGenerator();
        TaskList taskList = new TaskList();

        // generate destination path
        Path destinationDir = Paths.get(INIFileParser.getParams().get("OUTPUT"));
        if (!destinationDir.isAbsolute()) {
            destinationDir = Paths.get(workingDIR + "/" +destinationDir.toString());
        }
        if (Files.notExists(destinationDir)) {
            try {
                System.out.println("Creating output directory: " + destinationDir +"\n");
                Files.createDirectory(destinationDir);

            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        }

        System.out.println("Collecting file operation tasks ...");
        // collect MoveTasks
        for (Map.Entry<String, RevisionList> e : DL.getList().entrySet()) {
            RevisionList currentList = e.getValue();
            taskList.addAll(currentList.getTaskList(destinationDir,sg));
        }
        System.out.println("Gathered " + taskList.size() + " file tasks ... \n");

        // executing and gathering reports
        System.out.println("Executing tasks ... ");
        ReportMap reportMap = taskList.executeTasks();
        System.out.println("Ready!");

        return new OutputContainer(DL, reportMap);
    }

    public static OutputContainer scanOperation() {

        System.out.println("\nEntering scanning mode in " + workingDIR +" ...");

        System.out.println("     primary file extension : " + INIFileParser.getParams().get("PRIMARY"));
        System.out.println("   secondary file extension : " + INIFileParser.getParams().get("SECONDARY")+"\n");
        // new drawing list
        DrawingList DL = new DrawingList();

        // new scanner
        DrawingScanner DS = new DrawingScanner(workingDIR, DL,
                INIFileParser.getParams().get("PRIMARY"),
                INIFileParser.getParams().get("SECONDARY")
        );

        System.out.println("Scanning directory ... ");
        // scan working directory
        DS.scanDirectory();
        System.out.println("Finished ... \n");

        TaskList taskList = new TaskList();

        System.out.println("Generating results ... ");
        // collect EmptyTasks
        for (Map.Entry<String, RevisionList> e : DL.getList().entrySet()) {
            RevisionList currentList = e.getValue();
            taskList.addAll(currentList.getEmptyTasks());
        }

        // executing and gathering reports
        ReportMap reportMap = taskList.executeTasks();
        System.out.println("Ready!");

        return new OutputContainer(DL, reportMap);
    }

    public static void main(String[] args) {

        System.out.println("┌───────────────────────────────────╖");
        System.out.println("│   Starting maintenance tool ...   ║");
        System.out.println("│     ... reading settings ...      ║");
        System.out.println("╘═══════════════════════════════════╝");

        String iniFile = args[0];
        Path configFile = Paths.get(iniFile);

        workingDIR = Paths.get(iniFile).getParent().toString();
        String workingMode = new PathHandler(configFile).getFileNameWithoutExtension().toUpperCase();

        Main.iniFile = new INIFileParser(iniFile);
        Main.iniFile.parseFile();

        OutputContainer results = null;

        switch (workingMode) {
            case "CLEANUP" :
                results = cleanupOperation();
                break;
            case "SCAN" :
                results = scanOperation();
                break;
            default:
                break;
        }

        if (INIFileParser.getParams().get("REPORT").toUpperCase().equals("TRUE")) {
            HTMLOutput outputFile = new HTMLOutput(workingDIR);
            outputFile.outputReport(results);
            System.out.println("Report ready!");
        }
    }
}
