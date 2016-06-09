package com.bateeqshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
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
public class ColorFilterAdapter extends BaseAdapter {
    public Context mContext;
    LayoutInflater mInflater;
    public List<ColorFilterModel> listColor;
    public List<Integer> selected;



    public ColorFilterAdapter(LayoutInflater inflater,Context context, List<String> colors) {
        this.mInflater = inflater;
        this.mContext =context;
        listColor = new ArrayList<ColorFilterModel>();
        for (String color : colors
                ) {
            listColor.add(new ColorFilterModel(color,false));
        }
        selected = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listColor.size();
    }

    @Override
    public Object getItem(int position) {
        return listColor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.filter_color_item, null);
            viewHolder = new ViewHolder();
            viewHolder.colorTextView = (TextView) convertView.findViewById(R.id.filter_color_item_text_view);
            viewHolder.checkImageView = (ImageView) convertView.findViewById(R.id.filter_color_check_image);
            viewHolder.colorImageView = (ImageView) convertView.findViewById(R.id.filter_color_item_image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ColorFilterModel data = (ColorFilterModel) getItem(position);
        viewHolder.colorTextView.setText(data.colorName);
        viewHolder.checkImageView.setVisibility((data.selected) ? View.VISIBLE : View.GONE);
        setColor(viewHolder.colorImageView, data.colorName, convertView);

        return convertView;
    }

    private void setColor(ImageView colorImageView, String colorName,  View convertView) {
        switch (colorName.toLowerCase()){
            case "blue" : colorImageView.setColorFilter(Color.BLUE);break;
            case "red" : colorImageView.setColorFilter(Color.RED);break;
            case "green" : colorImageView.setColorFilter(Color.GREEN);break;
            case "gray" : colorImageView.setColorFilter(Color.GRAY);break;
            case "black" : colorImageView.setColorFilter(Color.BLACK);break;
            case "purple" : colorImageView.setColorFilter(Color.parseColor("#800080"));break;
            default:colorImageView.setColorFilter(Color.WHITE);break;
        }
    }

    public List<String> getSelectedItem() {
        List<String> selected = new ArrayList<>();
        for (ColorFilterModel item : listColor
                ) {
            if(item.selected)
                selected.add(item.colorName);
        }
        return selected;
    }

    public static class ViewHolder{
        public TextView colorTextView;
        public ImageView checkImageView;
        public ImageView colorImageView;
    }

    public class ColorFilterModel{
        public String colorName;
        public boolean selected;

        public ColorFilterModel(String color, boolean b) {
            this.colorName = color;
            this.selected = b;
        }
    }

    public void updateSelected(){
        selected.clear();
        for (int i = 0;i<getCount();i++){
            if(listColor.get(i).selected){
                selected.add(i);
            }
        }
    }

    public void refreshSelected(){
        for(int i = 0;i<getCount();i++){
            listColor.get(i).selected = selected.contains(i);
        }
    }
}
