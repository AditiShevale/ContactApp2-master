package com.example.aditi.contactapp2.Pojo;


import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aditi.contactapp2.R;
import com.squareup.picasso.Picasso;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyViewHolder>{
    private Cursor mCursor;
    private Context mContext;
    private RecyclerViewClickListenerFav mListener;

    public interface RecyclerViewClickListenerFav {

        //if we want to on click the item index value
        //void onClick(View view, int position);

        //if we want the whole object to retrive the items
        void onClick(Contact contacts);
    }
    public FavAdapter(Context context, RecyclerViewClickListenerFav listener) {

        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.custom_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
