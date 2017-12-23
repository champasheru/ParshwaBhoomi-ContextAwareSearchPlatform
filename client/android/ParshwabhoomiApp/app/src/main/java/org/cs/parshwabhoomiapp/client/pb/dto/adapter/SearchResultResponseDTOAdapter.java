package org.cs.parshwabhoomiapp.client.pb.dto.adapter;

import android.util.JsonReader;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.pb.dto.SearchResultResponseDTO;
import org.cs.parshwabhoomiapp.model.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh on 23/12/17.
 */

public class SearchResultResponseDTOAdapter {
    public static final String TAG = SearchResultResponseDTOAdapter.class.getSimpleName();

    public List<SearchResultResponseDTO> fromJson(InputStream inputStream){
        Log.i(TAG, "Parsing search result response JSON...");
        List<SearchResultResponseDTO> dtos = new ArrayList<>();
        JsonReader jsonReader = null;
        try {
            jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            jsonReader.beginArray();
            while (jsonReader.hasNext()){
                jsonReader.beginObject();
                SearchResultResponseDTO dto = new SearchResultResponseDTO();
                dto.readJSON(jsonReader);
                jsonReader.endObject();
                dtos.add(dto);
            }
            jsonReader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(jsonReader != null) {
                    jsonReader.close();
                }
                if(inputStream != null){
                    inputStream.close();;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dtos;
    }

    public List<SearchResult> handleResponse(List<SearchResultResponseDTO> dtos){
        Log.i(TAG, "Parsing search result response dtos...");

        List<SearchResult> results = new ArrayList<>();
        for(SearchResultResponseDTO dto : dtos){
            SearchResult result = new SearchResult();
            result.setBusinessCategory(dto.getBusinessCategory());
            result.setDisplayLink(dto.getDisplayLink());
            result.setFormattedUrl(dto.getFormattedUrl());
            result.setHtmlFormattedUrl(dto.getHtmlFormattedUrl());
            result.setHtmlSnippet(dto.getHtmlSnippet());
            result.setImageUrl(dto.getImageUrl());
            result.setLink(dto.getLink());
            result.setProvider(dto.getProvider());
            result.setSnippet(dto.getSnippet());
            result.setTagline(dto.getTagline());
            result.setTitle(dto.getTitle());
            result.setType(dto.getType());
            result.setVendorId(dto.getVendorId());
            results.add(result);
        }

        return results;
    }
}
