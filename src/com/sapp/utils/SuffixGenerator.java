package com.sapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SuffixGenerator {

    private String suffixString;

    public SuffixGenerator() {

        String appendDatePattern = "-yyyMMdd_kkmmss";
        Calendar now = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(appendDatePattern);
        suffixString = dateFormat.format(now.getTime());
    }

    public String getSuffixString() {return suffixString;}

}
