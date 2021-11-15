package com.paysky.momogrow.data.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoMoPayRegisterAccountRequest {
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("RoleType")
    @Expose
    private int RoleType = 1;
    @SerializedName("UserName")
    @Expose
    private String UserName;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
