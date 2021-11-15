package com.paysky.momogrow.data.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoMoPayAuthorizeForResetPasswordRequest {
    @SerializedName("CallBackURL")
    @Expose
    private String callBackURL;
    @SerializedName("Environment")
    @Expose
    private String environment;
    @SerializedName("FBToken")
    @Expose
    private String fBToken;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;

    public String getCallBackURL() {
        return callBackURL;
    }

    public void setCallBackURL(String callBackURL) {
        this.callBackURL = callBackURL;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getFBToken() {
        return fBToken;
    }

    public void setFBToken(String fBToken) {
        this.fBToken = fBToken;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
