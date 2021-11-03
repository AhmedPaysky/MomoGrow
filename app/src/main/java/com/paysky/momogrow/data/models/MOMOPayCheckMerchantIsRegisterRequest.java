package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MOMOPayCheckMerchantIsRegisterRequest {
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
}
