package com.bateeqshop.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bateeqshop.R;
import com.bateeqshop.fragments.FilterDialog;
import com.bateeqshop.model.CatalogueFilterQuery;
import com.bateeqshop.model.FilterModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Daniel.Nababan on 5/31/2016.
 */
public class CatalogueFilterAdapter extends BaseAdapter {
    public List<FilterModel> filterModels;
    public Context mContext;
    LayoutInflater mInflater;
    public FilterDialog parent;

    public final String PRICE = "price";
    public final String CATEGORY = "category";
    public final String COLOR = "color";

    public CatalogueFilterAdapter (Context context, LayoutInflater inflater, List<FilterModel> models, FilterDialog filterDialog){
        this.mContext = context;
        this.mInflater = inflater;
        this.filterModels = models;
        this.parent = filterDialog;
    }

    @Override
    public int getCount() {
        return filterModels.size();
    }

    @Override
    public Object getItem(int position) {
        return filterModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.filter_catalogue_item, null);
                viewHolder = new ViewHolder();

                viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.textFilterName);
                viewHolder.valueTextView = (TextView) convertView.findViewById(R.id.textFilterValue);
                viewHolder.checkImageView = (ImageView) convertView.findViewById(R.id.checkImage);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            FilterModel filterModel = (FilterModel) getItem(position);
            viewHolder.nameTextView.setText(filterModel.getName());
            if(filterModel.getName().equalsIgnoreCase(PRICE)){
                if(!filterModel.getValue().isEmpty()) {
                    String[] prices = filterModel.getValue().split(";");
                    viewHolder.valueTextView.setText("Rp "+ prices[0] + " - Rp " + prices[1]);
                }else{
                    viewHolder.valueTextView.setText(filterModel.getValue());
                }
            }else{
                viewHolder.valueTextView.setText((filterModel.getValue().length() > 30) ? filterModel.getValue().substring(0,29)+ "..."  : filterModel.getValue() );
            }
            viewHolder.checkImageView.setVisibility((filterModel.getValue().isEmpty()) ? View.INVISIBLE : View.VISIBLE);

        }catch(Exception ex){
            Log.e(this.getClass().getName(),ex.getMessage());
        }
        return convertView;
    }

    public static class ViewHolder{
        public TextView nameTextView;
        public TextView valueTextView;
        public ImageView checkImageView;
    }

    public void updateValue(String key, String newValue){
        for(int i =0; i< getCount();i++){
            FilterModel filterModel = (FilterModel)getItem(i);
            if(filterModel.getName().equalsIgnoreCase(key)){
                filterModel.setValue(newValue);
                break;
            }
        }
        notifyDataSetChanged();
    }

//    public CatalogueFilterQuery toFilterQuery(){
//        CatalogueFilterQuery catalogueFilterQuery = new CatalogueFilterQuery();
//        for(int i = 0 ;i< getCount();i++){
//            FilterModel filterModel = (FilterModel)getItem(i);
//            if(filterModel.getName().equalsIgnoreCase("price")){
//                if(!filterModel.getValue().isEmpty()) {
//                    String[] value = filterModel.getValue().split(";");
//                    catalogueFilterQuery.setMinPrice(Long.parseLong(value[0]));
//                    catalogueFilterQuery.setMaxPrice(Long.parseLong(value[1]));
//                }
//            }
//        }
//
//        return catalogueFilterQuery;
//    }
}
