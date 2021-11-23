package com.paysky.momogrow.data.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paysky.momogrow.utilis.DateTimeUtil;

public class InitiateOrderRequest {
    @SerializedName("DateTimeLocalTrxn")
    @Expose
    private String dateTimeLocalTrxn;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("MerchantId")
    @Expose
    private String merchantId;
    @SerializedName("TerminalId")
    @Expose
    private String terminalId;
    @SerializedName("Amount")
    @Expose
    private Float amount;
    @SerializedName("AmountTrxn")
    @Expose
    private Long amountTrxn;
    @SerializedName("Currency")
    @Expose
    private Integer currency;
    @SerializedName("ExpiryDateTime")
    @Expose
    private String expiryDateTime;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("NotificationMethod")
    @Expose
    private Integer notificationMethod;
    @SerializedName("NotificationValue")
    @Expose
    private String notificationValue;

    public String getDateTimeLocalTrxn() {
        return dateTimeLocalTrxn;
    }

    public void setDateTimeLocalTrxn(String dateTimeLocalTrxn) {
        this.dateTimeLocalTrxn = DateTimeUtil.getDateTimeLocalTrxn();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Long getAmountTrxn() {
        return amountTrxn;
    }

    public void setAmountTrxn(Long amountTrxn) {
        this.amountTrxn = amountTrxn;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(String expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNotificationMethod() {
        return notificationMethod;
    }

    public void setNotificationMethod(Integer notificationMethod) {
        this.notificationMethod = notificationMethod;
    }

    public String getNotificationValue() {
        return notificationValue;
    }

    public void setNotificationValue(String notificationValue) {
        this.notificationValue = notificationValue;
    }
}
