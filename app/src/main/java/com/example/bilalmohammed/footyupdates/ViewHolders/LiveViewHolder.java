package com.example.bilalmohammed.footyupdates.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bilalmohammed.footyupdates.R;

/**
 * Created by Bilal on 4/15/2018.
 */
// ViewHolder describes an item view and metadata about its place within the RecyclerView.
public class LiveViewHolder extends RecyclerView.ViewHolder {
    // When we use Recycler view then we use ViewHolders
    public TextView scoreTv,homeNameTv,awayNameTv,htScoreTv,ftScoreTv,statusTv,leagueNameTv;
    public ImageView bookMarkImageView,shareImageView;
    public boolean isBookMarked=false;

    public LiveViewHolder(View itemView) {
        super(itemView);
        scoreTv=(TextView) itemView.findViewById(R.id.score_live_tv);
        homeNameTv = (TextView) itemView.findViewById(R.id.home_name_tv);
        awayNameTv = (TextView) itemView.findViewById(R.id.away_name_tv);
        htScoreTv = (TextView) itemView.findViewById(R.id.ht_score_tv);
        ftScoreTv = (TextView) itemView.findViewById(R.id.ft_score_tv);
        statusTv = (TextView) itemView.findViewById(R.id.status_tv);
        leagueNameTv = (TextView) itemView.findViewById(R.id.league_name_tv);
        bookMarkImageView = (ImageView) itemView.findViewById(R.id.bookmark_imageView_live);
        shareImageView = (ImageView) itemView.findViewById(R.id.share_imageView_livesscore);

    }
}
