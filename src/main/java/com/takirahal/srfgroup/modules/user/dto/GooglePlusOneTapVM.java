package com.takirahal.srfgroup.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GooglePlusOneTapVM {
    private String alg;
    private String aud;
    private String azp;
    private String email;
    private String email_verified;
    private String exp;
    private String family_name;
    private String given_name;
    private String iat;
    private String iss;
    private String jti;
    private String kid;
    private String name;
    private String nbf;
    private String picture;
    private String sub;
    private String typ;
    private String sourceProvider;
    private String idOneSignal;
    private String langKey;
}
