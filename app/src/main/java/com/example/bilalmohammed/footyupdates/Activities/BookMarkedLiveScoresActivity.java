package com.example.bilalmohammed.footyupdates.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.bilalmohammed.footyupdates.Adapters.LiveListAdapter;
import com.example.bilalmohammed.footyupdates.HttpHandler;
import com.example.bilalmohammed.footyupdates.Models.LiveScoreObj;
import com.example.bilalmohammed.footyupdates.R;
import com.example.bilalmohammed.footyupdates.SharedPref.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookMarkedLiveScoresActivity extends AppCompatActivity {

    // This Activity is same as LiveScoreActivity, difference is just the 'if' condition in onPostExecute function
    //SONObject:Contains named values (key->value pairs, unordered //like {ID : 1}
    //SONArray:Contains only series values like [1, 'value'] // ordered sequence of values


    private String TAG = "BMiveScoresActivity";

    private ProgressDialog pDialog;
    // URL to get contacts JSON
    private static String url = "http://livescore-api.com/api-client/scores/live.json?key=WkSKNKFI30OIUPMJ&secret=x7TCrrHNIcWn4k90F1KtLqbPqZxlPj4m";
    //NEW
    private RecyclerView recycleView;
    ArrayList<LiveScoreObj> contactList;
    ArrayList<String> bookMarkedList = new ArrayList<>();
    String id, score,home_name,away_name,ht_score,ft_score,status,league_name;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marked_live_scores);
        contactList = new ArrayList<>();
        recycleView = (RecyclerView) findViewById(R.id.list_live_bookmarked_recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        prefManager = new PrefManager(this);
        new BookMarkedLiveScoresActivity.GetContacts().execute();
        bookMarkedList = prefManager.getLiveBookMarkIdList();
    }

    /**
     * Async task class to get json by making HTTP call
     * //AsyncTask enables proper and easy use of the UI thread.
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BookMarkedLiveScoresActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONObject jsonObjData = jsonObj.getJSONObject("data");
                    // Getting JSON Array node

                    JSONArray contacts = jsonObjData.getJSONArray("match");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        //NEW
                        id = c.getString("id");
                        home_name = c.getString("home_name");
                        away_name = c.getString("away_name");
                        score = c.getString("score");
                        ht_score = c.getString("ht_score");
                        ft_score = c.getString("ft_score");
                        status = c.getString("status");
                        league_name = c.getString("league_name");

                        //NEW
                        LiveScoreObj liveScoreObj = new LiveScoreObj(id,score,home_name,away_name,ht_score,ft_score,status,league_name);
                        Log.d(TAG, "doInBackground: Live Score id="+id);


                        // adding contact to contact list
                        //NEW
                        // Checking if bookMarkedList contains id got from server. If yes then add it to ArrayList
                        if(bookMarkedList.contains(id))
                        contactList.add(liveScoreObj);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            LiveListAdapter adapter = new LiveListAdapter(BookMarkedLiveScoresActivity.this,contactList);
            recycleView.setAdapter(adapter);

            Log.d(TAG, "onPostExecute: adapter set");
        }

    }
}
