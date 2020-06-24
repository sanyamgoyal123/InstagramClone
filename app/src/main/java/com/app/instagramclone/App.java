package com.app.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("8NdPD2wVXStNiXYks8XClwJw39CQeh6o1oGzAO9E")
                .clientKey("wCn6iCKC927dKnfsPvwQpKwN2Tyxnyu0e7EfuhWC")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
