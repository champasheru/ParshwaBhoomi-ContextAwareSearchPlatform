package org.cs.parshwabhoomiapp.client.pb.service;

/**
 * Created by saurabh on 23/12/17.
 */

public enum Endpoint {
    LOGIN("/v1/user/login"),
    SEARCH("/v1/search/q");

    private String endpointUrl;

    private Endpoint(String endpointUrl){
        this.endpointUrl = endpointUrl;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }
}
