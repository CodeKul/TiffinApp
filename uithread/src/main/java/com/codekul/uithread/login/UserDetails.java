package com.codekul.uithread.login;

/**
 * Created by aniruddha on 19/5/17.
 */

public class UserDetails {

    private String userName;
    private String userPassword;


    public UserDetails(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
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
}
