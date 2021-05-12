package com.ankesh.loganalyzer.domain;

import java.util.Date;

public class LoginDetails {
    private Date loginDate;
    private String userName;
    private long loginCount;

    public LoginDetails(Date loginDate, String userName, long loginCount) {
        this.loginDate = loginDate;
        this.userName = userName;
        this.loginCount = loginCount;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(long loginCount) {
        this.loginCount = loginCount;
    }

    @Override
    public String toString() {
        return "LoginDetails{" +
                "loginDate=" + loginDate +
                ", userName='" + userName + '\'' +
                ", loginCount=" + loginCount +
                '}';
    }
}
