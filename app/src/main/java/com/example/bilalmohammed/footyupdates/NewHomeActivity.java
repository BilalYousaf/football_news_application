package com.example.bilalmohammed.footyupdates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.example.bilalmohammed.footyupdates.Activities.BookMarkedLiveScoresActivity;
import com.example.bilalmohammed.footyupdates.Activities.BookMarkedNewsActivity;

public class NewHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView news,matches,bookmarkednews, getBookmarkedlive; //CardView is mostly used for good looking UI with RecyclerView.
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);
        //defining the cards
        news = (CardView) findViewById(R.id.NewsId);
        matches = (CardView) findViewById(R.id.live);
        bookmarkednews = (CardView) findViewById(R.id.saved);
        getBookmarkedlive = (CardView) findViewById(R.id.save);
        //add Click listener to the cards

        news.setOnClickListener(this);
        matches.setOnClickListener(this);
        bookmarkednews.setOnClickListener(this);
        getBookmarkedlive.setOnClickListener(this);
        logout = (Button) findViewById(R.id.LOGOUT);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewHomeActivity.this, LoginAppActivity.class);
                startActivity(intent);


            }
        });
    }


        @Override
    public void onClick(View view) {

        Intent i;

        switch (view.getId()){
            case R.id.NewsId : i = new Intent(this, FourFourTwoActivity.class); startActivity(i); break;
            case R.id.live : i = new Intent(this, LiveScoreActivity.class); startActivity(i);break;
            case R.id.saved : i = new Intent(this, BookMarkedNewsActivity.class); startActivity(i); break;
            case R.id.save : i = new Intent(this, BookMarkedLiveScoresActivity.class);  startActivity(i); break;
            default:break;

        }


    }


}
