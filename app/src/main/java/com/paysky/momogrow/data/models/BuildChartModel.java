package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuildChartModel {
    @SerializedName("TotalCountAllTransaction")
    @Expose
    private Integer totalCount;
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @SerializedName("TotalAmountAllTransaction")
    @Expose
    private String totalAmount;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Success")
    @Expose
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setTransactionsByDurationCategory(BuildChartSubModel transactionsByDurationCategory) {
        this.transactionsByDurationCategory = transactionsByDurationCategory;
    }

    public BuildChartSubModel getTransactionsByDurationCategory() {
        return transactionsByDurationCategory;
    }

    @SerializedName("TransactionsByDurationCategory")
    @Expose
    private BuildChartSubModel transactionsByDurationCategory;
}