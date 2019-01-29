package com.sapp.outputhandlers;

import com.sapp.OutputContainer;
import com.sapp.drawings.Drawing;
import com.sapp.drawings.DrawingList;
import com.sapp.drawings.RevisionList;
import com.sapp.tasks.taskResults.ReportMap;
import com.sapp.tasks.taskResults.ResultEnum;
import com.sapp.utils.INIFileParser;

import javax.xml.transform.Result;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class HTMLOutput implements OutputHandler {

    private StringBuilder sb = new StringBuilder();
    private String workingDIR;

    public HTMLOutput(String workingDIR) {
        this.workingDIR = workingDIR;
    }

    private class HTML {
        HTML() {
            sb.append("<HTML>\n");
        }
        void close() {
            sb.append("</HTML>\n");
        }
    }

    private class BODY {
        BODY() {
            sb.append("<BODY>\n");
        }
        void close() {
            sb.append("</BODY>\n");
        }
    }

    private class HEAD {
        HEAD() {
            sb.append("<HEAD>\n");
        }
        void close() {
            sb.append("</HEAD>\n");
        }
    }

    private class STYLE {
        STYLE() {
            sb.append("<style type=\"text/css\">\n");
        }

        void insertStyle (String style) {
            sb.append(style);
        }
        void close() {
            sb.append("</style>\n");
        }
    }

    private class TABLE {
        TABLE(String style) {
            sb.append("<TABLE ");
            sb.append("style=\"");
            sb.append(style);
            sb.append("\">\n");
        }
        void close() {
            sb.append("</TABLE>\n");
        }
        ROW createRow(String style) {
            return new ROW(style);
        }

        private class ROW {
            ROW(String style) {
                sb.append("<TR ");
                sb.append("style=\"");
                sb.append(style);
                sb.append("\">\n");
            }
            void close() {
                sb.append("</TR>\n");
            }
            void insertCell(String style,String content, String colspan) {
                sb.append("<TD ");
                sb.append("style=\"");
                sb.append(style);
                sb.append("\" colspan=\"");
                sb.append(colspan);
                sb.append("\">");
                sb.append(content);
                sb.append("</TD>\n");
            }
        }
    }

    private class LINK {

        private String href;
        private String content;
        private String style;

        public LINK (String href, String content, String style) {
            this.href = href;
            this.content = content;
            this.style = style;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();

            result.append("<a href=\"");
            result.append(href);
            result.append("\" style=\"");
            result.append(style);
            result.append("\">");
            result.append(content);
            result.append("</a>\n");

            return result.toString();
        }
    }

    private class ICON {

        private String fileName;
        private String hint;
        private String basePath;
        private Integer size;

        public ICON(ResultEnum resultEnum, Integer sizepx){

            size = sizepx;
            basePath= INIFileParser.getParams().get("GFX");

            if (!basePath.endsWith("/")) basePath = basePath +"\\";

            switch (resultEnum){
                case NO_ACTION:
                    fileName = "NO_ACTION.png";
                    hint = "NO ACTION";
                    break;
                case FAILED:
                    fileName = "FAILED.png";
                    hint = "FAILED";
                    break;
                case NULL_PATH:
                    fileName = "NULL_PATH.png";
                    hint = "NULL PATH";
                    break;
                case SUCCESSFUL:
                    fileName = "SUCCESSFUL.png";
                    hint = "SUCCESSFUL";
                    break;
                case CANT_DELETE:
                    fileName = "CANT_DELETE.png";
                    hint = "CAN'T DELETE";
                    break;
                case FILE_EXISTS:
                    fileName = "FILE_EXISTS.png";
                    hint = "FILE RENAMED";
                    break;
            }
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("<img src=\"");
            result.append(basePath + fileName);
            result.append("\" title=\"");
            result.append(hint);
            result.append("\" height=\"");
            result.append(size);
            result.append("\" width=\"");
            result.append(size);
            result.append("\"/>");
            return result.toString();
        }
    }

    @Override
    public void outputReport(OutputContainer input) {

        HTML tagHTML = new HTML();

        HEAD tagHEAD = new HEAD();
        STYLE tagSTYLE = new STYLE();
        tagSTYLE.insertStyle(
                "a:link,a:visited {\n" +
                "color: #330066;\n" +
                "text-decoration: none;\n" +
                "}\n" +
                "a:hover {\n" +
                "color: #991900;\n" +
                "text-decoration: none;\n" +
                "target-new: tab;\n" +
                "}\n"
        );
        tagSTYLE.close();
        tagHEAD.close();

        BODY tagBODY = new BODY();

        TABLE tagTABLE = new TABLE("" +
                "border: 2px dashed rgba(28,110,164,0.5); " +
                "border-radius: 5px; " +
                "-webkit-box-shadow: 6px 5px 24px 5px rgba(0,0,0,0.76); " +
                "box-shadow: 6px 5px 24px 5px rgba(0,0,0,0.76); " +
                "background-color: #F2E6FF; " +
                "width: 1200px; " +
                "font-family: 'Lucida Sans Unicode', 'Lucida Grande', sans-serif; " +
                "font-size: 12px; " +
                "letter-spacing: 0px; " +
                "word-spacing: 2px; " +
                "color: #3A3A3A; " +
                "font-weight: 400; " +
                "text-decoration: none; " +
                "font-style: normal; " +
                "font-variant: small-caps; " +
                "text-transform: none; "
        );

        TABLE.ROW first_row = tagTABLE.createRow("");
        first_row.insertCell("font-weight: 500; background-color: #E6CCFF; font-size: 18px;", "DRAWING CLEANUP REPORT","7");
        first_row.close();

        DrawingList drawingList = input.getDrawingList();
        ReportMap reportMap = input.getReportMap();

        Integer i = 1;
        for (Map.Entry<String, RevisionList> e : drawingList.getList().entrySet()) {

            TABLE.ROW drawingRow = tagTABLE.createRow("background-color: #DDCCFF;");
            drawingRow.insertCell("width: 50px;", (i++).toString() ,"1");// count
            drawingRow.insertCell("width: 1150px;", e.getKey(),"6"); // drawing id
            drawingRow.close();

            for (Drawing d : e.getValue()){

                TABLE.ROW revisionRow = tagTABLE.createRow("");
                revisionRow.insertCell("width: 50px;", "","1"); // empty cell
                revisionRow.insertCell("width: 50px;", d.getRevision(),"1"); // revision
                revisionRow.insertCell("width: 700px;", d.getTitle(),"1"); // title

                {// PRIMARY LINK
                    String primaryLinkPath = (reportMap.get(d).getTask().getPrimaryPathTrace().getDestinationPath() == null)?
                            reportMap.get(d).getTask().getPrimaryPathTrace().getSourcePath().toString() :
                            reportMap.get(d).getTask().getPrimaryPathTrace().getDestinationPath().toString();

                    primaryLinkPath = "file://" + primaryLinkPath;

                    revisionRow.insertCell("width: 50px;", new LINK(primaryLinkPath, INIFileParser.getParams().get("PRIMARY").toUpperCase(), "").toString(),"1"); // primary link
                }

                {// PRIMARY ACTIONS
                    StringBuilder actionString = new StringBuilder();
                    for (ResultEnum re : reportMap.get(d).getResults().getPrimaryResults()){
                        actionString.append(new ICON(re,20));
                    }
                    actionString.append("\n");

                    revisionRow.insertCell("width: 150px;", actionString.toString(),"1"); // action icons
                }

                if (d.hasSecondary()) {
                     // SECONDARY LINK
                        String secondaryLinkPath = (reportMap.get(d).getTask().getSecondaryPathTrace().getDestinationPath() == null)?
                                reportMap.get(d).getTask().getSecondaryPathTrace().getSourcePath().toString() :
                                reportMap.get(d).getTask().getSecondaryPathTrace().getDestinationPath().toString();

                        secondaryLinkPath = "file://" + secondaryLinkPath;
                        revisionRow.insertCell("width: 50px;", new LINK(secondaryLinkPath, INIFileParser.getParams().get("SECONDARY").toUpperCase(),"").toString(),"1"); // secondary link

                } else
                {
                    revisionRow.insertCell("width: 50px;", "","1"); // no secondary
                }

                {// SECONDARY ACTIONS
                    StringBuilder actionString = new StringBuilder();
                    for (ResultEnum re : reportMap.get(d).getResults().getSecondaryResults()){
                        actionString.append(new ICON(re,20));
                    }

                    revisionRow.insertCell("width: 150px;", actionString.toString(),"1"); // action icons
                }
                revisionRow.close();
            }

        }

        tagBODY.close();
        tagHTML.close();

        String appendDatePattern = "_yyy-mm-dd_hh-mm-ss";
        Calendar now = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(appendDatePattern);
        String timeSignature = dateFormat.format(now.getTime());

        try (PrintWriter pw = new PrintWriter(workingDIR+"\\CLEANUP_REPORT_"+timeSignature+".html")) {

                pw.print(sb.toString());

    } catch (Exception e) { System.err.println(e.getMessage());}
    }
}
