package com.bateeqshop;

import com.bateeqshop.model.ProductSize;
import com.bateeqshop.model.Query;
import com.bateeqshop.model.SessionModel;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.model.ShoppingCartResponse;
import com.bateeqshop.model.UserProfileData;
import com.bateeqshop.model.WishList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Session {
    /**Auth*/
    public static String accessToken;
    public static String tokenType;
    public static Integer expiredIn;
    public static String refreshToken;
    public static String issuedDate;
    public static String expiredDate;
    public static Long deviceBasedExpiredDate;

    /**User Profile*/
    public static String email;
    public static String firstName;
    public static String lastName;
    public static String phone;
    public static String company;
    public static String address;
    public static String city;
    public static String zipCode;
    public static String countryName;
    public static String stateName;
    public static String districtName;
    public static String userId;

    /**Shopping Cart*/
    public static ShoppingCart shoppingCart;
    public static List<ShoppingCartOrderDetail> shoppingCartOrderDetails;
    private static Integer shoppingCartOrderDetailNumber;

    /**Wish List*/
    public static List<WishList> wishLists;
    private static Integer wishListNumber;

    public static Integer getWishListNumber() {
        return wishListNumber;
    }

    public static void setWishListNumber(Integer wishListNumber) {
        if (wishListNumber <= 0) Session.wishListNumber = Integer.MIN_VALUE;
        else Session.wishListNumber = wishListNumber;
    }

    public static Integer getShoppingCartOrderDetailNumber() {
        return shoppingCartOrderDetailNumber;
    }

    public static void setShoppingCartOrderDetailNumber(Integer shoppingCartOrderDetailNumber) {
        if (shoppingCartOrderDetailNumber <= 0) Session.shoppingCartOrderDetailNumber = Integer.MIN_VALUE;
        else Session.shoppingCartOrderDetailNumber = shoppingCartOrderDetailNumber;
    }

    public static Boolean isValid()
    {
        SessionModel sessionModel = (SessionModel) Query.getSingle(SessionModel.class);

        if (sessionModel != null)
        {
            login(sessionModel);
            Long nowInMillis = Calendar.getInstance().getTimeInMillis();
            Date now = new Date(nowInMillis);
            Date expDate = new Date(sessionModel.getDeviceBasedExpiredDate());
            return accessToken.length() > 0 && now.before(expDate);
        }
        return false;
    }

    public static void login(SessionModel sessionModel)
    {
        accessToken = sessionModel.getAccessToken();
        tokenType = sessionModel.getTokenType();
        expiredIn = sessionModel.getExpiredIn();
        refreshToken = sessionModel.getRefreshToken();
        issuedDate = sessionModel.getIssuedDate();
        expiredDate = sessionModel.getExpiredDate();
        deviceBasedExpiredDate = sessionModel.getDeviceBasedExpiredDate();
    }

    public static void updateSession()
    {
        SessionModel sessionModel = (SessionModel)Query.getSingle(SessionModel.class);
        if (sessionModel == null) sessionModel = new SessionModel();
        sessionModel.setAccessToken(accessToken);
        sessionModel.setTokenType(tokenType);
        sessionModel.setExpiredIn(expiredIn);
        sessionModel.setRefreshToken(refreshToken);
        sessionModel.setIssuedDate(issuedDate);
        sessionModel.setExpiredDate(expiredDate);
        sessionModel.setDeviceBasedExpiredDate(deviceBasedExpiredDate);
        sessionModel.save();
    }

    public static void setUserProfile(UserProfileData userProfileData)
    {
        email = userProfileData.getEmail();
        firstName = userProfileData.getFirstName();
        lastName = userProfileData.getLastName();
        phone = userProfileData.getPhone();
        company = userProfileData.getCompany();
        address = userProfileData.getAddress();
        city = userProfileData.getCity();
        zipCode = userProfileData.getZipCode();
        countryName = userProfileData.getCountryName();
        stateName = userProfileData.getStateName();
        districtName = userProfileData.getDistrictName();
        userId = userProfileData.getUserId();
    }

    public static void updateUserProfile()
    {
        UserProfileData userProfileData = (UserProfileData)Query.getSingle(UserProfileData.class);
        if (userProfileData == null) userProfileData = new UserProfileData();
        userProfileData.setEmail(email);
        userProfileData.setFirstName(firstName);
        userProfileData.setLastName(lastName);
        userProfileData.setPhone(phone);
        userProfileData.setCompany(company);
        userProfileData.setAddress(address);
        userProfileData.setCity(city);
        userProfileData.setZipCode(zipCode);
        userProfileData.setCountryName(countryName);
        userProfileData.setStateName(stateName);
        userProfileData.setDistrictName(districtName);
        userProfileData.setUserId(userId);
        userProfileData.save();
    }

    public static void setShoppingCart(ShoppingCart shoppingCart)
    {
        Session.shoppingCart = shoppingCart;
        Session.shoppingCartOrderDetails = shoppingCart.getShoppingCartOrderDetails();
        setShoppingCartOrderDetailNumber(shoppingCart.getShoppingCartOrderDetails().size());
    }

    public static void saveShoppingCarts()
    {
        for(ShoppingCartOrderDetail orderDetail : shoppingCart.getShoppingCartOrderDetails())
        {
            orderDetail.setCustomerEmail(shoppingCart.getCustomerEmail());
            orderDetail.setOrderDate(shoppingCart.getOrderDate());
            orderDetail.setSubTotal(shoppingCart.getSubTotal());
            orderDetail.setShippingCost(shoppingCart.getShippingCost());
            orderDetail.setTax(shoppingCart.getTax());
            orderDetail.setOrderStatus(shoppingCart.getOrderStatus());
            orderDetail.setRemarks(shoppingCart.getRemarks());
            orderDetail.save();
        }
    }

    public static void updateShoppingCarts(ShoppingCart shoppingCart)
    {
        Query.truncate(ShoppingCartOrderDetail.class);
        setShoppingCart(shoppingCart);
        saveShoppingCarts();
    }

    public static void addOrderDetailLocal(ShoppingCartOrderDetail orderDetail)
    {
        Session.shoppingCartOrderDetails.add(orderDetail);
        Session.shoppingCart.setShoppingCartOrderDetails(Session.shoppingCartOrderDetails);
        setShoppingCartOrderDetailNumber(Session.shoppingCartOrderDetails.size());
        saveShoppingCarts();
    }

    public static void deleteOrderDetailLocal(long id)
    {
        ShoppingCartOrderDetail.deleteItemById(id);
        ShoppingCart shoppingCart;
        List<ShoppingCartOrderDetail> orderDetailList = ShoppingCartOrderDetail.getAll();
        if (orderDetailList != null && orderDetailList.size() > 0) {
            shoppingCart = new ShoppingCart(orderDetailList.get(0));
            shoppingCart.setShoppingCartOrderDetails(orderDetailList);
        }
        else
        {
            shoppingCart = new ShoppingCart();
            shoppingCart.setShoppingCartOrderDetails(new ArrayList<ShoppingCartOrderDetail>());
        }
        Session.setShoppingCart(shoppingCart);
    }

    public static void setWishlists(List<WishList> wishlists)
    {
        Session.wishLists = wishlists;
        setWishListNumber(wishlists.size());
    }

    public static void addWishListItem(WishList wishListItem)
    {
        wishListItem.save();
        Session.wishLists.add(wishListItem);
    }

    public static void deleteWishListItem(String code)
    {
        WishList.deleteItem(code);
        setWishlists(WishList.getAll());
    }

    public static void saveWishLists()
    {
        for(WishList wishList : wishLists)
        {
            wishList.save();
        }
    }

    public static void updateWishList(List<WishList> wishLists)
    {
        Query.truncate(WishList.class);
        setWishlists(wishLists);
        saveWishLists();
    }

    public static String getHeaderAuthorization()
    {
        return tokenType + " " + accessToken;
    }

    public static String loginInfo()
    {
        return "accessToken : " + Session.accessToken + ", " +
                "refreshToken : " + Session.refreshToken + ", " +
                "expiredIn : " + Session.expiredIn + ", " +
                "expiredDate : " + Session.expiredDate + ", " +
                "issuedDate : " + Session.issuedDate;
    }

    public static String userInfo()
    {
        return "Email : " + Session.email + ", " +
                "First Name : " + Session.firstName + ", " +
                "Last Name : " + Session.lastName + ", " +
                "Phone : " + Session.phone + ", " +
                "Company : " + Session.company + ", " +
                "Address : " + Session.address + ", " +
                "City : " + Session.city + ", " +
                "Zip Code : " + Session.zipCode + ", " +
                "Country Name : " + Session.countryName + ", " +
                "State Name : " + Session.stateName + ", " +
                "District Name : " + Session.districtName + ", " +
                "User ID : " + Session.userId + ", " +
                "Shopping Cart Number : " + Session.getShoppingCartOrderDetailNumber() + ", " +
                "Wish List Number : " + Session.getWishListNumber();
    }

    public static void logOut()
    {
        accessToken = "";
        tokenType = "";
        expiredIn = 0;
        refreshToken = "";
        issuedDate = "";
        expiredDate = "";
        deviceBasedExpiredDate = 0L;

        email = "";
        firstName = "";
        lastName = "";
        phone = "";
        company = "";
        address = "";
        city = "";
        zipCode = "";
        countryName = "";
        stateName = "";
        districtName = "";
        userId = "";

        initialize();
    }

    public static void initialize()
    {
        shoppingCart = new ShoppingCart();
        shoppingCartOrderDetails = new ArrayList<>();
        shoppingCart.setShoppingCartOrderDetails(shoppingCartOrderDetails);
        wishLists = new ArrayList<>();
        setShoppingCartOrderDetailNumber(0);
        setWishListNumber(0);
    }
}
