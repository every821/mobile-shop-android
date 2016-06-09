package com.bateeqshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bateeqshop.LoginNavigator;
import com.bateeqshop.R;
import com.bateeqshop.Session;
import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.config.MessageConfig;
import com.bateeqshop.model.FacebookLoginModel;
import com.bateeqshop.model.MergeModel;
import com.bateeqshop.model.SessionModel;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.model.UserProfile;
import com.bateeqshop.rest.RestProvider;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment {

    private Context mContext;
    private View mView;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private LoginButton mFacebookLoginButton;
    private CallbackManager mCallbackManager;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        initView();
        hideRetry();
        hideLoadingProgress();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        Log.i("LOGIN", "RESULT");
    }

    @Override
    public void syncNavigationViewSelection() {

    }

    @Override
    public ViewGroup getContentLayout() {
        return (ViewGroup) mView.findViewById(R.id.ll_login);
    }

    @Override
    public void loadContent() {
        login();
    }

    private void login()
    {
        String userName = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String grantType = ApiConfig.SESSION_PASSWORD_GRANT_TYPE;
        String clientId = ApiConfig.SESSION_CLIENT_ID;
        String clientSecret = ApiConfig.SESSION_CLIENT_SECRET;

        if (userName.length() > 0 && password.length() > 0)
        {
            hideContent();
            showLoadingProgress();

            Call<SessionModel> call = RestProvider.getAuthRest().login(userName, password, grantType, clientId, clientSecret);
            call.enqueue(new Callback<SessionModel>() {
                @Override
                public void onResponse(Call<SessionModel> call, retrofit2.Response<SessionModel> response) {
                    handleLoginResponse(response);
                }

                @Override
                public void onFailure(Call<SessionModel> call, Throwable t) {
                    handleErrorResponse(t);
                }
            });
        }
        else
        {
            showToast(MessageConfig.EMPTY_USERNAME_PASSWORD_ERR_MSG);
        }
    }

    private void getUserProfile()
    {
        Call<UserProfile> call;
        if (Session.getShoppingCartOrderDetailNumber() > 0)
        {
            List<MergeModel> mergeModelList = new ArrayList<>();
            for (ShoppingCartOrderDetail orderDetail : Session.shoppingCartOrderDetails)
            {
                MergeModel mergeModel = new MergeModel();
                mergeModel.setProductCode(orderDetail.getProductCode());
                mergeModel.setProductColorCode(orderDetail.getProductColorCode());
                mergeModel.setProductSizeCode(orderDetail.getProductSizeCode());
                mergeModelList.add(mergeModel);
            }
            call = RestProvider.getResourceRest().mergeShoppingCart(Session.getHeaderAuthorization(), mergeModelList);
        }
        else {
            call = RestProvider.getResourceRest().getUserProfile(Session.getHeaderAuthorization());
        }

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                handleUserProfileResponse(response);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                handleErrorResponse(t);
            }
        });
    }

    private void handleLoginResponse(retrofit2.Response<SessionModel> response)
    {
        switch(response.code())
        {
            case 200 :
                response.body().setDeviceBasedExpiredDate();
                Session.login(response.body());
                Session.updateSession();
                getUserProfile();
                break;
            case 400 :
                try
                {
                    String errorMessage = response.errorBody().string();
                    JSONObject obj = new JSONObject(errorMessage);
                    showToast(obj.getString(MessageConfig.ERROR_DESCRIPTION_RESPONSE_KEY));
                    hideLoadingProgress();
                    showContent();
                }
                catch (Exception ex)
                {
                    showToast(ex.getMessage());
                    hideLoadingProgress();
                    showContent();
                }
                break;
            default:
                showToast("Unknown Error");
                hideLoadingProgress();
                showContent();
        }
    }

    private void handleUserProfileResponse(Response<UserProfile> response)
    {
        switch(response.code())
        {
            case 200 :
                Session.setUserProfile(response.body().getData());
                Session.updateUserProfile();
                if (response.body().getData() != null)
                {
                    if (response.body().getData().getShoppingCarts() != null &&
                            response.body().getData().getShoppingCarts().size() > 0)
                        Session.updateShoppingCarts(response.body().getData().getShoppingCarts().get(0));
                    if (response.body().getData().getWishLists() != null &&
                            response.body().getData().getWishLists().size() > 0)
                        Session.updateWishList(response.body().getData().getWishLists());
                }
                getActivity().finish();
                break;
            case 400 :
                try
                {
                    String errorMessage = response.errorBody().string();
                    JSONObject obj = new JSONObject(errorMessage);
                    showToast(obj.getString(MessageConfig.ERROR_DESCRIPTION_RESPONSE_KEY));
                    hideLoadingProgress();
                    showContent();
                }
                catch (Exception ex)
                {
                    showToast(ex.getMessage());
                    hideLoadingProgress();
                    showContent();
                }
                break;
            default:
                showToast("Unknown Error");
                hideLoadingProgress();
                showContent();
        }
    }

    private void handleErrorResponse(Throwable t) {
        String errorMessage = t.toString();
        if (errorMessage.contains("timeout"))
        {
            showToast(MessageConfig.TIMEOUT_MESSAGE);
        }
        else
        {
            showToast(errorMessage);
        }
        hideLoadingProgress();
        showContent();
    }

    private void initView()
    {
        mUsernameEditText = (EditText) mView.findViewById(R.id.edit_text_username);
        mPasswordEditText = (EditText) mView.findViewById(R.id.edit_text_password);
        Button loginButton = (Button) mView.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        Button registerButton = (Button) mView.findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginNavigator.showRegisterFragment(mContext);
            }
        });

        TextView forgotPasswordTextView = (TextView) mView.findViewById(R.id.text_view_forgot_password);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginNavigator.showForgotPasswordFragment(mContext);
            }
        });

        TextView changePasswordTextView = (TextView) mView.findViewById(R.id.text_view_change_password);
        changePasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginNavigator.showChangePasswordFragment(mContext);
            }
        });

        mFacebookLoginButton = (LoginButton) mView.findViewById(R.id.btn_facebook_login);
        mFacebookLoginButton.setFragment(this);
        mFacebookLoginButton.setReadPermissions("public_profile");
        mFacebookLoginButton.setReadPermissions("email");
        mFacebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult)
            {
                hideContent();
                showLoadingProgress();

//                showToast(loginResult.getAccessToken().getToken());
                Log.i("LOGIN", loginResult.toString());
                Log.i("ACCESS TOKEN", loginResult.getAccessToken().getToken());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null)
                                {
                                    showToast("Failed to login with Facebook");
                                    Log.e("FACEBOOK LOGIN", "FAILED");
                                }
                                else
                                {
                                    try
                                    {
                                        loginWithFacebook(me, loginResult.getAccessToken().getToken());
                                        String email = response.getJSONObject().get("email").toString();
                                        Log.e("Result", email);
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    String id = me.optString("id");
                                    // send email and id to your web server
                                    Log.e("Result1", response.getRawResponse());
                                    Log.e("Result", me.toString());
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel(){
                Log.i("LOGIN", "ON CANCEL");
            }
            @Override
            public void onError(FacebookException e) {
                Log.i("LOGIN", "ON ERROR");
            }
        });
    }

    private void loginWithFacebook(JSONObject response, String facebookAccessToken)
    {
        FacebookLoginModel model = new FacebookLoginModel();
        model.setFirstName(response.optString("first_name"));
        model.setLastName(response.optString("last_name"));
        model.setEmail(response.optString("email"));
        model.setExternalAccessToken(facebookAccessToken);
        model.setPhoneNumber("phoneNumber");
        model.setProvider("Facebook");

        Call<SessionModel> call = RestProvider.getResourceRest().loginWithFacebook(model);
        call.enqueue(new Callback<SessionModel>() {
            @Override
            public void onResponse(Call<SessionModel> call, Response<SessionModel> response) {
                handleLoginResponse(response);
            }

            @Override
            public void onFailure(Call<SessionModel> call, Throwable t) {
                handleErrorResponse(t);
            }
        });
    }
}
