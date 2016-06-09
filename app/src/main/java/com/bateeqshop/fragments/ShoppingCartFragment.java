package com.bateeqshop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bateeqshop.MainNavigator;
import com.bateeqshop.R;
import com.bateeqshop.Session;
import com.bateeqshop.adapter.ShoppingCartAdapter;
import com.bateeqshop.model.Product;
import com.bateeqshop.model.ProductSize;
import com.bateeqshop.model.RequestObjectList;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.rest.RestProvider;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yosua.petra on 6/6/2016.
 */
public class ShoppingCartFragment extends Fragment implements ShoppingCartAdapter.ShoppingCartAdapterInterface {
    Context mContext;
    View mView;
    ListView mListView;
    LayoutInflater mInflater;
    ShoppingCartFragment mThis = this;

    List<ShoppingCartOrderDetail> mShoppingCartDetail;
    ShoppingCartAdapter mShoppingCartAdapter;

    ProgressBar mProgressBar;

    public void showProductDetail(Product product)
    {
        MainNavigator.showProductFragment(mContext, product);
    }

    public void deleteProduct(int position)
    {
        MainNavigator.showRemoveItemFromShoppingCartDialogFragment(mContext, position);
    }

    public void showSizeColorPicker(Product product, String showFor, String currentSelectionSize, String currentSelectionColor, int position)
    {
        MainNavigator.showChangeColorSizeShoppingCartDialogFragment(mContext, product,showFor, currentSelectionSize, currentSelectionColor, position);
    }

    private void hideLoadingDialog()
    {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showLoadingDialog()
    {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void loadShoppingCartData()
    {
        showLoadingDialog();
        if(Session.isValid())
        {
            Call<RequestObjectList<ShoppingCart>> call = RestProvider.getResourceRest().getShoppingCarts(Session.getHeaderAuthorization());
            call.enqueue(new Callback<RequestObjectList<ShoppingCart>>() {
                @Override
                public void onResponse(Call<RequestObjectList<ShoppingCart>> call, Response<RequestObjectList<ShoppingCart>> response) {
                    if(response.code() == HttpURLConnection.HTTP_OK)
                    {
                        hideLoadingDialog();
                        List<ShoppingCart> _shop = response.body().data.result;
                        if(_shop.size() > 0)
                        {
                            mShoppingCartDetail = _shop.get(0).getShoppingCartOrderDetails(); mListView = (ListView) mView.findViewById(R.id.shopping_cart_list_view);
                            mShoppingCartAdapter = new ShoppingCartAdapter(mThis,getContext(), mInflater, mShoppingCartDetail, mThis);
                            mListView.setAdapter(mShoppingCartAdapter);
                            Session.updateShoppingCarts(_shop.get(0));
                        }
                    }
                    else
                    {
                        hideLoadingDialog();
                        mShoppingCartDetail = new ArrayList<ShoppingCartOrderDetail>();
                        Toast.makeText(mContext, "Connection Error, please check your connection and try again",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RequestObjectList<ShoppingCart>> call, Throwable t) {
                    hideLoadingDialog();
                    Toast.makeText(mContext, "Connection Error, please check your connection and try again",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cart,container, false);
        mInflater = inflater;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mView = view;
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_bar_shopping_cart);
        loadShoppingCartData();
        Button checkOut = (Button) mView.findViewById(R.id.shopping_cart_checkout);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutShoppingCart();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
    }

    public void updateProduct(int position, String size, String color)
    {
        ShoppingCartOrderDetail _tempNew =  mShoppingCartDetail.get(position);
        List<ProductSize> _prodList = _tempNew.getProduct().getProductSizes();
//        _tempNew.setProductSize();
        int c = mListView.getChildCount();
        for (int i = 0; i < c; i++)
        {
            View view = mListView.getChildAt(i);
            if ((Long)view.getTag() == position)
            {
                TextView _color = (TextView) view.findViewById(R.id.color_shopping_cart_item);
                TextView _size =(TextView) view.findViewById(R.id.size_shopping_cart_item);
                _color.setText(color);
                _size.setText(size);
            }
        }
    }

    public void removeProduct(int position)
    {
        mShoppingCartAdapter.deleteCart(position);
    }

    private void checkOutShoppingCart()
    {
        MainNavigator.showShippingCartFragment(mContext);
    }
}
