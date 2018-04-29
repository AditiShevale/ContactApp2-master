package com.example.aditi.contactapp2;

import android.app.Activity;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aditi.contactapp2.Pojo.Contact;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends Activity {


    TextView txt_name, txt_number;
    CircleImageView img_dp;
    private String name, phone, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txt_name = findViewById(R.id.txt_detail_name);
        txt_number = findViewById(R.id.txt_detail_number);
        img_dp = findViewById(R.id.img_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        Contact contacts = getIntent().getParcelableExtra("parcel");
        if (contacts != null){
            name =contacts.getName();
            phone=contacts.getContact();
            image = contacts.getImage();
            txt_name.setText(name);
            txt_number.setText(phone);
            Picasso.with(this).load(image).into(img_dp);
        }




    }

    public void addToFav(View view) {
//        if (name != null && )
    }

}
