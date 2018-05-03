package com.example.aditi.contactapp2.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    public static final String AUTHORITY = "com.example.aditi.contactapp2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final String PATH_TASKS = "fav";

    public static class Fav implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TASKS).build();



        public static final String TABLE_NAME = "fav";

        public static final String COLUMN_IMAGE  = "image";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_PHONE = "phone";




    }








}
