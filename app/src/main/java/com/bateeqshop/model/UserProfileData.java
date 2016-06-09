package com.bateeqshop.model;


import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = BateeqShopContract.UserProfileDataEntry.TABLE_NAME, id = BaseColumns._ID)
public class UserProfileData extends Model
{
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_EMAIL)
    private String email;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_FIRST_NAME)
    private String firstName;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_LAST_NAME)
    private String lastName;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_PHONE)
    private String phone;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_COMPANY)
    private String company;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_ADDRESS)
    private String address;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_CITY)
    private String city;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_ZIP_CODE)
    private String zipCode;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_COUNTRY_NAME)
    private String countryName;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_STATE_NAME)
    private String stateName;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_DISTRICT_NAME)
    private String districtName;
    @Column(name = BateeqShopContract.UserProfileDataEntry.COLUMN_USER_ID)
    private String userId;

    @SerializedName("carts")
    private List<ShoppingCart> shoppingCarts;
    @SerializedName("wishlists")
    private List<WishList> wishLists;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }
}
