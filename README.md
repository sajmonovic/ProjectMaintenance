# ProjectMaintenance
Small shell integrateable software to analyze and cleanup multiple revision drawing files.

The operation is started by opening *.pmaint file format with a Windows batch file.

```
@echo off
java -jar "%0\..\ProjectMaintenance.jar" "%1"
pause
```
the program then uses the settings written in .pmaint file to scan and operate on files 
found in the current directory in which the .pmain file is located.

Currently the program uses two file names SCAN.pmaint and CLEANUP.pmaint to both scan with 
no action or scan and cleanup.

The program copies with safety warranty which means it copies file, then analyses it
and compares to the source file. After similarity is confirmed, the source file is then removed.
