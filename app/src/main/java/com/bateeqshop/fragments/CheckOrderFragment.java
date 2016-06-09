package com.bateeqshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bateeqshop.R;
import com.bateeqshop.Session;
import com.bateeqshop.VeritransActivity;
import com.bateeqshop.adapter.CheckOrderAdapter;
import com.bateeqshop.model.RequestData;
import com.bateeqshop.model.RequestObjectList;
import com.bateeqshop.model.ResponseData;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.rest.RestProvider;
import com.bateeqshop.utility.NumberFormatter;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daniel.Nababan on 6/7/2016.
 */
public class CheckOrderFragment extends Fragment {

    Context mContext;
    CheckOrderAdapter checkOrderAdapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_check_order, null);
        initializeListProduct(rootView, savedInstanceState);
        intializeShippingAddress(rootView);
        initializeNextButton(rootView);

        return rootView;
    }

    public void initializeListProduct(View rootView,Bundle savedInstanceState){
        listView = (ListView) rootView.findViewById(R.id.check_order_list_view);
        checkOrderAdapter = new CheckOrderAdapter(getContext(),this.getLayoutInflater(savedInstanceState), Session.shoppingCart.getShoppingCartOrderDetails());
        listView.setAdapter(checkOrderAdapter);

        TextView totalTextView = (TextView) rootView.findViewById(R.id.total_price);
        totalTextView.setText("IDR" + NumberFormatter.formatNumber(checkOrderAdapter.getTotalPrice(), Locale.ENGLISH));
    }

    public void intializeShippingAddress(View rootView){
        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView address = (TextView) rootView.findViewById(R.id.address);
        TextView province = (TextView) rootView.findViewById(R.id.province);
        TextView phone = (TextView) rootView.findViewById(R.id.phone);
        TextView zipCode = (TextView) rootView.findViewById(R.id.zipCode);

        name.setText(Session.shoppingCart.getConsignmentFirstName() + " " + Session.shoppingCart.getConsignmentLastName());
        address.setText(Session.shoppingCart.getConsignmentAddress() + ((Session.shoppingCart.getConsignmentDistrict().isEmpty()) ? "" : ", " + Session.shoppingCart.getConsignmentDistrict())
         +((Session.shoppingCart.getConsignmentCity().isEmpty()) ? "" : ","+ Session.shoppingCart.getConsignmentCity()));
        zipCode.setText(Session.shoppingCart.getConsignmentZipCode());
        province.setText(Session.shoppingCart.getConsignmentState());
        phone.setText(Session.shoppingCart.getConsignmentPhone());
    }

    public void initializeNextButton(View rootView){
        Button nextButton = (Button) rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.google.gson.Gson gson = new GsonBuilder().registerTypeAdapter(ShoppingCart.class, new ShoppingCart.ShoppingCartSerializer())
                        .create();
                String json = gson.toJson(Session.shoppingCart);

                Call<ResponseData<String>> call = RestProvider.getResourceRest().checkOut(Session.getHeaderAuthorization(),gson.toJsonTree(Session.shoppingCart));
                call.enqueue(new Callback<ResponseData<String>>() {
                    @Override
                    public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                        Intent intent = new Intent(getContext(), VeritransActivity.class);
                        intent.putExtra("veritrans",response.body().getData());
                        getContext().startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseData<String>> call, Throwable t) {

                    }
                });
            }
        });
    }
}
