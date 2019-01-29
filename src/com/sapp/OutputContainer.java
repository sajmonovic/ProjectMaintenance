package com.sapp;

import com.sapp.drawings.DrawingList;
import com.sapp.tasks.taskResults.ReportMap;

public class OutputContainer {
    private DrawingList drawingList;
    private ReportMap reportMap;

    public OutputContainer(DrawingList drawingList, ReportMap reportMap) {
        this.drawingList = drawingList;
        this.reportMap = reportMap;
    }

    public DrawingList getDrawingList() {
        return drawingList;
    }

    public ReportMap getReportMap() {
        return reportMap;
    }
}
