package com.example.bilalmohammed.footyupdates.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bilalmohammed.footyupdates.Models.LiveScoreObj;
import com.example.bilalmohammed.footyupdates.R;
import com.example.bilalmohammed.footyupdates.SharedPref.PrefManager;
import com.example.bilalmohammed.footyupdates.ViewHolders.LiveViewHolder;

import java.util.ArrayList;

// this has been created to bookmark anydata

public class LiveListAdapter extends RecyclerView.Adapter<LiveViewHolder>{

    //Custom Adapter for ReccyclerView
    Context c;
    ArrayList<LiveScoreObj> liveScoreObjArrayList;
    PrefManager prefManager;


//static belongs to a class
    private static String TAG = "LiveListAdapter";
    public LiveListAdapter(Context c, ArrayList<LiveScoreObj> liveList) {
        this.c = c;
        this.liveScoreObjArrayList = liveList;

        Log.d(TAG, "LiveListAdapter: liveList.size():"+liveList.size());
        prefManager = new PrefManager(c);

    }

    @Override
    public LiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.livescore_list,parent,false);

        return new LiveViewHolder(v);
    }


//A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    @Override
    public void onBindViewHolder(final LiveViewHolder holder, final int position) {

        // Set data in fields
        holder.scoreTv.setText(liveScoreObjArrayList.get(position).getScore());
        holder.homeNameTv.setText(liveScoreObjArrayList.get(position).getHome_name());
        holder.awayNameTv.setText(liveScoreObjArrayList.get(position).getAway_name());
        holder.htScoreTv.setText(liveScoreObjArrayList.get(position).getHt_score());
        holder.ftScoreTv.setText(liveScoreObjArrayList.get(position).getFt_score());
        holder.statusTv.setText(liveScoreObjArrayList.get(position).getStatus());
        holder.leagueNameTv.setText(liveScoreObjArrayList.get(position).getLeague_name());

        // Check first time, if Live score object is bookmarked or not, then change icon accordingly
        if(checkBookMark(liveScoreObjArrayList.get(position).getId())){
            holder.isBookMarked = true;
            holder.bookMarkImageView.setImageResource(R.drawable.ic_star_black_24dp);
            Log.d(TAG, "onBindViewHolder: previously book marked "+liveScoreObjArrayList.get(position).getId());
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

        holder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(position);
            }
        });


    }
    @Override
    public int getItemCount() {
        return liveScoreObjArrayList.size();
    }

    void bookMarkBtnClick(LiveViewHolder holder,int position){
        if(holder.isBookMarked){
            holder.isBookMarked = false;
            holder.bookMarkImageView.setImageResource(R.drawable.ic_star_border_black_24dp);
            prefManager.removeLiveBookMarkId(liveScoreObjArrayList.get(position).getId());
        }else {
            holder.isBookMarked = true;
            holder.bookMarkImageView.setImageResource(R.drawable.ic_star_black_24dp);
            prefManager.addLiveBookMarkId(liveScoreObjArrayList.get(position).getId());
        }


    }


    boolean checkBookMark(final String id){   //
        if(prefManager.getLiveBookMarkIdList().contains(id))
        {return true;}
        else return false;
    }

    // Share with others
    public void share(int position)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Score: "+liveScoreObjArrayList.get(position).getScore()
                        +"\nHome Name: "+liveScoreObjArrayList.get(position).getHome_name()
                        +"\nAway Name: "+liveScoreObjArrayList.get(position).getAway_name()
                        +"\nHt Score: "+liveScoreObjArrayList.get(position).getHt_score()
                        +"\nFt Score: "+liveScoreObjArrayList.get(position).getFt_score()
                        +"\nStatus: "+liveScoreObjArrayList.get(position).getStatus()
                        +"\nLeague Name: "+liveScoreObjArrayList.get(position).getLeague_name()

        );

        c.startActivity(Intent.createChooser(emailIntent, "Pick To Share!"));
    }


}
