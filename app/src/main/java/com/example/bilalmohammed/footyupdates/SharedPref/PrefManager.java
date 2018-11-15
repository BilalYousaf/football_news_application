package com.example.bilalmohammed.footyupdates.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.bilalmohammed.footyupdates.Classes.TinyDB;

import java.util.ArrayList;



public class PrefManager {
    // This class handles the storage of BookMarks

    private static final String TAG ="prefManager";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    TinyDB tinydb;
    private static String prefName = "FootUpdatesPref";
    ArrayList<String> liveBookMarkedList = new ArrayList<>();
    ArrayList<String> newsBookMarkedList = new ArrayList<>();

    //KEYS to store
    private static String LIVE_BOOKMARKED_LIST_KEY= "liveIds";
    private static String NEWS_BOOKMARKED_LIST_KEY= "newsUrl";

    public PrefManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(prefName,PRIVATE_MODE);
        editor = preferences.edit();

        // TinyDb is class to simplify storage of ArrayList in Shared Preferences
        //Image result for arraylist meaning
        //An ArrayList is a dynamic data structure, meaning items can be added and removed from the list.
        // A normal array in Java is a static data structure, because you stuck with the initial size of your array.
        tinydb = new TinyDB(context);
        // getting book marked list from storage
        liveBookMarkedList = getLiveBookMarkIdList();
        newsBookMarkedList = getNewsBookMarkUrlList();
        Log.d(TAG, "PrefManager: liveBookMarkedList.size():"+liveBookMarkedList.size());
        Log.d(TAG, "PrefManager: getNewsBookMarkUrlList.size():"+getNewsBookMarkUrlList());

    }


    //Live Scores Section START
    // Add live score bookmark
    public void addLiveBookMarkId(String id) {
        liveBookMarkedList.add(id);
        tinydb.putListString(LIVE_BOOKMARKED_LIST_KEY,liveBookMarkedList);
        Log.d(TAG, "addLiveBookMarkId:"+id);
    }

    // Get live score bookmark list
    public ArrayList<String> getLiveBookMarkIdList() {
        Log.i(TAG, "getLiveBookMarkId called");
        return  tinydb.getListString(LIVE_BOOKMARKED_LIST_KEY);
    }

    //remove live score bookmark
    public void removeLiveBookMarkId(String id){
        for (int i = 0; i < liveBookMarkedList.size(); i++){
            if(liveBookMarkedList.get(i).equals(id)){
                liveBookMarkedList.remove(i);
                Log.d(TAG, "removeLiveBookMarkId: id removed:"+id);
                Log.d(TAG, "removeLiveBookMarkId: book marked list length"+ liveBookMarkedList.size());
            }
        }
        updateLiveBookmarkIdsList(liveBookMarkedList);
    }

    // After removing bookmark, Update list.
    private void updateLiveBookmarkIdsList(ArrayList<String> bookMarkList){
        tinydb.remove(LIVE_BOOKMARKED_LIST_KEY);
        tinydb.putListString(LIVE_BOOKMARKED_LIST_KEY,liveBookMarkedList);
    }

    //Live Scores Section END


    // News Section START
    // Add news bookmark
    public void addNewsBookMarkUrl(String url) {
        newsBookMarkedList.add(url);
        tinydb.putListString(NEWS_BOOKMARKED_LIST_KEY,newsBookMarkedList);
        Log.d(TAG, "addNewsBookMarkUrl: "+url);
    }
    // Get news bookmark list
    public ArrayList<String> getNewsBookMarkUrlList() {
        Log.i(TAG, "getNewsBookMarkUrlList called");
        return  tinydb.getListString(NEWS_BOOKMARKED_LIST_KEY);
    }
    // Remove news bookmark
    public void removeNewsBookMarkUrl(String url){
        for (int i = 0; i < newsBookMarkedList.size(); i++){
            if(newsBookMarkedList.get(i).equals(url)){
                newsBookMarkedList.remove(i);
                Log.d(TAG, "removeNewsBookMarkUrl: url removed:"+url);
                Log.d(TAG, "removeNewsBookMarkUrl: book marked list length"+ newsBookMarkedList.size());
            }
        }
        updateNewsBookmarkUrlList(newsBookMarkedList);
    }

    // Update list after remove
    private void updateNewsBookmarkUrlList(ArrayList<String> bookMarkList){
        tinydb.remove(NEWS_BOOKMARKED_LIST_KEY);
        tinydb.putListString(NEWS_BOOKMARKED_LIST_KEY,newsBookMarkedList);
    }

    //News Section END
}
