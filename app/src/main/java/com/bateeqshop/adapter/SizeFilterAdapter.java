package com.bateeqshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bateeqshop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel.Nababan on 6/2/2016.
 */
public class SizeFilterAdapter extends BaseAdapter {
    public Context mContext;
    LayoutInflater mInflater;
    public List<SizeFilterModel> listSize;
    public List<Integer> selected;



    public SizeFilterAdapter(LayoutInflater inflater,Context context, List<String> sizes) {
        this.mInflater = inflater;
        this.mContext =context;
        listSize = new ArrayList<SizeFilterModel>();
        for (String size : sizes
                ) {
            listSize.add(new SizeFilterModel(size,false));
        }
        selected = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listSize.size();
    }

    @Override
    public Object getItem(int position) {
        return listSize.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.filter_size_item, null);
            viewHolder = new ViewHolder();
            viewHolder.categoryTextView = (TextView) convertView.findViewById(R.id.filter_size_item_text_view);
            viewHolder.checkImageView = (ImageView) convertView.findViewById(R.id.filter_size_check_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SizeFilterModel data = (SizeFilterModel) getItem(position);
        viewHolder.categoryTextView.setText(data.sizeName);
        viewHolder.checkImageView.setVisibility((data.selected) ? View.VISIBLE : View.GONE);

        return convertView;
    }

    public List<String> getSelectedItem() {
        List<String> selected = new ArrayList<>();
        for (SizeFilterModel item : listSize
                ) {
            if(item.selected)
                selected.add(item.sizeName);
        }
        return selected;
    }

    public static class ViewHolder{
        public TextView categoryTextView;
        public ImageView checkImageView;
    }

    public class SizeFilterModel{
        public String sizeName;
        public boolean selected;

        public SizeFilterModel(String size, boolean b) {
            this.sizeName = size;
            this.selected = b;
        }
    }

    public void updateSelected(){
        selected.clear();
        for (int i = 0;i<getCount();i++){
            if(listSize.get(i).selected){
                selected.add(i);
            }
        }
    }

    public void refreshSelected(){
        for(int i = 0;i<getCount();i++){
            listSize.get(i).selected = selected.contains(i);
        }
    }
}
