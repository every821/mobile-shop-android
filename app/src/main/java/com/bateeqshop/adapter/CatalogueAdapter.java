package com.bateeqshop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bateeqshop.MainNavigator;
import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.model.Product;
import com.bateeqshop.R;
import com.bateeqshop.model.ProductSize;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel.Nababan on 5/25/2016.
 */
public class CatalogueAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    List<Product> listProduct;

    public CatalogueAdapter(Context context, LayoutInflater inflater){
        mContext = context;
        mInflater    = inflater;
        listProduct = new ArrayList<Product>();
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateData(List<Product> data) {
        if(data == null) {
            Log.d("data query : ", "clear Adapter");
            listProduct = new ArrayList<Product>();
        }
        else
            listProduct.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.view_catalogue_item,null);
            viewHolder = new ViewHolder();

            viewHolder.nameView = (TextView) convertView.findViewById(R.id.catalog_productName);
//            viewHolder.sizeView = (TextView) convertView.findViewById(R.id.catalog_productSize);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.catalog_productPrice);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.catalog_productImage);
            viewHolder.progressView = (ProgressBar) convertView.findViewById(R.id.progressBar);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Product product = (Product)getItem(position);
        viewHolder.nameView.setText(((product.getName().length() > 27)? product.getName().substring(0,26) + "..." : product.getName()));
//        if(product.getProductSizes().size() > 0)
//            viewHolder.sizeView.setText(product.getProductSizes().get(0).getSize());
//        else
//            viewHolder.sizeView.setText("--");
        viewHolder.priceView.setText("IDR " + String.valueOf(product.getPrice()));

//        DownloadImageTask downloadTask = new DownloadImageTask(viewHolder.imageView);
        viewHolder.progressView.setVisibility(View.VISIBLE);
        if(product.getProductImages().size() > 0)
            Picasso.with(mContext)
            .load(ApiConfig.IMAGE_BASE_URL + product.getPrimaryImage())
            .fit()
                    .error(R.drawable.noimage)
            .into(viewHolder.imageView, new Callback() {
                @Override
                public void onSuccess() {
                    viewHolder.progressView.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
//            downloadTask.execute(product.getProductImages().get(0).getTitle());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            MainNavigator.showProductFragment(mContext, product);
            }
        });
        return convertView;
    }

    public static class ViewHolder{
        public TextView nameView;
//        public TextView sizeView;
        public TextView priceView;

        public ImageView imageView;

        public ProgressBar progressView;
    }
}
