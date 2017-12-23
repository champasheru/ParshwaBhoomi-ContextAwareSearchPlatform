package org.cs.parshwabhoomiapp.client.pb.dto;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.framework.dto.JSONSerializable;

import java.io.IOException;

/**
 * Created by saurabh on 23/12/17.
 */

public class SearchRequestDTO implements JSONSerializable {
    private String username;
    private String query;
    private float latitude;
    private float longitude;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public void readJSON(JsonReader jsonReader) {

    }

    @Override
    public void writeJSON(JsonWriter jsonWriter) {
        try {
            jsonWriter.beginObject();
            jsonWriter.name("username");
            jsonWriter.value(username);
            jsonWriter.name("query");
            jsonWriter.value(query);
            jsonWriter.name("latitude");
            jsonWriter.value(latitude);
            jsonWriter.name("longitude");
            jsonWriter.value(longitude);
            jsonWriter.endObject();
        } catch (IOException e) {
            Log.e(getClass().getName(), "Can't build request JSON!", e);
        }
    }
}
