package com.bateeqshop;

import android.test.AndroidTestCase;

import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.model.DashboardModel;
import com.bateeqshop.model.MergeModel;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.model.UserProfile;
import com.bateeqshop.model.UserProfileData;
import com.bateeqshop.rest.RestProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class TestResources extends AndroidTestCase
{
    public void testGetDashboardItems() throws IOException
    {
        Call<DashboardModel> call = RestProvider.getResourceRest().getDashboardItems();
        DashboardModel model = call.execute().body();
        assertTrue(model != null);
    }

    public void testMergeCart() throws IOException
    {
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOiI0ZTJkNWFkNS0yZjZiLTQxYjYtOGJhMi1hZmI0MGM4ZGQ3NWIiLCJ1bmlxdWVfbmFtZSI6InRlc3RAdGVzdC5jb20iLCJyb2xlIjoiQ3VzdG9tZXIiLCJpc3MiOiJsb2NhbGhvc3QiLCJhdWQiOiIwOTkxNTNjMjYyNTE0OWJjOGVjYjNlODVlMDNmMDAyMiIsImV4cCI6MTQ2NTI3Njk5NywibmJmIjoxNDY1Mjc1MTk3fQ.k60iAXeqfD56j97U3VNGyBeOp7eTVKOVjsqsost4KNQ";
        String headerAuth = "bearer " + accessToken;
        List<MergeModel> orderDetailList = new ArrayList<>();
        MergeModel orderDetail1 = new MergeModel();
        orderDetail1.setProductCode(ApiConfig.DEV_PRODUCT_CODE_1);
        orderDetail1.setProductColorCode(ApiConfig.DEV_PRODUCT_COLOR_CODE_1);
        orderDetail1.setProductSizeCode(ApiConfig.DEV_PRODUCT_SIZE_CODE_1);
        MergeModel orderDetail2 = new MergeModel();
        orderDetail2.setProductCode(ApiConfig.DEV_PRODUCT_CODE_2);
        orderDetail2.setProductColorCode(ApiConfig.DEV_PRODUCT_COLOR_CODE_2);
        orderDetail2.setProductSizeCode(ApiConfig.DEV_PRODUCT_SIZE_CODE_2);
        MergeModel orderDetail3 = new MergeModel();
        orderDetail3.setProductCode(ApiConfig.DEV_PRODUCT_CODE_3);
        orderDetail3.setProductColorCode(ApiConfig.DEV_PRODUCT_COLOR_CODE_3);
        orderDetail3.setProductSizeCode(ApiConfig.DEV_PRODUCT_SIZE_CODE_3);
        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetail2);
        orderDetailList.add(orderDetail3);

        Call<UserProfile> call = RestProvider.getResourceRest().mergeShoppingCart(headerAuth, orderDetailList);
        Response<UserProfile> response = call.execute();
        UserProfile userProfile = response.body();
        assertTrue(userProfile != null);
    }


}
