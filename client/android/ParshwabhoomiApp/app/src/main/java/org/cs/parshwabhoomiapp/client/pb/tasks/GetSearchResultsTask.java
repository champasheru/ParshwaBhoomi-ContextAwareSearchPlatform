package org.cs.parshwabhoomiapp.client.pb.tasks;

import android.os.Handler;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.framework.Task;
import org.cs.parshwabhoomiapp.client.framework.restful.DefaultRESTClient;
import org.cs.parshwabhoomiapp.client.framework.restful.RESTClient;
import org.cs.parshwabhoomiapp.client.framework.restful.RESTRequest;
import org.cs.parshwabhoomiapp.client.framework.restful.RESTRequestImpl;
import org.cs.parshwabhoomiapp.client.framework.restful.RestClientException;
import org.cs.parshwabhoomiapp.client.pb.dto.ErrorResponseDTO;
import org.cs.parshwabhoomiapp.client.pb.dto.SearchRequestDTO;
import org.cs.parshwabhoomiapp.client.pb.dto.SearchResultResponseDTO;
import org.cs.parshwabhoomiapp.client.pb.dto.adapter.ErrorResponseDTOAdapter;
import org.cs.parshwabhoomiapp.client.pb.dto.adapter.SearchRequestDTOAdapter;
import org.cs.parshwabhoomiapp.client.pb.dto.adapter.SearchResultResponseDTOAdapter;
import org.cs.parshwabhoomiapp.client.pb.service.Endpoint;
import org.cs.parshwabhoomiapp.model.SearchContext;
import org.cs.parshwabhoomiapp.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhATchampasheruDOTbuild on 15/5/16.
 * Task to fetch/get all the search results for the given query from the cloud.
 */
public class GetSearchResultsTask extends Task {
    public static final String TAG = GetSearchResultsTask.class.getSimpleName();
    private SearchContext searchContext;

    public GetSearchResultsTask() {
    }

    public GetSearchResultsTask(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    @Override
    protected void executeInternal() {
        Log.i(TAG, "Starting execution...");

        Object response = null;
        Exception exception = null;
        RESTClient restClient = null;
        try {
            RESTRequest request = new RESTRequestImpl();
            request.setMethod(RESTRequest.Method.POST);
            request.setContentType(RESTRequest.ContentType.APPLICATION_JSON);
            request.setUrl(getClient().getBaseUrl()+ Endpoint.SEARCH.getEndpointUrl());

            restClient = new DefaultRESTClient();
            restClient.connect(request);
            SearchRequestDTOAdapter requestDTOAdapter = new SearchRequestDTOAdapter();
            SearchRequestDTO requestDTO = requestDTOAdapter.buildRequest(searchContext);
            requestDTOAdapter.toJson(requestDTO, restClient.getOutputStream());

            int status = restClient.getStatusCode();

            if(status == 200){
                SearchResultResponseDTOAdapter dtoAdapter = new SearchResultResponseDTOAdapter();
                List<SearchResultResponseDTO> dtos = dtoAdapter.fromJson(restClient.getInputStream());
                List<SearchResult> results = dtoAdapter.handleResponse(dtos);
                response = results;
            }else{
                ErrorResponseDTOAdapter adapter = new ErrorResponseDTOAdapter();
                ErrorResponseDTO errorResponseDTO =  adapter.fromJson(restClient.getInputStream());
                exception = new RestClientException(errorResponseDTO.getMessage());
            }
        } catch (IOException e) {
            exception = new RestClientException("Sorry! Your request couldn't be fulfilled right now. Try again.");
        } finally {
            try {
                restClient.close();
            } catch (IOException e) {
                exception = new RestClientException("Sorry! Your request couldn't be fulfilled right now. Try again.");
            }
        }

        Log.i(TAG, "Response="+response);
        Log.i(TAG, "Exception="+exception);
        Handler handler = new Handler(getClient().getLooper());
        final Object r = response;
        final Exception e = exception;
        handler.post(new Runnable() {
            @Override
            public void run() {
                getTaskEventListener().onFinish(r, e);
            }
        });
    }
}
