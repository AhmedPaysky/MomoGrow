package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoMoPayRegisterResponse {
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("ReferenceNumber")
    @Expose
    private String ReferenceNumber;
    @SerializedName("Success")
    @Expose
    private boolean Success;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getReferenceNumber() {
        return ReferenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        ReferenceNumber = referenceNumber;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
