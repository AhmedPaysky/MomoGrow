package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MOMOPayLoginResponse {
    @SerializedName("AuthToken")
    @Expose
    private String AuthToken;
    @SerializedName("MerchantId")
    @Expose
    private String MerchantId;
    @SerializedName("TerminalId")
    @Expose
    private String TerminalId;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Success")
    @Expose
    private boolean Success;

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean getSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public boolean isSuccess() {
        return Success;
    }
}
