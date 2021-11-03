package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MOMOPayLoginRequest {
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("UserName")
    @Expose
    private String Username;
    @SerializedName("FBToken")
    @Expose
    private String fBToken;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getfBToken() {
        return fBToken;
    }

    public void setfBToken(String fBToken) {
        this.fBToken = fBToken;
    }
}
