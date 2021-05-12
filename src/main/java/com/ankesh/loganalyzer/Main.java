package com.ankesh.loganalyzer;

import com.ankesh.loganalyzer.domain.Log;
import com.ankesh.loganalyzer.domain.LoginDetails;
import com.ankesh.loganalyzer.exception.LoginDetailsException;
import com.ankesh.loganalyzer.exception.ParseFailureException;
import com.ankesh.loganalyzer.service.LogParserService;
import com.ankesh.loganalyzer.service.LoginDetailsService;
import com.ankesh.loganalyzer.utils.DateUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static final int MIN_LOGINS_TO_BE_PRINTED = 2;
    public static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws IOException {
        //String logFileAbsolutePath = args[0];
        String logFileAbsolutePath = "C:\\NisarWorkspace\\CodePI17\\LogAnalyzer\\src\\main\\resources\\server.log";
        File file = new File(logFileAbsolutePath);
        LogParserService logParserService = new LogParserService();
        LoginDetailsService loginDetailsService = new LoginDetailsService();
        List<Log> logs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Log log = null;
                try {
                    log = logParserService.parseLog(line);
                } catch (ParseFailureException e) {
                    logger.log(Level.SEVERE, "error while parsing line {}",line);
                    e.printStackTrace();
                }
                if (log != null) {
                    logs.add(log);
                }
            }
        }
        List<LoginDetails> loginDetailsList = null;
        try {
            loginDetailsList = loginDetailsService.buildDatewiseUserLoginDetails(logs);
        } catch (LoginDetailsException e) {
            logger.log(Level.SEVERE, "error while building datewise user login details {}",loginDetailsList);
            e.printStackTrace();
        }
        printLoginDetailsOutput(loginDetailsList);
    }

    private static void printLoginDetailsOutput(List<LoginDetails> loginDetailsList) {
        System.out.println("Date" + "\t\t"+"User" + "\t\t"+"Login Count");
        for (LoginDetails loginDetails : loginDetailsList) {
            if (loginDetails.getLoginCount() >= MIN_LOGINS_TO_BE_PRINTED) {
                String formattedDate = DateUtils.buildFormattedDate(loginDetails.getLoginDate());
                System.out.println(formattedDate + "\t\t"+loginDetails.getUserName() + "\t\t"+loginDetails.getLoginCount());
            }
        }
    }




}
