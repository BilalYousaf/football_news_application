package com.example.bilalmohammed.footyupdates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bilalmohammed.footyupdates.Activities.BookMarkedLiveScoresActivity;
import com.example.bilalmohammed.footyupdates.Activities.BookMarkedNewsActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnnewsapi;
    private Button btnLive;
    private Button btnFixtures;
    private  Button btnAddTask;
    Button bookMarkedLiveBtn, bookMarkedNewsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnnewsapi = (Button) findViewById(R.id.btnnewsapi);
        btnLive = (Button) findViewById(R.id.btnLive);
        btnFixtures = (Button) findViewById(R.id.btnFixtures);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        bookMarkedLiveBtn = (Button) findViewById(R.id.bookmarked_live_button);
        bookMarkedNewsBtn = (Button) findViewById(R.id.bookmarked_news_btn);

        btnnewsapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FourFourTwoActivity.class);
                startActivity(intent);
            }
        });

        btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LiveScoreActivity.class);
                startActivity(intent);

            }
        });

        btnFixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EPLActivity.class);
                startActivity(intent);


            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, AddTask.class);
                    startActivity(intent);

                }
        });

        bookMarkedLiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BookMarkedLiveScoresActivity.class));
            }
        });

        bookMarkedNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BookMarkedNewsActivity.class));
            }
        });
    }

}
