package com.example.aditi.contactapp2.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

    private String mName,mContact,mImage;

    public Contact(String name, String contact, String image){
        mName = name;
        mContact=contact;
        mImage=image;

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }



    protected Contact(Parcel in) {
        this.mName=in.readString();
        this.mContact=in.readString();
        this.mImage=in.readString();
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.
            Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mContact);
        dest.writeString(this.mImage);
    }
}
