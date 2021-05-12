package com.ankesh.loganalyzer.service;

import com.ankesh.loganalyzer.domain.Log;
import com.ankesh.loganalyzer.exception.ParseFailureException;
import com.ankesh.loganalyzer.utils.DateUtils;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParserService {
    public static final String SPACE_DELIMETER = " ";
    public static final String COLON_DELIMETER = ":";
    private static final String timestampRegex = "(?<timestamp>^[a-zA-Z]+ [a-zA-Z]+ (0?[1-9]|[12][0-9]|3[01]) ([0-9]+(:[0-9]+)+)\\.[0-9]+ [0-9]+ )";
    private static final String logLevelRegex = "(?<level>TRACE|DEBUG|INFO|NOTICE|WARN|ERROR|SEVERE|FATAL) ";
    private static final String threadRegex = " (?<thread><THREADID: [0-9]+>) ";
    private static final String userDetailsRegex = "(?<userDetails><USER: [a-zA-Z]+>) ";
    private static final String messageRegex = "(?<message><MSG: .*>)";
    private static final Pattern pattern = Pattern.compile(timestampRegex + logLevelRegex + threadRegex + userDetailsRegex + messageRegex, Pattern.CASE_INSENSITIVE);


    public Log parseLog(String line) throws ParseFailureException {
        Log log = null;
        try {
            line.replaceAll("\\s{2,}", " ").trim();
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String[] timestampDetails = matcher.group("timestamp").split(SPACE_DELIMETER);
                String logLevel = matcher.group("level");
                String userDetails = matcher.group("userDetails");
                String threadDetails = matcher.group("thread");
                String messageDetails = matcher.group("message");

                String user = userDetails.split(COLON_DELIMETER)[1].trim();
                String threadId = threadDetails.split(COLON_DELIMETER)[1].trim();
                Date date = DateUtils.buildDate(timestampDetails[1], timestampDetails[2], timestampDetails[4]);
                log = new Log(removeLastChar(user), removeLastChar(messageDetails.split(COLON_DELIMETER)[1].trim()),
                        logLevel, date, removeLastChar(threadId));
            }
        }
        catch(Exception ex){
            throw new ParseFailureException("Error while parsing line "+line, ex.getCause());
        }
        return log;
    }

    public String removeLastChar(String s) {
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }

}
