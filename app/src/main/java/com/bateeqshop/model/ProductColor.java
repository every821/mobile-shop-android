package com.bateeqshop.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by Daniel.Nababan on 5/26/2016.
 */
@Table(name = BateeqShopContract.ProductColorDataEntry.TABLE_NAME)
public class ProductColor extends Model  implements Serializable {
    @Column(name = BateeqShopContract.ProductColorDataEntry.COLUMN_CODE)
    private String code;
    @Column(name = BateeqShopContract.ProductColorDataEntry.COLUMN_PRODUCT_CODE)
    private String productCode;
    @Column(name = BateeqShopContract.ProductColorDataEntry.COLUMN_COLOR)
    private String color;
    @Column(name = BateeqShopContract.ProductColorDataEntry.COLUMN_SEQUENCE)
    private int sequence;
//    @Column(name = BateeqShopContract.ProductColorDataEntry.PARENT_SHOPPING_CART_ORDER_DETAIL)
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public ShoppingCartOrderDetail getShoppingCartOrderDetail() {
        return shoppingCartOrderDetail;
    }

    public void setShoppingCartOrderDetail(ShoppingCartOrderDetail shoppingCartOrderDetail) {
        this.shoppingCartOrderDetail = shoppingCartOrderDetail;
    }
}