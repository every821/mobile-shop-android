package com.bateeqshop.model;

public class DashboardModel
{
    private String apiVersion;
    private DashboardDataModel data;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public DashboardDataModel getData() {
        return data;
    }

    public void setData(DashboardDataModel data) {
        this.data = data;
    }
}
