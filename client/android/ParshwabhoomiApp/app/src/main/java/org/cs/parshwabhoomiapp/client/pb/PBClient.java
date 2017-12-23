package org.cs.parshwabhoomiapp.client.pb;

import org.cs.parshwabhoomiapp.client.pb.tasks.LoginTask;
import org.cs.parshwabhoomiapp.client.pb.tasks.GetSearchResultsTask;
import org.cs.parshwabhoomiapp.model.SearchContext;
import org.cs.parshwabhoomiapp.model.UserCredential;

/**
 * Created by saurabhATchampasheruDOTbuild on 15/5/16.
 * Specs out the contract for the behaviour for Parshwabhoomi client.
 */
public interface PBClient {
    public GetSearchResultsTask getSearchResultsTask(SearchContext searchContext);

    public LoginTask getLoginTask(UserCredential credential);
}
