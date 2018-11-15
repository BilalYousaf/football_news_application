package com.example.bilalmohammed.footyupdates.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bilalmohammed.footyupdates.FourFourTwoDetailsActivity;
import com.example.bilalmohammed.footyupdates.Models.NewsObj;
import com.example.bilalmohammed.footyupdates.R;
import com.example.bilalmohammed.footyupdates.SharedPref.PrefManager;
import com.example.bilalmohammed.footyupdates.ViewHolders.NewsViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class NewsListAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    // Custom adapter for News RecyclerView
    //RecyclerView is designed to display long lists (or grids) of items.
    Context c;
    ArrayList<NewsObj> newsObjArrayList;
    PrefManager prefManager;


    private static String TAG = "NewsListAdapter";

    public NewsListAdapter(Context c, ArrayList<NewsObj> newsObjList) {  // accessing the news onject that we created
        this.c = c;
        this.newsObjArrayList = newsObjList;

        Log.d(TAG, "NewsListAdapter: newsObjList.size():" + newsObjList.size());
        prefManager = new PrefManager(c);

    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.fourfourtwolist_view, parent, false);
        //

        return new NewsViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {

        // Adding data in fields
        try{
        holder.authorTv.setText(newsObjArrayList.get(position).getAuthor());
        holder.titleTv.setText(newsObjArrayList.get(position).getTitle());
        holder.descTv.setText(newsObjArrayList.get(position).getDesc());
        holder.publishDateTv.setText(newsObjArrayList.get(position).getPublish_date());


        if (newsObjArrayList.get(position).getImage_url().toString().length() < 5) {
            holder.newsImageView.setVisibility(View.GONE);
        } else {
            Picasso.with(c) //A powerful image downloading and caching library for Android
                    .load(newsObjArrayList.get(position).getImage_url())
                    .resize(300, 200)
                    .into(holder.newsImageView);
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }



        // Check first time, if news object is bookmarked or not, then change icon accordingly
        if(checkBookMark(newsObjArrayList.get(position).getUrl())){
            holder.isBookMarked = true;
            holder.bookMarkImageView.setImageResource(R.drawable.ic_star_black_24dp);
            Log.d(TAG, "onBindViewHolder: previously book marked "+newsObjArrayList.get(position).getUrl());
        }else {
            holder.isBookMarked = false;
            holder.bookMarkImageView.setImageResource(R.drawable.ic_star_border_black_24dp);
        }


        holder.bookMarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookMarkBtnClick(holder,position);
            }
        });

        holder.newsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Intent i = new Intent(c, FourFourTwoDetailsActivity.class);
                i.putExtra("url", newsObjArrayList.get(+position).getUrl());
                c.startActivity(i);
            }
        });
        holder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return newsObjArrayList.size();
    }

    void bookMarkBtnClick(NewsViewHolder holder,int position){
        if(holder.isBookMarked){
            holder.isBookMarked = false;
            holder.bookMarkImageView.setImageResource(R.drawable.ic_star_border_black_24dp);
            prefManager.removeNewsBookMarkUrl(newsObjArrayList.get(position).getUrl());
        }else {
            holder.isBookMarked = true;
            holder.bookMarkImageView.setImageResource(R.drawable.ic_star_black_24dp);
            prefManager.addNewsBookMarkUrl(newsObjArrayList.get(position).getUrl());
        }

    }


    boolean checkBookMark(final String id){
        if(prefManager.getNewsBookMarkUrlList().contains(id))
        {return true;}
        else return false;
    }

    // Share to others
    public void share(int position)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        emailIntent.putExtra(Intent.EXTRA_TEXT,newsObjArrayList.get(position).getTitle()+
                "\n"+newsObjArrayList.get(position).getUrl());

        c.startActivity(Intent.createChooser(emailIntent, "Pick To Share!"));
    }

}