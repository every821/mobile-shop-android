package com.bateeqshop.adapter;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bateeqshop.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by Daniel.Nababan on 6/2/2016.
 */
public class CategoryFilterAdapter extends BaseAdapter {
    public Context mContext;
    LayoutInflater mInflater;
    public List<CategoryFilterModel> listCategory;
    public List<Integer> selected;

    public CategoryFilterAdapter(LayoutInflater inflater,Context context, List<String> categories) {
        this.mInflater = inflater;
        this.mContext =context;
        listCategory = new ArrayList<CategoryFilterModel>();
        for (String category : categories
             ) {
            listCategory.add(new CategoryFilterModel(category,false));
        }
        selected = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return listCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
       return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.filter_category_item, null);
            viewHolder = new ViewHolder();
            viewHolder.categoryTextView = (TextView) convertView.findViewById(R.id.filter_category_item_text_view);
            viewHolder.checkImageView = (ImageView) convertView.findViewById(R.id.filter_category_check_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryFilterModel data = (CategoryFilterModel) getItem(position);
        viewHolder.categoryTextView.setText(data.categoryName);
        viewHolder.checkImageView.setVisibility((data.selected) ? View.VISIBLE : View.GONE);

        return convertView;
    }

    public List<String> getSelectedItem() {
        List<String> selected = new ArrayList<>();
        for (CategoryFilterModel item : listCategory
             ) {
            if(item.selected)
                selected.add(item.categoryName);
        }
        return selected;
    }

    public static class ViewHolder{
        public TextView categoryTextView;
        public ImageView checkImageView;
    }

    public class CategoryFilterModel{
        public String categoryName;
        public boolean selected;

        public CategoryFilterModel(String category, boolean b) {
            this.categoryName = category;
            this.selected = b;
        }
    }

    public void updateSelected(){
        selected.clear();
        for (int i = 0;i<getCount();i++){
            if(listCategory.get(i).selected){
                selected.add(i);
            }
        }
    }

    public void refreshSelected(){
        for(int i = 0;i<getCount();i++){
            listCategory.get(i).selected = selected.contains(i);
        }
    }
}
