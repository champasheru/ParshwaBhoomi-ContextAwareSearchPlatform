package org.cs.parshwabhoomiapp.client.framework;

import android.os.Looper;

/**
 * Created by saurabhATchampasheruDOTbuild on 15/5/16.
 * Very very generic contract for the what's called as a Client facade.
 * On framework side rather than app/domain side.
 */
public interface IClient {
    public String  getBaseUrl();
    public void setBaseUrl(String baseUrl);
    public Looper getLooper();
    public void submitTask(Task task);
}
