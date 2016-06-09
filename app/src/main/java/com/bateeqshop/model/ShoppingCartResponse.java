package com.bateeqshop.model;

public class ShoppingCartResponse
{
    private String apiVersion;
    private ShoppingCart data;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public ShoppingCart getData() {
        return data;
    }

    public void setData(ShoppingCart data) {
        this.data = data;
    }
}
