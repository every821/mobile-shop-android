package com.bateeqshop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bateeqshop.MainNavigator;
import com.bateeqshop.R;
import com.bateeqshop.Session;
import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.fragments.ShoppingCartFragment;
import com.bateeqshop.model.Product;
import com.bateeqshop.model.Query;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.model.ShoppingCartResponse;
import com.bateeqshop.rest.RestProvider;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by yosua.petra on 6/6/2016.
 */
public class ShoppingCartAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<ShoppingCartOrderDetail> mShoppingCartDetail;
    ShoppingCartFragment mParent;

    ShoppingCartAdapterInterface clickListener;

    public interface ShoppingCartAdapterInterface{
        public void showSizeColorPicker(Product product, String showFor, String currentSelectionSize, String currentSelectionColor, int position);
        public void deleteProduct(int position);
        public void showProductDetail(Product product);
    }

    public void deleteCart(final int position)
    {
        final ShoppingCartOrderDetail orderDetail =  mShoppingCartDetail.get(position);
        if (orderDetail != null) {
            Call<ShoppingCartResponse> call = RestProvider.getResourceRest().deleteShoppingCart(Session.getHeaderAuthorization(), orderDetail.getCode());
            call.enqueue(new retrofit2.Callback<ShoppingCartResponse>() {
                @Override
                public void onResponse(Call<ShoppingCartResponse> call, Response<ShoppingCartResponse> response) {
                    ShoppingCartOrderDetail.deleteItem(orderDetail.getCode());
                    ShoppingCart shoppingCart = response.body().getData();
                    Session.updateShoppingCarts(shoppingCart);
                    removeItem(position);
                }

                @Override
                public void onFailure(Call<ShoppingCartResponse> call, Throwable t) {
                    Log.e("DELETE CART", "FAILED");
                }
            });
        } else {
            Log.e("DELETE CART", "EMPTY");
        }
    }

    public void removeItem(int position)
    {
        if(this.mShoppingCartDetail != null)
            this.mShoppingCartDetail.remove(position);
        notifyDataSetChanged();
    }

    public ShoppingCartAdapter(ShoppingCartFragment parent, Context context, LayoutInflater layoutInflater, List<ShoppingCartOrderDetail> _listOfShoppingCartOrderDetail,ShoppingCartAdapterInterface buttonListener)
    {
        mParent = parent;
        mContext = context;
        mInflater = layoutInflater;
        mShoppingCartDetail = _listOfShoppingCartOrderDetail;
        clickListener = buttonListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(mShoppingCartDetail == null)
            return convertView;

        if(convertView == null)
        {
            final ShoppingCartOrderDetail orderDetail = (ShoppingCartOrderDetail) getItem(position);
            convertView = mInflater.inflate(R.layout.item_shopping_cart,null);

            TextView _title = (TextView)  convertView.findViewById(R.id.title_shopping_cart_item);
            _title.setText(orderDetail.getProduct().getName());

            orderDetail.getProduct();
            TextView _price = (TextView) convertView.findViewById(R.id.price_shopping_cart_item);

            String moneyValue = "";
            DecimalFormat df = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.US));
            moneyValue = df.format(Double.parseDouble(orderDetail.getProductPrice()));

            _price.setText("IDR " + moneyValue);

            final TextView _size = (TextView) convertView.findViewById(R.id.size_shopping_cart_item);
            _size.setText(orderDetail.getProductSize().getSize());

            final ProgressBar _progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar_item_shopping_cart);
            _progressBar.setVisibility(View.VISIBLE);

            final Product temp = orderDetail.getProduct();
            ImageView _image = (ImageView) convertView.findViewById(R.id.image_shopping_cart_item);
            Picasso.with(mContext)
                    .load(ApiConfig.IMAGE_BASE_URL  + orderDetail.getProduct().getProductImages().get(0).getTitle())
                    .error(R.drawable.noimage)
                    .into(_image,
                            new ImageLoadedCallback(_progressBar) {
                                @Override
                                public void onSuccess() {
                                    if (this.progressBar != null) {
                                        this.progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            }
                    );

            _image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.showProductDetail(temp);
                }
            });
            final TextView _color = (TextView) convertView.findViewById(R.id.color_shopping_cart_item);
            _color.setText(orderDetail.getProductColor().getColor());

            convertView.setTag(getItemId(position));
            final int pos = position;

            _color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.showSizeColorPicker(temp, "color", _size.getText().toString(), _color.getText().toString(), pos);
                }
            });
            _size.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.showSizeColorPicker(temp, "size", _size.getText().toString(), _color.getText().toString(), pos);
                }
            });

            ImageButton _delete = (ImageButton) convertView.findViewById(R.id.shopping_cart_remove_item);
            _delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    clickListener.deleteProduct(position);
                }
            });
        }
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mShoppingCartDetail.get(position);
    }

    @Override
    public int getCount() {
        if(mShoppingCartDetail != null)
            return mShoppingCartDetail.size();
        else
            return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public  ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
