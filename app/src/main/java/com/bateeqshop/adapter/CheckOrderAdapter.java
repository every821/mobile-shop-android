package com.bateeqshop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bateeqshop.R;
import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.utility.NumberFormatter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by Daniel.Nababan on 6/7/2016.
 */
public class CheckOrderAdapter extends BaseAdapter {
    private List<ShoppingCartOrderDetail> listOrder;
    private LayoutInflater mInflater;
    private Context mContext;

    public CheckOrderAdapter(Context mContext, LayoutInflater mInflater,List<ShoppingCartOrderDetail> listOrder) {
        this.mContext = mContext;
        this.listOrder = listOrder;
        this.mInflater = mInflater;
    }

    @Override
    public int getCount() {
        return listOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return listOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView ==null){
            convertView = mInflater.inflate(R.layout.check_order_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.productColorTextView = (TextView)convertView.findViewById(R.id.check_order_product_color);
            viewHolder.productImageView = (ImageView)convertView.findViewById(R.id.check_order_image_view);
            viewHolder.productNameTextView = (TextView)convertView.findViewById(R.id.check_order_product_name);
            viewHolder.productPriceTextView = (TextView)convertView.findViewById(R.id.check_order_price);
            viewHolder.productSizeTextView = (TextView)convertView.findViewById(R.id.check_order_product_size);
            viewHolder.progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ShoppingCartOrderDetail detail = (ShoppingCartOrderDetail) getItem(position);
        viewHolder.productNameTextView.setText(detail.getProduct().getName());
        viewHolder.productColorTextView.setText(detail.getProductColor().getColor());
        viewHolder.productSizeTextView.setText(detail.getProductSize().getSize());
        if(detail.getProduct().getProductImages().size() > 0)
            Picasso.with(mContext)
                    .load(ApiConfig.IMAGE_BASE_URL + detail.getProduct().getPrimaryImage())
                    .fit()
                    .error(R.drawable.noimage)
                    .into(viewHolder.productImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            viewHolder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

        viewHolder.productPriceTextView.setText("IDR " + NumberFormatter.formatNumber(detail.getProduct().getPrice(), Locale.ENGLISH));

        return convertView;
    }

    public double getTotalPrice(){
        double total = 0;
        for (ShoppingCartOrderDetail detail: listOrder){
            total += detail.getProduct().getPrice();
        }
        return total;
    }

    public static class ViewHolder{
        public ImageView productImageView;
        public TextView productNameTextView;
        public TextView productSizeTextView;
        public TextView productColorTextView;
        public TextView productPriceTextView;
        public ProgressBar progressBar;
    }
}
