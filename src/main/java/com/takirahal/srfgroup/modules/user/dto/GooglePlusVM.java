package com.takirahal.srfgroup.modules.user.dto;

import com.takirahal.srfgroup.modules.user.models.ProfileObj;

public class GooglePlusVM {

    String Ba;
    String accessToken;
    String googleId;
    ProfileObj profileObj;
    String tokenId;
    String sourceProvider;

    public String getBa() {
        return Ba;
    }

    public void setBa(String ba) {
        Ba = ba;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public ProfileObj getProfileObj() {
        return profileObj;
    }

    public void setProfileObj(ProfileObj profileObj) {
        this.profileObj = profileObj;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getSourceProvider() {
        return sourceProvider;
    }

    public void setSourceProvider(String sourceProvider) {
        this.sourceProvider = sourceProvider;
    }
}
