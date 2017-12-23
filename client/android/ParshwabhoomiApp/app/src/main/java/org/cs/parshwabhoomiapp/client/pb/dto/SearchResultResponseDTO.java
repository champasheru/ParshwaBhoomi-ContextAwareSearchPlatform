package org.cs.parshwabhoomiapp.client.pb.dto;

import android.util.JsonReader;
import android.util.JsonWriter;

import org.cs.parshwabhoomiapp.client.framework.dto.JSONSerializable;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by saurabh on 23/12/17.
 */

public class SearchResultResponseDTO implements JSONSerializable {
    private String title;
    private String tagline;
    private String htmlTitle;
    private String link;
    private String displayLink;
    private String snippet;
    private String htmlSnippet;
    private String formattedUrl;
    private String htmlFormattedUrl;
    private String imageUrl;
    //Preferred(location+prefs),Generic, Google preferred, Google generic.
    private String type;
    //Provider name in case the search result is provided by search engine like Google, Bing, Yahoo etc.
    private String provider;
    private String businessCategory;
    private String vendorId;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getHtmlTitle() {
        return htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDisplayLink() {
        return displayLink;
    }

    public void setDisplayLink(String displayLink) {
        this.displayLink = displayLink;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getHtmlSnippet() {
        return htmlSnippet;
    }

    public void setHtmlSnippet(String htmlSnippet) {
        this.htmlSnippet = htmlSnippet;
    }

    public String getFormattedUrl() {
        return formattedUrl;
    }

    public void setFormattedUrl(String formattedUrl) {
        this.formattedUrl = formattedUrl;
    }

    public String getHtmlFormattedUrl() {
        return htmlFormattedUrl;
    }

    public void setHtmlFormattedUrl(String htmlFormattedUrl) {
        this.htmlFormattedUrl = htmlFormattedUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public void readJSON(JsonReader jsonReader) {
        try {
            while(jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("title")) {
                    title = jsonReader.nextString();
                } else if (name.equals("htmlTitle")) {
                    htmlTitle = jsonReader.nextString();
                } else if (name.equals("link")) {
                    link = jsonReader.nextString();
                } else if (name.equals("displayLink")) {
                    displayLink = jsonReader.nextString();
                } else if (name.equals("formattedUrl")) {
                    formattedUrl = jsonReader.nextString();
                } else if (name.equals("htmlFormattedUrl")) {
                    htmlFormattedUrl = jsonReader.nextString();
                } else if (name.equals("imageUrl")) {
                    imageUrl = jsonReader.nextString();
                } else if (name.equals("snippet")) {
                    snippet = jsonReader.nextString();
                } else if (name.equals("htmlSnippet")) {
                    htmlSnippet = jsonReader.nextString();
                } else if (name.equals("tagline")) {
                    tagline = jsonReader.nextString();
                } else if (name.equals("type")) {
                    type = jsonReader.nextString();
                } else if (name.equals("provider")) {
                    provider = jsonReader.nextString();
                } else if (name.equals("businessCategory")) {
                    businessCategory = jsonReader.nextString();
                } else if (name.equals("vendorId")) {
                    vendorId = jsonReader.nextString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeJSON(JsonWriter jsonWriter) {

    }
}
