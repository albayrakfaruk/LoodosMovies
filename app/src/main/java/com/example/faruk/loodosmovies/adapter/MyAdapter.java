package com.example.faruk.loodosmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faruk.loodosmovies.R;
import com.example.faruk.loodosmovies.helper.OnItemClickListener;
import com.example.faruk.loodosmovies.models.Search;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FARUK on 6.04.2018.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        Context context;
        OnItemClickListener itemClickListener;
        public List<Search> searchList;

        public MyAdapter(Context context, List<Search> searchList) {
            this.context = context;
            this.searchList = searchList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return  new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvTitle.setText(searchList.get(position).getTitle());
            holder.tvType.setText(searchList.get(position).getType());
            holder.tvYear.setText(searchList.get(position).getYear());
            Picasso.with(context).load(searchList.get(position).getPoster()).resize(600, 600).into(holder.ivPoster);
        }

        @Override
        public int getItemCount() {
            return searchList.size();
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvTitle, tvYear,tvType;
    public ImageView ivPoster;

    public ViewHolder(View view) {
        super(view);
        tvTitle = (TextView) view.findViewById(R.id.itemTitle);
        tvYear = (TextView) view.findViewById(R.id.itemYear);
        tvType = (TextView) view.findViewById(R.id.itemType);
        ivPoster = (ImageView) view.findViewById(R.id.itemPoster);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(view, getPosition());
        }
    }
}
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }
}
