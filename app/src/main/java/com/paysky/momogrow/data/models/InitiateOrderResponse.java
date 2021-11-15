package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitiateOrderResponse {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("ISOQR")
    @Expose
    private String isoqr;
    @SerializedName("IsFromQRLink")
    @Expose
    private Boolean isFromQRLink;
    @SerializedName("OrderId")
    @Expose
    private String orderId;
    @SerializedName("OrderRefId")
    @Expose
    private String orderRefId;
    @SerializedName("OrderURL")
    @Expose
    private String orderURL;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getIsoqr() {
        return isoqr;
    }

    public void setIsoqr(String isoqr) {
        this.isoqr = isoqr;
    }

    public Boolean getIsFromQRLink() {
        return isFromQRLink;
    }

    public void setIsFromQRLink(Boolean isFromQRLink) {
        this.isFromQRLink = isFromQRLink;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderRefId() {
        return orderRefId;
    }

    public void setOrderRefId(String orderRefId) {
        this.orderRefId = orderRefId;
    }

    public String getOrderURL() {
        return orderURL;
    }

    public void setOrderURL(String orderURL) {
        this.orderURL = orderURL;
    }

}
