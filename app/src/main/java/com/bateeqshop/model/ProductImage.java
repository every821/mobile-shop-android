package com.bateeqshop.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by Daniel.Nababan on 5/25/2016.
 */
@Table(name = BateeqShopContract.ProductImageDataEntry.TABLE_NAME)
public class ProductImage extends Model  implements Serializable {
    @Column(name = BateeqShopContract.ProductImageDataEntry.COLUMN_CODE)
    private String code;
    @Column(name = BateeqShopContract.ProductImageDataEntry.COLUMN_PRODUCT_CODE)
    private String productCode;
    @Column(name = BateeqShopContract.ProductImageDataEntry.COLUMN_TITLE)
    private String title;
    @Column(name = BateeqShopContract.ProductImageDataEntry.COLUMN_IS_PRIMARY)
    private boolean isPrimary;
//    @Column(name = BateeqShopContract.ProductImageDataEntry.PARENT_SHOPPING_CART_ORDER_DETAIL)
    private ShoppingCartOrderDetail shoppingCartOrderDetail;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public ShoppingCartOrderDetail getShoppingCartOrderDetail() {
        return shoppingCartOrderDetail;
    }

    public void setShoppingCartOrderDetail(ShoppingCartOrderDetail shoppingCartOrderDetail) {
        this.shoppingCartOrderDetail = shoppingCartOrderDetail;
    }
}
