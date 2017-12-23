package org.cs.parshwabhoomiapp.client.pb.tasks;

import android.os.Handler;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.framework.Task;
import org.cs.parshwabhoomiapp.client.framework.restful.DefaultRESTClient;
import org.cs.parshwabhoomiapp.client.framework.restful.RESTClient;
import org.cs.parshwabhoomiapp.client.framework.restful.RESTRequest;
import org.cs.parshwabhoomiapp.client.framework.restful.RESTRequestImpl;
import org.cs.parshwabhoomiapp.client.framework.restful.RestClientException;
import org.cs.parshwabhoomiapp.client.pb.PBClientImpl;
import org.cs.parshwabhoomiapp.client.pb.dto.ErrorResponseDTO;
import org.cs.parshwabhoomiapp.client.pb.dto.LoginRequestDTO;
import org.cs.parshwabhoomiapp.client.pb.dto.adapter.ErrorResponseDTOAdapter;
import org.cs.parshwabhoomiapp.client.pb.dto.adapter.LoginRequestDTOAdapter;
import org.cs.parshwabhoomiapp.client.pb.service.Endpoint;
import org.cs.parshwabhoomiapp.model.UserCredential;

import java.io.IOException;

/**
 * Created by saurabhATchampasheruDOTbuild on 17/5/16.
 * The task for downloading the file from the cloud.
 * TODO: Refer Task class - fork out a subclass called ProgressTask. All tasks which need to show the definite/in-definite progress
 * shall extend from this ProgressTask, for e.g. this LoginTask.
 */
public class LoginTask extends Task {
    public static final String TAG = LoginTask.class.getSimpleName();
    private UserCredential credential;

    public LoginTask(UserCredential credential){
        this.credential = credential;
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
            request.setUrl(getClient().getBaseUrl()+ Endpoint.LOGIN.getEndpointUrl());
            LoginRequestDTOAdapter requestDTOAdapter = new LoginRequestDTOAdapter();
            LoginRequestDTO requestDTO = requestDTOAdapter.buildRequest(credential);

            restClient = new DefaultRESTClient();
            restClient.connect(request);
            requestDTOAdapter.toJson(requestDTO, restClient.getOutputStream());
            int status = restClient.getStatusCode();

            if(status == 200){
                response = Boolean.TRUE;
                credential.setPassword("");//invalidate
                ((PBClientImpl)getClient()).setUserCredential(credential);
            }else{
                response = Boolean.FALSE;
                ErrorResponseDTOAdapter adapter = new ErrorResponseDTOAdapter();
                ErrorResponseDTO errorResponseDTO =  adapter.fromJson(restClient.getInputStream());
                exception = new RestClientException(errorResponseDTO.getMessage());
            }
        } catch (IOException e) {
            Log.e(TAG, "RestClient couldn't connect!", e);
            exception = new RestClientException("Sorry! Your request couldn't be fulfilled right now. Try again.");
        } finally {
            try {
                if(restClient != null){
                    restClient.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "RestClient couldn't connect!", e);
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
