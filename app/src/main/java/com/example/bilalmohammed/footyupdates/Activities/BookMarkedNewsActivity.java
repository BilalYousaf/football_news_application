package com.example.bilalmohammed.footyupdates.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bilalmohammed.footyupdates.Adapters.NewsListAdapter;
import com.example.bilalmohammed.footyupdates.FourFourTwoFunction;
import com.example.bilalmohammed.footyupdates.Models.NewsObj;
import com.example.bilalmohammed.footyupdates.R;
import com.example.bilalmohammed.footyupdates.SharedPref.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookMarkedNewsActivity extends AppCompatActivity {

    // This Activity is same as FourFourTwoActivity, difference is just the 'if' condition in onPostExecute function
    // class that extends the Object class to allow short operations to run asynchronously in the background

    String API_KEY = "d8f93a736f9348a1b5ce67b39c520637"; // ###  NEWS API  ###
    String NEWS_SOURCE = "four-four-two";

    ProgressBar loader;


    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";

    //NEW
    ArrayList<NewsObj> dataList = new ArrayList<>();
    RecyclerView recyclerViewNews; //RecyclerView is more flexible that ListView even if it introduces some complexities
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_four_two);

        //NEW
        recyclerViewNews = (RecyclerView) findViewById(R.id.news_recycleView);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        prefManager = new PrefManager(this);

        loader = (ProgressBar) findViewById(R.id.loader);
//        listNews.setEmptyView(loader);



        if(FourFourTwoFunction.isNetworkAvailable(getApplicationContext()))
        {
            BookMarkedNewsActivity.DownloadNews newsTask = new BookMarkedNewsActivity.DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }


    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";
            xml = FourFourTwoFunction.excuteGet("https://newsapi.org/v1/articles?source="+NEWS_SOURCE+"&sortBy=top&apiKey="+API_KEY, urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){ // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        //NEW
                        NewsObj newsObj = new NewsObj(
                                jsonObject.optString(KEY_AUTHOR).toString(),
                                jsonObject.optString(KEY_TITLE).toString(),
                                jsonObject.optString(KEY_DESCRIPTION).toString(),
                                jsonObject.optString(KEY_URL).toString(),
                                jsonObject.optString(KEY_URLTOIMAGE).toString(),
                                jsonObject.optString(KEY_PUBLISHEDAT).toString()
                        );

                        // Checking if bookMarkedList contains url got from server. If yes then add it to ArrayList
                        if(prefManager.getNewsBookMarkUrlList().contains(newsObj.getUrl()))
                        dataList.add(newsObj);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                NewsListAdapter adapter = new NewsListAdapter(BookMarkedNewsActivity.this, dataList);
                recyclerViewNews.setAdapter(adapter);


            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
            loader.setVisibility(View.GONE);
        }



    }



}