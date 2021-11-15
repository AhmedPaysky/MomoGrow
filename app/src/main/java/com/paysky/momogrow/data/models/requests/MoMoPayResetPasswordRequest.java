package com.paysky.momogrow.data.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoMoPayResetPasswordRequest {

    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("ReferenceNumber")
    @Expose
    private String referenceNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

}
