package com.example.aditi.contactapp2.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;

    private DbHelper mDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Contract.AUTHORITY,Contract.PATH_TASKS, TASKS);
        uriMatcher.addURI(Contract.AUTHORITY,Contract.PATH_TASKS+"/#",TASK_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new DbHelper(context);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db  = mDbHelper.getWritableDatabase();
        Cursor retCursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case TASKS:
        retCursor = db.query(Contract.Fav.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        break;
        default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

    // Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri returnUri ;

        int match = sUriMatcher.match(uri);

        switch (match){

            case TASKS:

                long id = db.insert(Contract.Fav.TABLE_NAME,null,values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.Fav.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Uri returnUri; // URI to be returned


        int match = sUriMatcher.match(uri);
        int tasksDeleted; // starts as 0


        switch (match) {
            case TASK_WITH_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(Contract.Fav.TABLE_NAME, "_id=?",
                        new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deletedfile:///usr/lib/slack/resources/app.asar/src/static/logo_spinner.webp
        return tasksDeleted;


        // Implement this to handle requests to delete one or more rows.

    }
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
