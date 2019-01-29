package com.sapp.outputhandlers;

import java.io.PrintWriter;

public class HTMLOutput implements OutputHandler {

    private StringBuilder sb = new StringBuilder();

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

    @Override
    public void outputReport() {

        HTML tagHTML = new HTML();
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
        first_row.insertCell("font-weight: 500; background-color: #E6CCFF; font-size: 18px;", "Raport z wykonanego czyszczenia","6");
        first_row.close();

        TABLE.ROW drawingRow = tagTABLE.createRow("background-color: #DDCCFF;");
        drawingRow.insertCell("width: 50px;", "1","1");// count
        drawingRow.insertCell("width: 1150px;", "T1612-STR-H00-D437","5"); // drawing id
        drawingRow.close();

        TABLE.ROW revisionRow = tagTABLE.createRow("");
        revisionRow.insertCell("width: 50px;", "","1"); // empty cell
        revisionRow.insertCell("width: 50px;", "00","1"); // revision
        revisionRow.insertCell("width: 900px;", "Schody zelbetowe RC stairs H-SCH-18-23-Rysunek zbrojeniowy Reinforcement drawing","1"); // title
        revisionRow.insertCell("width: 50px;", "PDF","1"); // primary link
        revisionRow.insertCell("width: 50px;", "DWG","1"); // secondary link
        revisionRow.insertCell("width: 100px;", "ACTIONS","1"); // action icons
        revisionRow.close();

        tagBODY.close();
        tagHTML.close();

        try (PrintWriter pw = new PrintWriter("D:/test2/html.html")) {

            pw.print(sb.toString());

        } catch (Exception e) {};


    }


}
