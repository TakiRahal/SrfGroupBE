package com.takirahal.srfgroup.modules.user.dto;


import com.takirahal.srfgroup.modules.user.models.PictureFacebook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    String idOneSignal;
}
