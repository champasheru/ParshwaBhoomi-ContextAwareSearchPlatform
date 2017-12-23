package org.cs.parshwabhoomiapp.client.pb;

import android.os.Looper;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.framework.Client;
import org.cs.parshwabhoomiapp.client.pb.tasks.LoginTask;
import org.cs.parshwabhoomiapp.client.pb.tasks.GetSearchResultsTask;
import org.cs.parshwabhoomiapp.model.SearchContext;
import org.cs.parshwabhoomiapp.model.UserCredential;

/**
 * Created by saurabhATchampasheruDOTbuild on 5/13/2016.
 * Singleton & concrete implementation of the PBClient; this makes sure that there's a single instance throughout the lifetime
 * of the app.
 */
public class PBClientImpl extends Client implements PBClient {
    public static final String TAG = PBClient.class.getSimpleName();
    public static final String BASE_URL_SUFFIX = "/parshwabhoomi-server";
    private UserCredential userCredential;

    public PBClientImpl() {
        super();
    }

    public PBClientImpl(String baseUrl) {
        super(baseUrl);
    }

    public PBClientImpl(String baseUrl, Looper looper){
        super(baseUrl, looper);
    }


    @Override
    public void setBaseUrl(String baseUrl) {
        baseUrl += BASE_URL_SUFFIX;
        Log.i(TAG, "Base url configured="+baseUrl);
        super.setBaseUrl(baseUrl);
    }

    public void setUserCredential(UserCredential credential){
        this.userCredential = credential;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public GetSearchResultsTask getSearchResultsTask(SearchContext searchContext){
        GetSearchResultsTask getSearchResultsTask = new GetSearchResultsTask(searchContext);
        getSearchResultsTask.setClient(this);
        return getSearchResultsTask;
    }


    @Override
    public LoginTask getLoginTask(UserCredential credential) {
        LoginTask loginTask = new LoginTask(credential);
        loginTask.setClient(this);
        return loginTask;
    }
}
