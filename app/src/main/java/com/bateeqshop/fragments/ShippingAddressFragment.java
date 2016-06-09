package com.bateeqshop.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bateeqshop.MainNavigator;
import com.bateeqshop.R;
import com.bateeqshop.Session;
import com.bateeqshop.model.RequestObjectList;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.rest.RestProvider;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daniel.Nababan on 6/6/2016.
 */
public class ShippingAddressFragment extends Fragment {
    EditText firstName, lastName, company, phone
            ,address,country, state, city, district, zipCode;

    Button copyButton, nextButton;

    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shipping_address,null);

        initializeShippingAddressForm(rootView);
        initializeCopyButton(rootView);
        initializeNextButton(rootView);

        Call<RequestObjectList<ShoppingCart>> call = RestProvider.getResourceRest().getShoppingCarts(Session.getHeaderAuthorization());

        call.enqueue(new Callback<RequestObjectList<ShoppingCart>>() {
            @Override
            public void onResponse(Call<RequestObjectList<ShoppingCart>> call, Response<RequestObjectList<ShoppingCart>> response) {
                if (response.body().data.result.size() > 0) {
                    Session.shoppingCart = response.body().data.result.get(0);
                }
            }

            @Override
            public void onFailure(Call<RequestObjectList<ShoppingCart>> call, Throwable t) {

            }
        });


        return rootView;
    }

    private void initializeCopyButton(View rootView) {
        copyButton = (Button) rootView.findViewById(R.id.copy_address_button);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName.setText(Session.firstName);
                lastName.setText(Session.lastName);
                company.setText(Session.company);
                phone.setText(Session.phone);
                address.setText(Session.address);
                country.setText(Session.countryName);
                state.setText(Session.stateName);
                city.setText(Session.city);
                district.setText(Session.districtName);
                zipCode.setText(Session.zipCode);
            }
        });
    }

    private void initializeNextButton(View rootView){
        nextButton = (Button) rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidAddress()){
                    Session.shoppingCart.setConsignmentFirstName(firstName.getText().toString());
                    Session.shoppingCart.setConsignmentLastName(lastName.getText().toString());
                    Session.shoppingCart.setConsignmentCompany(company.getText().toString());
                    Session.shoppingCart.setConsignmentPhone(phone.getText().toString());
                    Session.shoppingCart.setConsignmentAddress(address.getText().toString());
                    Session.shoppingCart.setConsignmentCountry(country.getText().toString());
                    Session.shoppingCart.setConsignmentState(state.getText().toString());
                    Session.shoppingCart.setConsignmentCity(city.getText().toString());
                    Session.shoppingCart.setConsignmentDistrict(district.getText().toString());
                    Session.shoppingCart.setConsignmentZipCode(zipCode.getText().toString());

                    MainNavigator.showCheckOrder(mContext);
                }
            }
        });
    }

    private boolean isValidAddress() {
        return !isEmpty(firstName) & !isEmpty(phone) & !isEmpty(address) & !isEmpty(district)
                & !isEmpty(state) & !isEmpty(country) & !isEmpty(zipCode) & !isEmpty(city);
    }

    private boolean isEmpty(TextView textView){
        if(textView.getText().toString().isEmpty()){
            textView.setBackgroundColor(Color.parseColor("#EFADAD"));
        }else {
            textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        return textView.getText().toString().isEmpty();
    }

    private void initializeShippingAddressForm(View rootView) {
        firstName = (EditText) rootView.findViewById(R.id.edit_text_first_name);
        lastName = (EditText) rootView.findViewById(R.id.edit_text_last_name);
        company = (EditText) rootView.findViewById(R.id.edit_text_company);
        phone = (EditText) rootView.findViewById(R.id.edit_text_phone);
        address = (EditText) rootView.findViewById(R.id.edit_text_address);
        country = (EditText) rootView.findViewById(R.id.edit_text_country);
        state = (EditText) rootView.findViewById(R.id.edit_text_state);
        city = (EditText) rootView.findViewById(R.id.edit_text_city);
        district = (EditText) rootView.findViewById(R.id.edit_text_district);
        zipCode = (EditText) rootView.findViewById(R.id.edit_text_postal_code);
    }
}
