package com.example.aditi.contactapp2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aditi.contactapp2.Database.Contract;
import com.example.aditi.contactapp2.Pojo.Contact;
import com.example.aditi.contactapp2.Pojo.FavAdapter;
import com.example.aditi.contactapp2.Pojo.Recycler;
import com.facebook.stetho.Stetho;

import org.json.JSONException;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<Cursor> {

   private Recycler mMyAdapter;
   private RecyclerView mRecyclerView;
    private static final int LOADER_ID = 0;
    private FavAdapter mFavAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new
                LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        build("Fav");
        Stetho.initializeWithDefaults(this);
        final URL ur1 = Network.buildUrl();
        new MovieDBQueryTask().execute(ur1);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|
        ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int id = (int) viewHolder.itemView.getTag();
                String sid = String.valueOf(id);
                Uri uri = Contract.Fav.CONTENT_URI;
                uri = uri.buildUpon().appendPath(sid).build();
                getContentResolver().delete(uri,null,null);
                getSupportLoaderManager().restartLoader(LOADER_ID,null,
                        MainActivity.this);

            }
        }).attachToRecyclerView(mRecyclerView);

    }


    private URL build(String sort) {
        URL final_Url = Network.buildUrl();
        new MovieDBQueryTask().execute(final_Url);
        return  final_Url;

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }
            @Nullable
            @Override
            public Cursor loadInBackground() {
                return getContentResolver()
                        .query(Contract.Fav.CONTENT_URI, null,
                                null, null, null);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        Log.i("hula", String.valueOf(data));

        mFavAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mFavAdapter.swapCursor(null);
    }

    private class MovieDBQueryTask extends AsyncTask<URL,Void,List<Contact>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // mProgressBar.setVisibility(View.VISIBLE);
        }

        protected List<Contact> doInBackground(URL... urls) {


            try {
                List<Contact>  result = Network.fetchContactData(urls[0]);
                return  result;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(final List<Contact> contactList) {
//
            super.onPostExecute(contactList);


           mMyAdapter = new Recycler( contactList,
                   new Recycler.RecyclerViewClickListenerFav() {
                       @Override
                       public void onListItemClick(Contact contacts) {
                           Intent intent = new Intent(MainActivity.this,
                                   DetailActivity.class);
                           intent.putExtra("parcel",contacts);
                           startActivity(intent);
                       }
                   });
           mRecyclerView.setAdapter(mMyAdapter);
           mMyAdapter.notifyDataSetChanged();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       if (id == R.id.action_JSON){
           mRecyclerView.setAdapter(mMyAdapter);

        }
        if (id == R.id.action_FAV) {

            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<Cursor> cursorLoader = loaderManager.getLoader(LOADER_ID);
            if (cursorLoader == null) {
                getSupportLoaderManager().initLoader(LOADER_ID, null, this);

            } else {
                getSupportLoaderManager().restartLoader(LOADER_ID, null, this);

            }
            mFavAdapter = new FavAdapter(this, new FavAdapter.RecyclerViewClickListenerFav() {
                @Override
                public void onClick(Contact contacts) {
                    Intent i = new Intent(MainActivity.this
                            , DetailActivity.class);
                    i.putExtra("parcel", contacts);
                    startActivity(i);
                }
            });
            mRecyclerView.setAdapter(mFavAdapter);
        }

         return super.onOptionsItemSelected(item);

    }

}

