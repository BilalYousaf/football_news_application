package com.example.bilalmohammed.footyupdates;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.bilalmohammed.footyupdates.Adapters.LiveListAdapter;
import com.example.bilalmohammed.footyupdates.Models.LiveScoreObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LiveScoreActivity extends AppCompatActivity {

    private String TAG = LiveScoreActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://livescore-api.com/api-client/scores/live.json?key=WkSKNKFI30OIUPMJ&secret=x7TCrrHNIcWn4k90F1KtLqbPqZxlPj4m";

    //NEW
    private RecyclerView recycleView;
    ArrayList<LiveScoreObj> contactList;
    String id, score,home_name,away_name,ht_score,ft_score,status,league_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);
        contactList = new ArrayList<>();
        recycleView = (RecyclerView) findViewById(R.id.list_recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LiveScoreActivity.this);
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
            LiveListAdapter adapter = new LiveListAdapter(LiveScoreActivity.this,contactList);
            recycleView.setAdapter(adapter);

            Log.d(TAG, "onPostExecute: adapter set");
        }

    }
}