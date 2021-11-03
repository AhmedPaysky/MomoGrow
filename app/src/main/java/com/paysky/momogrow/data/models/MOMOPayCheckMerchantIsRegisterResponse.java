package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MOMOPayCheckMerchantIsRegisterResponse {
    @SerializedName("IsRegister")
    @Expose
    private boolean IsRegister;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Success")
    @Expose
    private boolean Success;

    public boolean isRegister() {
        return IsRegister;
    }

    public void setRegister(boolean register) {
        IsRegister = register;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
