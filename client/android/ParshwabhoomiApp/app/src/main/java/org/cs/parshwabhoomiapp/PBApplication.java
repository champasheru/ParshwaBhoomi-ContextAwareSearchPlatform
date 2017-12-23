package org.cs.parshwabhoomiapp;

import android.app.Application;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.pb.PBClientImpl;
import org.cs.parshwabhoomiapp.location.LocationService;

/**
 * Created by saurabhATchampasheruDOTbuild on 5/13/2016.
 *
 * Keep the globally required fields here: some may need to be singletons, like Client others may not.
 */
public class PBApplication extends Application{
    public static final String TAG = PBApplication.class.getSimpleName();
    private static PBApplication instance;
    private PBClientImpl client;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        client = new PBClientImpl();
        LocationService.init();
        Log.i(TAG, "Application state initialized!");
    }

    public static PBApplication getInstance(){
        return instance;
    }

    public PBClientImpl getClient(){
        return client;
    }
}
