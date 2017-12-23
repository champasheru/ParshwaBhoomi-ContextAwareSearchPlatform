package org.cs.parshwabhoomiapp.client.pb.dto.adapter;

import android.util.JsonWriter;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.pb.dto.LoginRequestDTO;
import org.cs.parshwabhoomiapp.client.pb.dto.SearchRequestDTO;
import org.cs.parshwabhoomiapp.model.SearchContext;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by saurabh on 23/12/17.
 */

public class SearchRequestDTOAdapter {
    public static final String TAG = SearchResultResponseDTOAdapter.class.getSimpleName();

    public SearchRequestDTO buildRequest(SearchContext context){
        SearchRequestDTO dto = new SearchRequestDTO();
        dto.setUsername(context.getUsername());
        dto.setQuery(context.getQuery());
        dto.setLatitude(context.getLatitude());
        dto.setLongitude(context.getLongitude());
        return dto;
    }

    public void toJson(SearchRequestDTO dto, OutputStream outputStream){
        JsonWriter jsonWriter = null;
        try {
            jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream));
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
