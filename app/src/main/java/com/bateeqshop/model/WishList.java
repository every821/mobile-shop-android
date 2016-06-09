package com.bateeqshop.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = BateeqShopContract.WishListDataEntry.TABLE_NAME, id = BaseColumns._ID)
public class WishList extends Model
{
    @Column(name = BateeqShopContract.WishListDataEntry.COLUMN_CODE)
    private String code;
    @Column(name = BateeqShopContract.WishListDataEntry.COLUMN_EMAIL)
    private String email;
    @Column(name = BateeqShopContract.WishListDataEntry.COLUMN_PRODUCT_CODE)
    private String productCode;
    @Column(name = BateeqShopContract.WishListDataEntry.COLUMN_PRODUCT_SIZE_CODE)
    private String productSizeCode;
    @Column(name = BateeqShopContract.WishListDataEntry.COLUMN_PRODUCT_COLOR_CODE)
    private String productColorCode;
    @Column(name = BateeqShopContract.WishListDataEntry.COLUMN_IS_ACTIVE)
    private Boolean isActive;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductSizeCode() {
        return productSizeCode;
    }

    public void setProductSizeCode(String productSizeCode) {
        this.productSizeCode = productSizeCode;
    }

    public String getProductColorCode() {
        return productColorCode;
    }

    public void setProductColorCode(String productColorCode) {
        this.productColorCode = productColorCode;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public static List<WishList> getAll()
    {
        return new Select().from(WishList.class).execute();
    }

    public static void deleteItem(String code)
    {
        new Delete().from(WishList.class).where(BateeqShopContract.WishListDataEntry.COLUMN_CODE + " = ?", code).execute();
    }
}
