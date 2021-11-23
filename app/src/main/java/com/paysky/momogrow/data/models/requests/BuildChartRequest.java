package com.paysky.momogrow.data.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paysky.momogrow.utilis.DateTimeUtil;

public class BuildChartRequest {
    @SerializedName("DateTimeLocalTrxn")
    @Expose
    private String dateTimeLocalTrxn;
    @SerializedName("DurationType")
    @Expose
    private Integer durationType = 1;
    @SerializedName("MerchantId")
    @Expose
    private String merchantId;
    @SerializedName("TerminalId")
    @Expose
    private String terminalId;

    public String getDateTimeLocalTrxn() {
        return dateTimeLocalTrxn;
    }

    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setDateTimeLocalTrxn(String dateTimeLocalTrxn) {
        this.dateTimeLocalTrxn = dateTimeLocalTrxn;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
}
