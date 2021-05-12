package com.ankesh.loganalyzer.service;

import com.ankesh.loganalyzer.Main;
import com.ankesh.loganalyzer.domain.Log;
import com.ankesh.loganalyzer.domain.LoginDetails;
import com.ankesh.loganalyzer.exception.LoginDetailsException;
import com.ankesh.loganalyzer.exception.ParseFailureException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class LoginDetailsService {
    public static final String LOGGED_IN = "Logged in";
    public static final String LOGGED_OUT = "Logged out";
    public static final String INFO = "INFO";

    public List<LoginDetails> buildDatewiseUserLoginDetails(List<Log> logs) throws LoginDetailsException{
        List<LoginDetails> loginDetailsList = new ArrayList<>();
        try {
            Map<Date, List<Log>> dateWiseLogs = logs.stream().filter(this::isInfoLevelAndUserLoginOrLogOutMessage).collect(groupingBy(Log::getDate));
            for (Map.Entry<Date, List<Log>> specificDateLog : dateWiseLogs.entrySet()) {
                Map<String, List<Log>> userWiseLogs = specificDateLog.getValue().stream().collect(groupingBy(Log::getUserName));
                for (Map.Entry<String, List<Log>> specificUserLog : userWiseLogs.entrySet()) {
                    long loggedInCount = specificUserLog.getValue().stream().filter(this::isLoginMessage).count();
                    long loggedOutCount = specificUserLog.getValue().stream().filter(this::isLogoutMessage).count();
                    long userLoginCount = Math.min(loggedInCount, loggedOutCount);
                    if (userLoginCount > 0) {
                        loginDetailsList.add(new LoginDetails(specificDateLog.getKey(), specificUserLog.getKey(), userLoginCount));
                    }
                }
            }
        }
        catch (Exception ex){
            throw new LoginDetailsException("Error while building login details "+loginDetailsList, ex.getCause());
        }
        return loginDetailsList;
    }

    private boolean isInfoLevelAndUserLoginOrLogOutMessage(Log log) {
        return log.getLevel().equals(INFO) && (isLoginMessage(log) || isLogoutMessage(log));
    }

    private boolean isLogoutMessage(Log log) {
        return log.getMessage().equals(LOGGED_OUT);
    }

    private boolean isLoginMessage(Log log) {
        return log.getMessage().equals(LOGGED_IN);
    }
}
