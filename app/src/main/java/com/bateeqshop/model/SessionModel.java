package com.bateeqshop.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

@Table(name = BateeqShopContract.SessionItemEntry.TABLE_NAME)
public class SessionModel extends Model
{
    private static final Long EARLIER_EXPIRED_DATE_ADJUSTOR = 0L;

    @SerializedName("access_token")
    @Column(name = BateeqShopContract.SessionItemEntry.COLUMN_ACCESS_TOKEN)
    private String accessToken;
    @SerializedName("token_type")
    @Column(name = BateeqShopContract.SessionItemEntry.COLUMN_TOKEN_TYPE)
    private String tokenType;
    @SerializedName("expires_in")
    @Column(name = BateeqShopContract.SessionItemEntry.COLUMN_EXPIRED_IN)
    private Integer expiredIn;
    @SerializedName("refresh_token")
    @Column(name = BateeqShopContract.SessionItemEntry.COLUMN_REFRESH_TOKEN)
    private String refreshToken;
    @SerializedName(".issued")
    @Column(name = BateeqShopContract.SessionItemEntry.COLUMN_ISSUED_DATE)
    private String issuedDate;
    @SerializedName(".expires")
    @Column(name = BateeqShopContract.SessionItemEntry.COLUMN_EXPIRED_DATE)
    private String expiredDate;

    @Column(name = BateeqShopContract.SessionItemEntry.COLUMN_DEVICE_BASED_EXPIRED_DATE)
    private Long deviceBasedExpiredDate;

    public SessionModel()
    {
        super();
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken){ this.accessToken = accessToken; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Integer getExpiredIn() { return expiredIn; }
    public void setExpiredIn(Integer expiredIn) { this.expiredIn = expiredIn; }
    public void setExpiredIn(String expiredIn) { this.expiredIn = Integer.parseInt(expiredIn); }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public String getIssuedDate() {return issuedDate;}
    public void setIssuedDate(String issuedDate) {this.issuedDate = issuedDate;}

    public String getExpiredDate() {return expiredDate;}
    public void setExpiredDate(String expiredDate) {this.expiredDate = expiredDate;}

    public Long getDeviceBasedExpiredDate() {
        return deviceBasedExpiredDate;
    }

    public void setDeviceBasedExpiredDate(Long deviceBasedExpiredDate) {
        this.deviceBasedExpiredDate = deviceBasedExpiredDate;
    }

    public void setDeviceBasedExpiredDate()
    {
        this.deviceBasedExpiredDate = Calendar.getInstance().getTimeInMillis() + (getExpiredIn() * 1000) - EARLIER_EXPIRED_DATE_ADJUSTOR;
    }
}
