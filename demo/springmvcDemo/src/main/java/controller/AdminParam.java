package controller;

import org.springframework.stereotype.Component;

/**
 * Created by jinhua.chen on 2018/4/30.
 */
public class AdminParam {
    private String username;
    private String pwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
