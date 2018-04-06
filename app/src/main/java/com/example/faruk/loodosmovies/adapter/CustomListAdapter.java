package com.example.faruk.loodosmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.faruk.loodosmovies.R;
import com.example.faruk.loodosmovies.models.Rating;

import java.util.ArrayList;

/**
 * Created by FARUK on 6.04.2018.
 */

public class CustomListAdapter extends ArrayAdapter<Rating> {
    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Rating> ratingArrayList;
    private static class ViewHolder {
        TextView tvSource;
        TextView tvValue;
    }
    public CustomListAdapter(Context context, ArrayList<Rating> ratingArrayList) {
        super(context,0, ratingArrayList);
        this.context = context;
        this.ratingArrayList = ratingArrayList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return ratingArrayList.size();
    }
    @Override
    public Rating getItem(int position) {
        return ratingArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return ratingArrayList.get(position).hashCode();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_item, null);
            holder = new ViewHolder();
            holder.tvSource = (TextView) convertView.findViewById(R.id.tvSource);
            holder.tvValue = (TextView) convertView.findViewById(R.id.tvValue);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        Rating rating = ratingArrayList.get(position);
        if(rating != null){
            holder.tvSource.setText(rating.getSource());
            holder.tvValue.setText(rating.getValue());
        }
        return convertView;
    }
}
