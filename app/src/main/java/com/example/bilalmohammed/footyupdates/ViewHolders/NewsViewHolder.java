package com.example.bilalmohammed.footyupdates.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bilalmohammed.footyupdates.R;

/**
 * Created by Bilal on 4/15/2018.
 */

//ViewHolder is a design pattern which can be applied when using a custom adapter.
//Every time when the adapter calls getView() method, the findViewById() method is also called.
public class NewsViewHolder extends RecyclerView.ViewHolder {
    // When we use Recycler view then we use ViewHolders
    public TextView authorTv, titleTv, descTv, publishDateTv;
    public ImageView newsImageView, bookMarkImageView, shareImageView;
    public CardView newsCardView;
    public boolean isBookMarked = false;

    public NewsViewHolder(View itemView) {
        super(itemView);
        newsImageView = (ImageView) itemView.findViewById(R.id.galleryImage);
        shareImageView = (ImageView) itemView.findViewById(R.id.share_imageView_news);
        bookMarkImageView = (ImageView) itemView.findViewById(R.id.bookmark_news_imageView);
        authorTv = (TextView) itemView.findViewById(R.id.author);
        titleTv = (TextView) itemView.findViewById(R.id.title);
        descTv = (TextView) itemView.findViewById(R.id.sdetails);
        publishDateTv = (TextView) itemView.findViewById(R.id.time);
        newsCardView = itemView.findViewById(R.id.news_cardview);


    }
}