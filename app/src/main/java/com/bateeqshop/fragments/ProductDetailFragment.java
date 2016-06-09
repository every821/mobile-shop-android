package com.bateeqshop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bateeqshop.R;
import com.bateeqshop.model.Product;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by yosua.petra on 5/30/2016.
 */
public class ProductDetailFragment extends Fragment {

    private View mView;
    private Context mContext;
    private static final String ARG_PARAM1 = "param1";

    private Product productModel;

    public ProductDetailFragment()
    {
    }


    public static ProductDetailFragment newInstance(Product param1) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_product, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
        if (getArguments() != null) {
            productModel = (Product) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    private void initView()
    {
        TabHost tabHost = (TabHost)mView.findViewById(R.id.tabHostProductDetail);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Details");
        tabHost.addTab(spec);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab Two");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Size Charts");
        tabHost.addTab(spec2);

        TextView title = (TextView) mView.findViewById(R.id.product_detail_title);
        title.setText(productModel.getName());

        TextView price = (TextView) mView.findViewById(R.id.product_detail_price);
        String moneyValue = "";
        DecimalFormat df = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.US));
        moneyValue = df.format(productModel.getPrice());
        price.setText("IDR "+ moneyValue);

        WebView wb = (WebView) mView.findViewById(R.id.product_detail_desc);
        String html = "<html><head><style>* {margin:0;padding:0;}</style></head><body>" +productModel.getDescription() + "</body></html>";
        wb.loadData(html, "text/html", "UTF-8");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        initView();
    }
}
