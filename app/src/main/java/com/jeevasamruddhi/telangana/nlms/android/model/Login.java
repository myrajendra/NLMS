package com.jeevasamruddhi.telangana.nlms.android.model;

/**
 * Created by s on 11/14/2016.
 */

public class Login extends BaseModel
{
    private String userName;
    private String password;

    public Login(){}
    public Login(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
