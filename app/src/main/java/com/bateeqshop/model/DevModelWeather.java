package com.bateeqshop.model;

public class DevModelWeather
{
    private String cod;
    private String message;
    private String cnt;

    public String getCnt() {
        return cnt;
    }

    public String getCod() {
        return cod;
    }

    public String getMessage() {
        return message;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
