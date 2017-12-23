package org.cs.parshwabhoomiapp.client.pb.dto.adapter;

import android.util.JsonWriter;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.pb.dto.LoginRequestDTO;
import org.cs.parshwabhoomiapp.model.UserCredential;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

/**
 * Created by saurabh on 23/12/17.
 */

public class LoginRequestDTOAdapter {
    public static final String TAG = LoginRequestDTOAdapter.class.getSimpleName();

    public LoginRequestDTO buildRequest(UserCredential credential){
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setUsername(credential.getUsername());
        dto.setPassword(credential.getPassword());
        return dto;
    }

    public void toJson(LoginRequestDTO dto, OutputStream outputStream){
        JsonWriter jsonWriter = null;
        try {
            jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            jsonWriter.setIndent(" ");
            dto.writeJSON(jsonWriter);
            jsonWriter.flush();
        } catch (IOException e) {
            Log.e(TAG, "Couldn't write JSON to request!", e);
        }finally {
            try{
                if(jsonWriter != null){
                    jsonWriter.close();
                }
            }catch (IOException e) {
                Log.e(TAG, "Couldn't write JSON to request!", e);
            }
        }
    }
}
