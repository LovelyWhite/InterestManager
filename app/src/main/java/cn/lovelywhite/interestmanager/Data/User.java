package cn.lovelywhite.interestmanager.Data;

import java.sql.Timestamp;

public class User {
    private String userEmail;
    private String userName;
    private String userPassword;
    private Timestamp userCreateTime;//日期和时间
    private String userHeadImage;
    private int userType;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Timestamp getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Timestamp userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}