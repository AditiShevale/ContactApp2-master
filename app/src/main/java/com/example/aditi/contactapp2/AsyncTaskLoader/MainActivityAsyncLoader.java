package com.example.aditi.contactapp2.AsyncTaskLoader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.aditi.contactapp2.DetailActivity;
import com.example.aditi.contactapp2.Network;
import com.example.aditi.contactapp2.Pojo.Contact;
import com.example.aditi.contactapp2.Pojo.Recycler;
import com.example.aditi.contactapp2.R;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class MainActivityAsyncLoader extends AppCompatActivity
implements LoaderManager.LoaderCallbacks<List<Contact>>{

    private RecyclerView mRecyclerView;
    private Recycler mMyAdapter;
    private String URL_EXTRA = "nomac";
    private static final int CONTACT_LOADER = 25;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);

        mRecyclerView.addItemDecoration(itemDecor);
       URL url1 = Network.buildUrl();

        Bundle bundle = new Bundle();
        bundle.putString(URL_EXTRA, url1.toString());

        getSupportLoaderManager().initLoader(CONTACT_LOADER, bundle, this);
    }

    @NonNull
    @Override
    public Loader<List<Contact>> onCreateLoader(int id, @Nullable final Bundle args) {

        return new AsyncTaskLoader<List<Contact>>(this) {
            List<Contact> list;

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }

                if (list != null) {
                    deliverResult(list);
                } else {
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public List<Contact> loadInBackground() {
                String url = args.getString(URL_EXTRA);

                if (url == null) {
                    return null;
                }

                try {
                    URL url1 = new URL(url);
                    List<Contact> json = Network.fetchContactData(url1);
                    return json;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Contact> data) {
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Contact>> loader, List<Contact> data) {
        if (data == null) {

            Toast.makeText(this, "No data Sorry !!", Toast.LENGTH_SHORT).show();

        } else {
           mMyAdapter= new Recycler(data, new Recycler.RecyclerViewClickListenerFav() {
               @Override
               public void onListItemClick(Contact contacts) {
                   Intent i = new Intent(MainActivityAsyncLoader.this, DetailActivity.class);
                   i.putExtra("parcel", contacts);
                   startActivity(i);
               }
           });
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mMyAdapter);
            mMyAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Contact>> loader) {

    }
}
