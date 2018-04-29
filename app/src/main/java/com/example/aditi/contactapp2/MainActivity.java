package com.example.aditi.contactapp2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aditi.contactapp2.Pojo.Contact;
import com.example.aditi.contactapp2.Pojo.Recycler;

import org.json.JSONException;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   private Recycler mRecyclerContact;
   private RecyclerView mRecyclerView;
   //private ProgressBar mProgressBar;


   private final static String MENU_SELECTED = "selected";
    private int selected = -1;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        //mProgressBar = findViewById(R.id.progressBar);

        RecyclerView.LayoutManager mLayoutManager = new
                LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            build("Fav");




        if (savedInstanceState !=null){
            selected =savedInstanceState.getInt(MENU_SELECTED);

            if (selected == -1){
                build("Fav");
            }
            else if (selected == R.id.action_JSON){
                build("Json");
            }
            else{
                build("Fav");
            }
        }

    }

    private URL build(String sort) {
        URL final_Url = Network.buildUrl(sort);
        new MovieDBQueryTask().execute(final_Url);
        return  final_Url;

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
        protected void onPostExecute(List<Contact> contactList) {
//            Toast.makeText(MainActivity.this, String.valueOf(contactList),
//                    Toast.LENGTH_SHORT).show();
            super.onPostExecute(contactList);
            //mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerContact = new Recycler(contactList);
            //Log.i("lo", String.valueOf(contactList));
            mRecyclerView.setAdapter(mRecyclerContact);
            mRecyclerContact.notifyDataSetChanged();


        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
      outState.putInt(MENU_SELECTED,selected);
      super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_JSON:
                build("Json");
                selected=id;

                break;


            case R.id.action_FAV:
                build("Fav");
                selected= id;
                break;
        }
        return  super.onOptionsItemSelected(item);
    }
}

