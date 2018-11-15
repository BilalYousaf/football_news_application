package com.example.bilalmohammed.footyupdates;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class EPLActivity extends AppCompatActivity {

    private String TAG = EPLActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://api.sportdeer.com/v1/stages/1/upcoming?page=1&populate=countries&access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YTlhY2Y0ZjA1MGI2NTRhNDNkYTI3NDYiLCJhY2Nlc3NfbGV2ZWwiOjEsImlhdCI6MTUyMjMyNDgwNywiZXhwIjoxNTIyMzI2NjA3fQ.uwkhhLUgwD6I_h6dPR6rIZ-toYNGg0sK4qDkAWpKjN8&";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epl);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

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
            pDialog = new ProgressDialog(EPLActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            EPLHTTPHandler sh = new EPLHTTPHandler(); //object of HTTPHandler

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url); //calling makeHTTPCall method and string its response in a string
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray docs = jsonObj.getJSONArray("docs");

                    // looping through All Contacts
                    for (int i = 0; i < docs.length(); i++) {
                        JSONObject c = docs.getJSONObject(i);

                        String fixture_status = c.getString("fixture_status");  //save string from variable 'id' to string
                        String team_season_away_name = c.getString("team_season_away_name");
                        String team_season_home_name = c.getString("team_season_home_name");
                        String number_goal_team_home = c.getString("number_goal_team_home");
                        //String ht_score = c.getString("ht_score");
                        String schedule_date = c.getString("schedule_date");
                        String stadium = c.getString("stadium");
                        //String referee_name = c.getString("referee_name");

                        // Phone node is JSON Object
                        //JSONObject phone = c.getJSONObject("phone");
                        //String mobile = phone.getString("mobile");
                        //String home = phone.getString("home");
                        //String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> matches = new HashMap<>();  //create a hash map to set key and value pair

                        // adding each child node to HashMap key => value
                        matches.put("fixture_status", fixture_status); //hash map will save key and its value
                        matches.put("team_season_home_name ", team_season_home_name);
                        matches.put("team_season_away_name", team_season_away_name);
                        matches.put("number_goal_team_home" , number_goal_team_home);
                      //  matches.put("ht_score" , ht_score);
                        matches.put("schedule_date", schedule_date);
                        matches.put("stadium", stadium);
                       // matches.put("referee_name", referee_name);



                        // adding contact to contact list
                        contactList.add(matches); //now save all of the key value pairs to arraylist

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show(); //show if you catch any exception with data
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
                                .show(); //show if you are unable to connect with the internet or if jString is null
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
            ListAdapter adapter = new SimpleAdapter(
                    EPLActivity.this, contactList,
                    R.layout.epl_item, new String[]{"fixture_status", "team_season_home_name", "team_season_away_name","number_goal_team_home","schedule_date","stadium"
            }, new int[]{R.id.fixture_status, R.id.team_season_home_name, R.id.team_season_away_name, R.id.number_goal_team_home,R.id.schedule_date,R.id.stadium});

            lv.setAdapter(adapter);
        }

    }
}



