package org.cs.parshwabhoomiapp.client.pb.dto.adapter;

import android.util.JsonReader;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.pb.dto.ErrorResponseDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by saurabh on 23/12/17.
 */

public class ErrorResponseDTOAdapter {
    public static final String TAG = ErrorResponseDTOAdapter.class.getSimpleName();
    public ErrorResponseDTO fromJson(InputStream inputStream){
        Log.i(TAG, "Reading error dto from JSON");
        JsonReader jsonReader = null;
        try {
            jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            jsonReader.beginObject();
            ErrorResponseDTO dto = new ErrorResponseDTO();
            dto.readJSON(jsonReader);
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ErrorResponseDTO fromException(Exception e){
        return null;
    }
}
