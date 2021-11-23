package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BuildChartSubModel {
    @SerializedName("ChartDataMobile")
    @Expose
    private ArrayList<BuildChartData> chartDataMobile;

    public ArrayList<BuildChartData> getChartDataMobile() {
        return chartDataMobile;
    }

    public void setChartDataMobile(ArrayList<BuildChartData> chartDataMobile) {
        this.chartDataMobile = chartDataMobile;
    }
}