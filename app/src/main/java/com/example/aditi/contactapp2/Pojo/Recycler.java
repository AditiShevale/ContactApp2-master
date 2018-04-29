package com.example.aditi.contactapp2.Pojo;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aditi.contactapp2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Recycler extends RecyclerView.Adapter<Recycler.MyViewHolder> {
    private List<Contact> mContactList;
    public CircleImageView contactImg;

    public Recycler(List<Contact> contactList) {
        mContactList = contactList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Contact contact = mContactList.get(position);
        Context context = holder.contactImg.getContext();
        Picasso.with(context).load(contact.getImage())
                .into(holder.contactImg);
        Log.i("xyz", String.valueOf(contact.getImage()
        ));
        holder.txt_name.setText(contact.getName());
        holder.txt_phone.setText(contact.getContact());

        holder.contactImg.setImageURI(Uri.parse(contact.getImage()));
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView contactImg;
        public TextView txt_name, txt_phone;


        public MyViewHolder(View itemView) {
            super(itemView);
            contactImg = itemView.findViewById(R.id.profile_image);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_phone = itemView.findViewById(R.id.txt_phone);
        }

    }
}
