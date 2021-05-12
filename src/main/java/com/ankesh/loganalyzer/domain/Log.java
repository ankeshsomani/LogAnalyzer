package com.ankesh.loganalyzer.domain;

import java.util.Date;

public class Log {

    private String userName;
    private String message;
    private String level;
    private Date date;
    private String threadId;

    public Log(String userName, String message, String level, Date date, String threadId) {
        this.userName = userName;
        this.message = message;
        this.level = level;
        this.date = date;
        this.threadId = threadId;
    }

    @Override
    public String toString() {
        return "Log{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", level='" + level + '\'' +
                ", date=" + date +
                ", threadId='" + threadId + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
}
