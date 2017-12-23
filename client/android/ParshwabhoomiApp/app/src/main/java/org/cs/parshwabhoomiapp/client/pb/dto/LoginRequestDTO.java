package org.cs.parshwabhoomiapp.client.pb.dto;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import org.cs.parshwabhoomiapp.client.framework.dto.JSONSerializable;

import java.io.IOException;

/**
 * Created by saurabh on 23/12/17.
 */

public class LoginRequestDTO implements JSONSerializable{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
            jsonWriter.name("password");
            jsonWriter.value(password);
            jsonWriter.endObject();
        } catch (IOException e) {
            Log.e(getClass().getName(), "Can't build request JSON!", e);
        }
    }
}
