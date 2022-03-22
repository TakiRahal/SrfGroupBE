package com.takirahal.srfgroup.modules.user.dto;


import com.takirahal.srfgroup.modules.user.models.PictureFacebook;

public class FacebookVM {

    String accessToken;
    String data_access_expiration_time;
    String email;
    String expiresIn;
    String graphDomain;
    String id;
    String name;
    PictureFacebook picture;
    String signedRequest;
    String userID;
    String sourceProvider;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getData_access_expiration_time() {
        return data_access_expiration_time;
    }

    public void setData_access_expiration_time(String data_access_expiration_time) {
        this.data_access_expiration_time = data_access_expiration_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getGraphDomain() {
        return graphDomain;
    }

    public void setGraphDomain(String graphDomain) {
        this.graphDomain = graphDomain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureFacebook getPicture() {
        return picture;
    }

    public void setPicture(PictureFacebook picture) {
        this.picture = picture;
    }

    public String getSignedRequest() {
        return signedRequest;
    }

    public void setSignedRequest(String signedRequest) {
        this.signedRequest = signedRequest;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSourceProvider() {
        return sourceProvider;
    }

    public void setSourceProvider(String sourceProvider) {
        this.sourceProvider = sourceProvider;
    }
}
