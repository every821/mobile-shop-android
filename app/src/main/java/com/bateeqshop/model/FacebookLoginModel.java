package com.bateeqshop.model;

public class FacebookLoginModel
{
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String externalAccessToken;
    private String provider;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getExternalAccessToken() {
        return externalAccessToken;
    }

    public void setExternalAccessToken(String externalAccessToken) {
        this.externalAccessToken = externalAccessToken;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
