package com.sapp;

import com.sapp.drawings.DrawingList;
import com.sapp.drawings.DrawingScanner;
import com.sapp.drawings.RevisionList;
import com.sapp.tasks.TaskList;
import com.sapp.tasks.TaskReport;
import com.sapp.utils.INIFileParser;
import com.sapp.utils.PathHandler;
import com.sapp.utils.SuffixGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {

    private static String workingDIR;
    private static INIFileParser iniFile;

    public static void cleanupOperation() {

        System.out.println("");
        System.out.println("Entering cleanup mode in " + workingDIR +" ...");

        System.out.println("     primary file extension : " + iniFile.getParams().get("PRIMARY"));
        System.out.println("   secondary file extension : " + iniFile.getParams().get("SECONDARY")+"\n");
        // new drawing list
        DrawingList DL = new DrawingList();

        // new scanner
        DrawingScanner DS = new DrawingScanner(workingDIR, DL,
                iniFile.getParams().get("PRIMARY"),
                iniFile.getParams().get("SECONDARY")
        );

        System.out.println("Scanning directory ... ");
        // scan working directory
        DS.scanDirectory();
        System.out.println("Finished ... \n");

        SuffixGenerator sg = new SuffixGenerator();
        TaskList taskList = new TaskList();

        // generate destination path
        Path destinationDir = Paths.get(iniFile.getParams().get("OUTPUT"));
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
        List<TaskReport> tr = taskList.executeTasks();

        System.out.println("Ready!");

        /*for (TaskReport t : tr) {
            MoveFileTask mft = (MoveFileTask)t.getTask();

            System.out.println("Operation: "+ mft.getSourcePathString() + " -> " + mft.getDestinationPathString() + "<> Result :");
            for (TaskResultEnum e : t.getResults()) {
                System.out.println("   -> " +e.toString());
            }
        }*/
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

        switch (workingMode) {
            case "CLEANUP" :
                cleanupOperation();
                break;

            default:
                break;
        }
    }
}
