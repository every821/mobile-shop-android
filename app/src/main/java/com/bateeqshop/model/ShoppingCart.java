package com.bateeqshop.model;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

public class ShoppingCart
{
    private String code;
    private String customerEmail;
    private String orderDate;
    private String subTotal;
    private String shippingCost;
    private String tax;
    private String orderStatus;
    private String remarks;

    private String ConsignmentFirstName;
    private String ConsignmentLastName;
    private String ConsignmentPhone;
    private String ConsignmentCompany;
    private String ConsignmentAddress;
    private String ConsignmentCity;
    private String ConsignmentZipCode;
    private String ConsignmentCountry;
    private String ConsignmentState;
    private String ConsignmentDistrict;
    private String DiscountCouponCode;


    @SerializedName("orderDetails")
    private List<ShoppingCartOrderDetail> shoppingCartOrderDetails;

    public ShoppingCart()
    {

    }

    public ShoppingCart(ShoppingCartOrderDetail orderDetail)
    {
        setCode(orderDetail.getOrderCode());
        setCustomerEmail(orderDetail.getCustomerEmail());
        setOrderDate(orderDetail.getOrderDate());
        setSubTotal(orderDetail.getSubTotal());
        setShippingCost(orderDetail.getShippingCost());
        setTax(orderDetail.getTax());
        setOrderStatus(orderDetail.getOrderStatus());
        setRemarks(orderDetail.getRemarks());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<ShoppingCartOrderDetail> getShoppingCartOrderDetails() {
        return shoppingCartOrderDetails;
    }

    public void setShoppingCartOrderDetails(List<ShoppingCartOrderDetail> shoppingCartOrderDetails) {
        this.shoppingCartOrderDetails = shoppingCartOrderDetails;
    }

    public String getConsignmentFirstName() {
        return ConsignmentFirstName;
    }

    public void setConsignmentFirstName(String consignmentFirstName) {
        ConsignmentFirstName = consignmentFirstName;
    }

    public String getConsignmentLastName() {
        return ConsignmentLastName;
    }

    public void setConsignmentLastName(String consignmentLastName) {
        ConsignmentLastName = consignmentLastName;
    }

    public String getConsignmentPhone() {
        return ConsignmentPhone;
    }

    public void setConsignmentPhone(String consignmentPhone) {
        ConsignmentPhone = consignmentPhone;
    }

    public String getConsignmentCompany() {
        return ConsignmentCompany;
    }

    public void setConsignmentCompany(String consignmentCompany) {
        ConsignmentCompany = consignmentCompany;
    }

    public String getConsignmentAddress() {
        return ConsignmentAddress;
    }

    public void setConsignmentAddress(String consignmentAddress) {
        ConsignmentAddress = consignmentAddress;
    }

    public String getConsignmentCity() {
        return ConsignmentCity;
    }

    public void setConsignmentCity(String consignmentCity) {
        ConsignmentCity = consignmentCity;
    }

    public String getConsignmentZipCode() {
        return ConsignmentZipCode;
    }

    public void setConsignmentZipCode(String consignmentZipCode) {
        ConsignmentZipCode = consignmentZipCode;
    }

    public String getConsignmentCountry() {
        return ConsignmentCountry;
    }

    public void setConsignmentCountry(String consignmentCountry) {
        ConsignmentCountry = consignmentCountry;
    }

    public String getConsignmentState() {
        return ConsignmentState;
    }

    public void setConsignmentState(String consignmentState) {
        ConsignmentState = consignmentState;
    }

    public String getConsignmentDistrict() {
        return ConsignmentDistrict;
    }

    public void setConsignmentDistrict(String consignmentDistrict) {
        ConsignmentDistrict = consignmentDistrict;
    }

    public String getDiscountCouponCode() {
        return DiscountCouponCode;
    }

    public void setDiscountCouponCode(String discountCouponCode) {
        DiscountCouponCode = discountCouponCode;
    }

    public static class ShoppingCartSerializer implements JsonSerializer<ShoppingCart> {
        @Override
        public JsonElement serialize(ShoppingCart src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("code",new JsonPrimitive(src.getCode()));
            result.add("customerEmail",new JsonPrimitive(src.getCustomerEmail()));
            result.add("orderDate",new JsonPrimitive(src.getOrderDate()));
            result.add("subTotal",new JsonPrimitive(src.getSubTotal()));
            result.add("shippingCost",new JsonPrimitive(src.getShippingCost()));
            result.add("tax",new JsonPrimitive(src.getTax()));
            result.add("orderStatus",new JsonPrimitive(src.getOrderStatus()));
            result.add("remarks",new JsonPrimitive(src.getRemarks()));
            com.google.gson.Gson gson = new GsonBuilder().registerTypeAdapter(ShoppingCartOrderDetail.class, new ShoppingCartOrderDetail.ShoppingCartOrderDetailSerializer())
                    .create();
            result.add("orderDetails", gson.toJsonTree(src.getShoppingCartOrderDetails()));
            result.add("ConsignmentFirstName", new JsonPrimitive(src.getConsignmentFirstName()));
            result.add("ConsignmentLastName", new JsonPrimitive(src.getConsignmentLastName()));
            result.add("ConsignmentPhone", new JsonPrimitive(src.getConsignmentPhone()));
            result.add("ConsignmentCompany", new JsonPrimitive(src.getConsignmentCompany()));
            result.add("ConsignmentAddress", new JsonPrimitive(src.getConsignmentAddress()));
            result.add("ConsignmentCity", new JsonPrimitive(src.getConsignmentCity()));
            result.add("ConsignmentZipCode", new JsonPrimitive(src.getConsignmentZipCode()));
            result.add("ConsignmentCountry", new JsonPrimitive(src.getConsignmentCountry()));
            result.add("ConsignmentState", new JsonPrimitive(src.getConsignmentState()));
            result.add("ConsignmentDistrict", new JsonPrimitive(src.getConsignmentDistrict()));
            result.add("DiscountCouponCode", new JsonPrimitive(src.getDiscountCouponCode()));
            return result;
        }
    }
}
